import mysql from 'mysql';
import dotenv from 'dotenv';
dotenv.config({path:'../../.env'});

function createConnection() {
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

function runQuery(sql) {
    const conn = createConnection();

    conn.connect(function(err) {
        if (err) {
            console.error(`Error connecting to MySQL database: ${err.message}`);
            throw err;
        } 
        
        console.log("Connected to MySQL database!");
        conn.query(sql, function(err, results) {
            if (err) {
                console.error(`Error executing query '${sql}': ${err.message}`);
                throw err;
            }
            console.log(JSON.stringify(results, null, 2));
        });
      
    });

}

function createUser(firstName, lastName, email, password) {
    const conn = createConnection();

    conn.connect(function(err) {
        if (err) {
            console.error(`Error connecting to MySQL database: ${err.message}`);
            throw err;
        } 
        
        console.log("Connected to MySQL database!");

        // perform password hashing here...
        password_hash = password;
        
        var sql = `INSERT INTO User (fname, lname, email, password_hash, creation_date) VALUES 
                    (??, ??, ??, ??, NOW()
                );`;
        var inserts = [firstName, lastName, email, password_hash];
        sql = mysql.format(sql, inserts);
        conn.query(sql, function(err, results) {
            if (err) {
                console.error(`Error executing query '${sql}': ${err.message}`);
                throw err;
            }
            console.log("Successfully added new user to the database!");
            console.log(JSON.stringify(results, null, 2));
        });
      
    });
}