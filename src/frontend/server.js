import express from "express";
import { fileURLToPath } from 'url';
import { dirname } from 'path';
import dotenv from 'dotenv';
import {validateHash, getUser, emailExists} from './database.js';

let user = {};
const app = express();
dotenv.config({path:'../../.env'});
const port = process.env.PORT;
const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);

app.set('view engine', 'ejs');
app.set('views', 'views');

app.use(express.static(__dirname + '/public'));

// API Middlewares
app.use(express.json()); // to accept data in JSON format
app.use(express.urlencoded({ extended: true })); // to decode data that is sent through an html form

app.get('/', function(req, res) {
  res.render('pages/index', {
    pageTitle: 'Welcome'
  });
});

app.get('/about', function(req, res) {
  res.render('pages/about', {
    pageTitle: 'About'
  });
});

app.get('/login', function(req, res) {
  res.render('pages/login', {
    pageTitle: 'Login',
    error: false
  });
});

app.post('/login', async function(req, res) {
  const email = req.body.email.trim();
  const password = req.body.password.trim();

  if (email === "" || password === "") { // if email is empty
    return;
  }

  const validEmail = await emailExists(email);
  if (!validEmail) { // if email is not in database
    // pop up invalid login alert
    res.render('pages/login', {
      pageTitle: 'Login',
      error: true
    });
    return;
  }

  // if the email does exist, then compare the user-entered password to the stored hash
  const isValidHash = await validateHash(email, password);

  if (isValidHash) {
      const userData = await getUser(email);
      user = {
        first_name: userData.fname,
        last_name: userData.lname
      }
      res.redirect("/home");
  } else { // if the password doesn't match the hash
      // pop up invalid login alert
      res.render('pages/login', {
        pageTitle: 'Login',
        error: true
      });
      return;
  }

});

app.get('/register', function(req, res) {
  res.render('pages/register', {
    pageTitle: 'Register'
  });
});

app.post('/register', function(req, res) {
  res.send(req.body);
});

app.get('/home', function(req, res) {
  res.render('pages/home', {
    pageTitle: 'Home',
    user: user
  });
});

app.get('/test', function(req, res) {
  res.render('pages/test', {
    pageTitle: 'Test',
    jumboTitle: 'EJS IS COOL'
  });
});

app.listen(port, () => {
  console.log(`Server is running on port ${port}!`);
});