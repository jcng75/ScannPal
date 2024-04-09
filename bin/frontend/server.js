import express from "express";
import { fileURLToPath } from 'url';
import { dirname } from 'path';
import path from "path";

const app = express();
const port = 3000;
const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);

app.set('view engine', 'ejs');
app.set('views', 'views');

app.use(express.static(__dirname + '/public'));

app.get('/', function(req, res) {
  res.render('pages/index');
});

app.get('/about', function(req, res) {
  res.render('pages/about');
});

app.get('/login', function(req, res) {
  res.render('pages/login');
});

app.get('/test', function(req, res) {
  res.render('pages/test', {
    jumboTitle: "NEW EJS TITLE"
  });
});

app.listen(port, () => {
  console.log(`Server is running on port ${port}!`);
});