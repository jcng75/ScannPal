import { createConnection } from "./database.js";
import mysql from 'mysql';

// get the list of top 3 most recent scans
export async function getUserResults(userID) {
    const conn = createConnection();
    
    return new Promise((resolve, reject) => {
        conn.connect(function(err) {
            if (err) {
                console.error(`Error connecting to MySQL database: ${err.message}`);
                reject(err);
                return;
            }

            // format the sql query
            let sql = `SELECT j.job_id, 
                            j.date_started,
                            j.date_completed,
                            SUM(CASE WHEN r.is_vulnerable = 1 THEN 1 ELSE 0 END) AS vulnerabilities
                 FROM Result r
                INNER JOIN Task t ON r.task_id = t.task_id
                INNER JOIN Job j ON t.job_id = j.job_id
                INNER JOIN User u ON j.user_id = u.user_id
                WHERE u.user_id = ?
                GROUP BY j.job_id
                ORDER BY j.date_completed
                LIMIT 3;
            `;
            const inserts = [userID];
            sql = mysql.format(sql, inserts);

            conn.query(sql, function(err, results) {
                if (err) {
                    console.error(`Error executing query: ${err.message}`);
                    reject(err);
                    return;
                }
                resolve(results);
            });

            conn.end();
        });
    });
}

// get the list of top 3 most recent scans
export async function getActiveScans(userID) {
    const conn = createConnection();
    
    return new Promise((resolve, reject) => {
        conn.connect(function(err) {
            if (err) {
                console.error(`Error connecting to MySQL database: ${err.message}`);
                reject(err);
                return;
            }

            // format the sql query
            let sql = `SELECT j.job_id,
                            j.date_started,
                            COUNT(t.task_id) AS number_of_tasks,
                            SUM(CASE WHEN t.completed = 1 THEN 1 ELSE 0 END) AS completed_tasks,
                            (SUM(CASE WHEN t.completed = 1 THEN 1 ELSE 0 END) / COUNT(t.task_id)) * 100 AS completed_percentage
                FROM Job j
                INNER JOIN Task t ON j.job_id = t.job_id
                INNER JOIN User u ON j.user_id = u.user_id
                WHERE u.user_id = ? AND j.completed = 0
                GROUP BY j.job_id, j.date_started;
            `;
            const inserts = [userID];
            sql = mysql.format(sql, inserts);

            conn.query(sql, function(err, results) {
                if (err) {
                    console.error(`Error executing query: ${err.message}`);
                    reject(err);
                    return;
                }
                resolve(results);
            });

            conn.end();
        });
    });
}