import mysql from 'mysql';
import { createConnection } from './database.js';
import fs from 'fs';
import moment from 'moment/moment.js';

// get the list of completed jobs (or scans) for a given user
export async function getCompletedJobs(userId) {
    const conn = createConnection();
    
    return new Promise((resolve, reject) => {
        conn.connect(function(err) {
            if (err) {
                console.error(`Error connecting to MySQL database: ${err.message}`);
                reject(err);
                return;
            }

            // format the sql query
            // let sql = `SELECT * FROM Job WHERE user_id = ? and completed = true;`;
            let sql = `
                SELECT
                    j.job_id,
                    j.website_link,
                    j.date_started,
                    j.date_completed,
                    COUNT(r.result_id) AS num_results
                FROM
                    Job j
                JOIN
                    Task t ON j.job_id = t.job_id
                JOIN
                    Result r ON t.task_id = r.task_id
                WHERE
                    j.user_id = ? AND
                    j.completed = true
                GROUP BY
                    j.job_id, j.date_started, j.date_completed
                ORDER BY
                    j.job_id DESC;
            `;

            const inserts = [userId];
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
            let sql = `SELECT 
                    r.result_id, 
                    r.attack_type, 
                    r.payload, 
                    LEFT(r.html_string, 1100) as truncated_html_string,
                    r.screenshot
                FROM Result r
                INNER JOIN Task t ON r.task_id = t.task_id
                INNER JOIN Job j ON t.job_id = j.job_id
                WHERE j.job_id = ?
                LIMIT 30;
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

// const result = await getResults(8);
// console.log(result[1]);

// const result = await getResults(16);
// console.log(result[1]);

// const binary = result[1].screenshot;
// console.log(binary);

// const imageUrl = createImageUrl(binary);
// console.log(imageUrl);

// const jobs = await getCompletedJobs(1);
// console.log(jobs);

// for (let job of jobs) {
//     console.log(job.job_id);
//     console.log(job.website_link);
//     console.log(job.date_started);
//     console.log(job.date_completed);
//     console.log(job.num_results);
// }

// const jobs = await getCompletedJobs(1);

// const results = {};

// for (const job of jobs) {
//     results[job.job_id] = await getResults(job.job_id);

//     const jobResults = results[job.job_id];

//     for (const jobResult of jobResults) {
//         const binaryData = jobResult.screenshot;
//         if (binaryData !== null) {
//             const imageUrl = createImageUrl(binaryData);
//             jobResult.base64data = imageUrl;
//         }
//     } 
// }

// console.log(results[16]);