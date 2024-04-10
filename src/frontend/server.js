import express from "express";
import { fileURLToPath } from 'url';
import { dirname } from 'path';

const app = express();
const port = 3000;
const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);

app.set('view engine', 'ejs');
app.set('views', 'views');

app.use(express.static(__dirname + '/public'));

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

app.get('/register', function(req, res) {
  res.render('pages/register', {
    pageTitle: 'Register'
  });
});

const user = {
  first_name: 'Justin',
  last_name: 'Ng'
}

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