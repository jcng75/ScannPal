import mysql from 'mysql';
import { createConnection } from './database.js';
import fs from 'fs';

// get the list of jobs (or scans) for a given user
export async function getJobs(userId) {
    const conn = createConnection();
    
    return new Promise((resolve, reject) => {
        conn.connect(function(err) {
            if (err) {
                console.error(`Error connecting to MySQL database: ${err.message}`);
                reject(err);
                return;
            }

            // format the sql query
            let sql = `SELECT * FROM Job WHERE user_id = ?;`;
            const inserts = [userId];
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

// get the list of results for each given Job (or scan)
export async function getResults(jobId) {
    const conn = createConnection();
    
    return new Promise((resolve, reject) => {
        conn.connect(function(err) {
            if (err) {
                console.error(`Error connecting to MySQL database: ${err.message}`);
                reject(err);
                return;
            }

            // format the sql query
            let sql = `SELECT r.* FROM Result r
                INNER JOIN Task t ON r.task_id = t.task_id
                INNER JOIN Job j ON t.job_id = j.job_id
                WHERE j.job_id = ?;
            `;
            const inserts = [jobId];
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

// create a base64 encoded img url that can be used to display the binary image data
export function createImageUrl(binaryData) {
    // convert binary data to Base64 string
    let base64Data = Buffer.from(binaryData).toString('base64');
    // construct data URL
    let imageUrl = `data:image/jpeg;base64,${base64Data}`;
    return imageUrl;
}

// save the binary image data as a jpg file
export function createImage(binaryData) {
    fs.writeFileSync('output-image.png', binaryData);
}

// const result = await getResults(16);
// console.log(result[1]);

// const binary = result[1].screenshot;
// console.log(binary);

// const imageUrl = createImageUrl(binary);
// console.log(imageUrl);