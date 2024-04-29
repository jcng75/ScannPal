import { createConnection } from "./database.js";
import mysql from 'mysql';
import moment from 'moment/moment.js';

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
                            j.website_link,
                            SUM(CASE WHEN r.is_vulnerable = 1 THEN 1 ELSE 0 END) AS vulnerabilities
                FROM Result r
                INNER JOIN Task t ON r.task_id = t.task_id
                INNER JOIN Job j ON t.job_id = j.job_id
                INNER JOIN User u ON j.user_id = u.user_id
                WHERE u.user_id = ? AND j.completed = 1
                GROUP BY j.job_id
                ORDER BY j.job_id DESC
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

                // convert the time objects to a more human-readable format
                for (let record of results) {
                    record.date_started = moment(record.date_started).format('ddd M/D/YYYY HH:mm:ss');
                    record.date_completed = moment(record.date_completed).format('ddd M/D/YYYY HH:mm:ss');
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
                            j.website_link,
                            SUM(CASE WHEN t.completed = 1 THEN 1 ELSE 0 END) AS completed_tasks,
                            (SUM(CASE WHEN t.completed = 1 THEN 1 ELSE 0 END) / COUNT(t.task_id)) * 100 AS completed_percentage
                FROM Job j
                INNER JOIN Task t ON j.job_id = t.job_id
                INNER JOIN User u ON j.user_id = u.user_id
                WHERE u.user_id = ? AND j.completed = 0
                GROUP BY j.job_id, j.date_started
                ORDER BY j.job_id DESC;
            `;
            const inserts = [userID];
            sql = mysql.format(sql, inserts);

            conn.query(sql, function(err, results) {
                if (err) {
                    console.error(`Error executing query: ${err.message}`);
                    reject(err);
                    return;
                }
                // convert the time objects to a more human-readable format
                for (let record of results) {
                    record.date_started = moment(record.date_started).format('ddd M/D/YYYY HH:mm:ss');
                }
                resolve(results);
            });

            conn.end();
        });
    });
}