const proxy = require('express-http-proxy');
const app = require('express')();
const cors = require('cors');

const ADDRESS = 'http://127.0.0.1:8080/';
const PORT = 8081;

// app.use(cors());
app.use('/', proxy(ADDRESS));
app.listen(PORT, () => console.log("Proxy is RUNNING "+ADDRESS+" -> "+PORT));