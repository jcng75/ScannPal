import express from "express";
import { fileURLToPath } from 'url';
import { dirname } from 'path';
import dotenv from 'dotenv';
import { updateSettings } from './settings.js';
import { runChecks } from './scan.js';

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

app.use(function(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
  next();
});

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
    pageTitle: 'Login'
  });
});

app.post('/login', function(req, res) {
  res.send(req.body);
});

app.get('/register', function(req, res) {
  res.render('pages/register', {
    pageTitle: 'Register'
  });
});

app.post('/register', function(req, res) {
  res.send(req.body);
});

app.get('/logout', function(req, res) {
  res.render('pages/logout', {
    pageTitle: 'Logout',
  });
});

const user = {
  first_name: 'Justin',
  last_name: 'Ng',
  last_scan: '4/12/2024',
  total_scans: 3
}

app.get('/home', function(req, res) {
  res.render('pages/home', {
    pageTitle: 'Home',
    user: user
  });
});

app.get('/scan', function(req, res) {
  // console.log(httpGet('https://example.com'));
  res.render('pages/scan', {
    pageTitle: 'Scan',
    user: user
  });
});

app.post('/scan', function(req, res) {
  // console.log(req.body)
  let bodyReq = req.body;
  let selectOption  = bodyReq.selectOption;
  // res.send(bodyReq);
  let errors = runChecks(bodyReq);
  if (errors.length > 0){
    console.log(errors);
    res.render('pages/scan', {
      pageTitle: 'Scan',
      user: user,
      errors: errors
    })
  } else {
    let success = 'You have successfully started a scan.'
    res.render('pages/scan', {
      pageTitle: 'Scan',
      user: user,
      success: success
    })
  }
});

app.get('/test', function(req, res) {
  res.render('pages/test', {
    pageTitle: 'Test',
    jumboTitle: 'EJS IS COOL'
  });
});

app.get('/settings', function(req, res) {
  res.render('pages/settings', {
    pageTitle: 'Settings',
    user: user,
  });
});

app.post('/settings', function(req, res) {
  // console.log(req.body)
  let bodyReq = req.body;
  let selectOption  = bodyReq.selectOption;
  let errors = updateSettings(selectOption, bodyReq);
  if (errors.length > 0){
    console.log(errors);
    res.render('pages/settings', {
      pageTitle: 'Settings',
      user: user,
      errors: errors
    })
  } else {
    let success = 'You have successfully updated your settings.'
    res.render('pages/settings', {
      pageTitle: 'Settings',
      user: user,
      success: success
    })
  }
});



app.listen(port, () => {
  console.log(`Server is running on port ${port}!`);
});