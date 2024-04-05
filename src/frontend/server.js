import express from "express";
import { fileURLToPath } from 'url';
import { dirname } from 'path';

import path from "path";

const app = express();
const port = 3000;
const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);

app.use(express.static(__dirname + '/public'));

app.get('/', function(req, res) {
  res.sendFile(path.join(__dirname, 'html/index.html'));
});

app.get('/login', function(req, res) {
  res.sendFile(path.join(__dirname, 'html/login.html'));
});


app.listen(port, () => {
  console.log(`Server is running on port ${port}!`);
});