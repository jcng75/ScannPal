import express from "express";
import session from "express-session";
import expressMySQLSession from "express-mysql-session";
import { fileURLToPath } from 'url';
import { dirname } from 'path';
import dotenv from 'dotenv';
import { validateHash, getUser, getName, emailExists, createUser, getUserData } from './database.js';
import { verifySettings, updateSettings } from './settings.js';
import { runChecks } from './scan.js';
import { getJobs, getResults, createImageUrl } from './results.js';
import { getActiveScans, getUserResults } from "./home.js";

dotenv.config({path:'../../.env'});

const app = express();
const port = process.env.SERVER_PORT;
const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);
const IN_PROD = process.env.NODE_ENV === "production";

const MySQLStore = expressMySQLSession(session);

// Session store options
const options = {
  host: process.env.MYSQL_HOST,
  port: process.env.MYSQL_PORT,
  user: process.env.MYSQL_USERNAME,
  password: process.env.MYSQL_PASSWORD,
  database: process.env.MYSQL_DB
};

const sessionStore = new MySQLStore(options);

app.use(session({
  name: process.env.SESS_NAME,
  secret: process.env.SESS_SECRET,
  resave: false,
  saveUninitialized: false,
  store: sessionStore,
  cookie: {
    maxAge: parseInt(process.env.SESS_LIFETIME),
    sameSite: true, // strict
    secure: IN_PROD
  }
}));

app.set('view engine', 'ejs');
app.set('views', 'views');

app.use(express.static(__dirname + '/public'));

// API Middlewares
app.use(express.json()); // to accept data in JSON format
app.use(express.urlencoded({ extended: true })); // to decode data that is sent through an html form

const redirectLogin = (req, res, next) => {
  if (!req.session.userId) {
    res.redirect('/login');
  } else {
    next();
  }
}

const redirectHome = (req, res, next) => {
  if (req.session.userId) {
    res.redirect('/home');
  } else {
    next();
  }
}

// routes
app.get('/', redirectHome, function(req, res) {
  const { userId } = req.session;
  // console.log(userId);
  res.render('pages/index', {
    pageTitle: 'Welcome'
  });
});

app.get('/home', redirectLogin, async function(req, res) {
  // console.log(req.session);
  const userId = req.session.userId;
  // console.log(userId);
  const userData = await getName(userId);
  const results = await getUserResults(userId);
  // console.log(results);
  // console.log(results.length);
  const activeScans = await getActiveScans(userId);
  console.log(activeScans);
  const user = {
    first_name: userData.fname,
    results: results,
    scans: activeScans
  }
  res.render('pages/home', {
    pageTitle: 'Home',
    user: user
  });
});

app.get('/about', function(req, res) {
  res.render('pages/about', {
    pageTitle: 'About'
  });
});

app.get('/login', redirectHome, function(req, res) {
  res.render('pages/login', {
    pageTitle: 'Login',
    error: false
  });
});

app.post('/login', redirectHome, async function(req, res) {
  const email = req.body.email.trim();
  const password = req.body.password.trim();

  // if the email is not in the database
  const isValidEmail = await emailExists(email);

  if (!isValidEmail) {
    // pop up invalid login alert
    return res.render('pages/login', {
      pageTitle: 'Login',
      error: true
    });
  }

  // if the email does exist, then compare the user-entered password to the stored hash
  const isValidHash = await validateHash(email, password);

  if (!isValidHash) {
     // if the password doesn't match the hash
     // pop up invalid login alert
    return res.render('pages/login', {
      pageTitle: 'Login',
      error: true
    });
  }

  // made it past all of the errors here ...
  const userData = await getUser(email);
  
  if (userData.user_id) {
    req.session.userId = userData.user_id;
    return res.redirect('/home');
  }

  // if error occurs during login
  res.redirect('/login');

});

app.get('/register', redirectHome, function(req, res) {
  res.render('pages/register', {
    pageTitle: 'Register',
    error: false
  });
});

app.post('/register', redirectHome, async function(req, res) {
  const firstName = req.body.fname;
  const lastName = req.body.lname;
  const email = req.body.email;
  const password = req.body.password;

  createUser(firstName, lastName, email, password);

  const userData = await getUser(email);

  if (userData.user_id) {
    req.session.userId = userData.user_id;
    return res.redirect('/home');
  }

  // if error occurs during registration
  res.redirect('/register');
});

app.get('/scan', redirectLogin, function(req, res) {
  res.render('pages/scan', {
    pageTitle: 'Scan',
  });
});

app.post('/scan', redirectLogin, function(req, res) {
  let bodyReq = req.body;
  let errors = runChecks(bodyReq);
  if (errors.length > 0){
    console.log(errors);
    res.render('pages/scan', {
      pageTitle: 'Scan',
      user: testData,
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

app.get('/settings', redirectLogin, async function(req, res) {
  const userId = req.session.userId;
  const userData = await getUserData(userId);
  const user = {
    first_name: userData.fname,
    last_name: userData.lname,
    email: userData.email
  }
  res.render('pages/settings', {
    pageTitle: 'Settings',
    user: user
  });
});

app.post('/settings', async function(req, res) {
  if (req.body.delete === ""){
    console.log('Delete the account.');
    res.redirect('/login');
  }
  const bodyReq = req.body;
  const selectOption  = bodyReq.selectOption;
  const userId = req.session.userId;
  let userData = await getUserData(userId);
  let errors = await verifySettings(selectOption, bodyReq, userData);
  let user;
  if (errors.length > 0){
    user = {
      first_name: userData.fname,
      last_name: userData.lname,
      email: userData.email
    }
    res.render('pages/settings', {
      pageTitle: 'Settings',
      user: user,
      errors: errors
    })
  } else {
    await updateSettings(selectOption, bodyReq, userId);
    userData = await getUserData(userId);
    user = {
      first_name: userData.fname,
      last_name: userData.lname,
      email: userData.email
    }
    let success = 'You have successfully updated your settings';
    res.render('pages/settings', {
      pageTitle: 'Settings',
      user: user,
      success: success
    })
  }
});

app.get('/payment', function(req, res) {
  res.render('pages/payment', {
    pageTitle: 'Payment'
  });
});

app.get('/plans', function(req, res) {
  res.render('pages/plans', {
    pageTitle: 'Payment Plans',
  });
});

app.get('/guidelines', function(req, res) {
  res.render('pages/guidelines', {
    pageTitle: 'Scan Guidelines',
  })
})

app.get('/results', async function(req, res) {
  const result = await getResults(16);
  const binary = result[1].screenshot;
  const imageUrl = createImageUrl(binary);

  res.render('pages/results', {
    pageTitle: 'Results',
    imageUrl
  });
});

app.get('/logout', redirectLogin, function(req, res) {
  req.session.destroy(err => {
    if (err) {
      return res.redirect('/home');
    }

    res.clearCookie(process.env.SESS_NAME);
    res.render('pages/logout', {
      pageTitle: 'Logout'
    });
  })
});

app.listen(port, () => {
  console.log(`Server is running on port ${port}!`);
});