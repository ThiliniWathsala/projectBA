/* const express = require('express');
const app = express();
const path = require('path');

app.use(express.static(__dirname + '/dist/batestapp2'));

app.get('/*', function(req,res)){
        res.sendFile(path.join(__dirname + '/dist/batestapp2/index.html'));
});

app.listen(process.env.PORT || 8080);

*/

const express = require('express');
const http = require('http')
const path = require('path');

const app = express();

app.use(express.static(path.join(__dirname, 'dist/Project')));

app.get('*', (req, res) => {
  res.sendFile(path.join(__dirname + '/dist/Project/index.html'));
});

const port = process.env.PORT || 3000;
app.set('port', port);

const server = http.createServer(app);
server.listen(port, () => console.log('running'));
