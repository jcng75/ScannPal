import mysql from 'mysql';
import dotenv from 'dotenv';
import bcrypt from 'bcrypt';

dotenv.config({path:'../../.env'});

// create a connection to the MySQL database
export function createConnection() {
    const host = process.env.MYSQL_HOST;
    const user = process.env.MYSQL_USERNAME;
    const password = process.env.MYSQL_PASSWORD;
    const database = process.env.MYSQL_DB;
    
    try {
        const conn = mysql.createConnection({
            host: host,
            user: user,
            password: password,
            database: database
        });
        return conn;
    } catch (err) {
        console.error(`Error creating database connection: ${err.message}`);
        throw err;
    }
}

// print the results of a SQL query
async function displayQuery(sql) {
    const conn = createConnection();

    return new Promise((resolve, reject) => {
        conn.connect(function(err) {
            if (err) {
                console.error(`Error connecting to MySQL database: ${err.message}`);
                reject(err);
                return;
            } 
            
            // console.log("Connected to MySQL database!");
            conn.query(sql, function(err, results) {
                if (err) {
                    console.error(`Error executing query '${sql}': ${err.message}`);
                    reject(err);
                    return;
                }
                
                // display results here ...
                console.log(`Running ${sql} ...`);

                if (results.length === 0) {
                    console.log("Table is empty\n");
                }
                else {
                    // get column names
                    const columns = Object.keys(results[0]);

                    results.forEach((row) => {
                        columns.forEach((col) => {
                            console.log(`${col}: ${row[col]}`);
                        });
                        console.log();
                    });
                }
            });

            conn.end();
            resolve();
        });
    });
}

// return the results of a SQL query as an object
async function getQueryResults(sql) {
    const conn = createConnection();
    
    return new Promise((resolve, reject) => {
        conn.connect(function(err) {
            if (err) {
                console.error(`Error connecting to MySQL database: ${err.message}`);
                reject(err);
                return;
            } 
            conn.query(sql, function(err, results) {
                if (err) {
                    console.error(`Error executing query '${sql}': ${err.message}`);
                    reject(err);
                    return;
                }
                resolve(results);
            });

            conn.end();
        });
    });
}

// generates a bcrypt password hash for a user-entered password
// how to call function:
// const password_hash = await hash("goodyear");
async function hash(password) {
    const saltRounds = 10;
    try {
        const salt = await bcrypt.genSalt(saltRounds);
        // console.log(`Salt: ${salt}`);

        const hash = await bcrypt.hash(password, salt);
        // console.log(`Hash: ${hash}`);

        return hash;
    } catch (err) {
        console.error(`Error hashing password: ${err.message}`);
    }
}

// add a new user to the User table
export function createUser(firstName, lastName, email, password) {
    const conn = createConnection();

    conn.connect(async function(err) {
        if (err) {
            console.error(`Error connecting to MySQL database: ${err.message}`);
            throw err;
        } 
        
        console.log("Connected to MySQL database!");

        // perform password hashing here...
        const password_hash = await hash(password);
        
        // format the sql query
        let sql = `INSERT INTO User (fname, lname, email, password_hash, creation_date) VALUES (?, ?, ?, ?, NOW());`;
        const inserts = [firstName, lastName, email, password_hash];
        sql = mysql.format(sql, inserts);
        
        // run the query
        conn.query(sql, function(err, results) {
            if (err) {
                console.error(`Error executing query '${sql}': ${err.message}`);
                throw err;
            }
            console.log("Successfully added new user to the database!");
            console.log(JSON.stringify(results, null, 2));
        });

        conn.end();
    });
}

// checks if an email exists in the database
export async function emailExists(email) {
    const conn = createConnection();

    return new Promise((resolve, reject) => {
        conn.connect(function(err) {
            if (err) {
                console.error(`Error connecting to MySQL database: ${err.message}`);
                reject(err);
                return;
            }

            let sql = `SELECT * FROM User where email = ?;`;
            const inserts = [email];
            sql = mysql.format(sql, inserts);

            conn.query(sql, function(err, res) {
                if (err) {
                    console.error(`Error executing query '${sql}': ${err.message}`);
                    reject(err);
                    return;
                }
                resolve(res.length > 0);
            });

            conn.end();
        });
    });
}

// returns true if the user-entered password matches the hash stored in the database
// returns false otherwise
export async function validateHash(email, password) {
    const conn = createConnection();

    return new Promise((resolve, reject) => {
        conn.connect(function(err) {
            if (err) {
                console.error(`Error connecting to MySQL database: ${err.message}`);
                reject(err);
                return;
            }

            let sql = `SELECT password_hash FROM User where email = ?;`;
            const inserts = [email];
            sql = mysql.format(sql, inserts);
            
            conn.query(sql, async function(err, results) {
                if (err) {
                    console.error(`Error executing query '${sql}': ${err.message}`);
                    reject(err);
                    return;
                }

                // perform hash validation here ...
                const hash = String(results[0].password_hash);
                try {
                    const isValidHash = await bcrypt.compare(password, hash);
                    resolve(isValidHash);
                } catch (err) {
                    reject(err);
                }
            });
            
            conn.end();
        });
    });
}

// returns user information as an object
export async function getUser(email) {
    const conn = createConnection();

    return new Promise((resolve, reject) => {
        conn.connect(function(err) {
            if (err) {
                console.error(`Error connecting to MySQL database: ${err.message}`);
                reject(err);
                return;
            } 

            // format the sql query
            let sql = `SELECT user_id, fname, lname FROM User where email = ?;`;
            const inserts = [email];
            sql = mysql.format(sql, inserts);

            conn.query(sql, function(err, results) {
                if (err) {
                    console.error(`Error executing query '${sql}': ${err.message}`);
                    reject(err);
                    return;
                }
                resolve(results[0]);
            });

            conn.end();
        });
    });
}

// returns user information as an object
export async function getName(userId) {
    const conn = createConnection();

    return new Promise((resolve, reject) => {
        conn.connect(function(err) {
            if (err) {
                console.error(`Error connecting to MySQL database: ${err.message}`);
                reject(err);
                return;
            } 

            // format the sql query
            let sql = `SELECT fname FROM User where user_id = ?;`;
            const inserts = [userId];
            sql = mysql.format(sql, inserts);

            conn.query(sql, function(err, results) {
                if (err) {
                    console.error(`Error executing query '${sql}': ${err.message}`);
                    reject(err);
                    return;
                }
                resolve(results[0]);
            });

            conn.end();
        });
    });
}

// updates a user's password in the database
export async function updatePassword(userID, newPassword) {
    const conn = createConnection();

    return new Promise((resolve, reject) => {
        conn.connect(async function(err) {
            if (err) {
                console.error(`Error connecting to MySQL database: ${err.message}`);
                reject(err);
                return;
            }

            // perform password hashing here...
            const password_hash = await hash(newPassword);
            
            // format the sql query
            let sql = `UPDATE User SET password_hash = ? WHERE user_id = ?`;
            const inserts = [password_hash, userID];
            sql = mysql.format(sql, inserts);
            
            // run the query
            conn.query(sql, function(err, results) {
                if (err) {
                    console.error(`Error executing query: ${err.message}`);
                    reject(err);
                    return;
                }
                console.log("Successfully added new user to the database!");
                console.log(JSON.stringify(results, null, 2));
            });

            conn.end();
            resolve();
        });
    });
}