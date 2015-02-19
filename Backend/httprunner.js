var http = require('http');
var fs = require('fs');
var cs = require('./commandinterpreter.js');
var url = require('url');
var express = require('express');
var app = express();

var port = 8080;
var path = require('path');
var mypath = "./storage.txt";

var event_path = "./events.json";
var user_path = "./users.json";

/*
 * Call to get events. filter specifies what type of get event this req is
 */
app.get('/event/', function(req, res) {
	console.log("get " + url.parse(req.url, true).path);
	var filter = url.parse(req.url, true).query.filter;
	switch (filter) {
		case 'none': 
			res.writeHead(200);
			cs.getAllEvents(res);
			break;
		case "name":
			var name = url.parse(req.url, true).query.name;
			res.writeHead(200);
			cs.getEvent(name, res);
			break;
		default:
			res.writeHead(400);
			console.log("not a valid filter type");
			res.end();
	}
});

app.post('/event/', function(req, res) {
	console.log("post " + url.parse(req.url, true).path);
	res.writeHead(201);
	req.on('data', function(chunk) {
		cs.addEvent(JSON.parse(chunk.toString()));
	});
	res.end();
});

app.delete('/event', function(req, res) {
	console.log("delete " + url.parse(req.url, true).path);
	var name = url.parse(req.url, true).query.name;
	res.writeHead(200);
	cs.deleteEvent(name);
	res.end();
});
http.createServer(app).listen(port);

/*
http.createServer(function(req, res) {
	var cmd = url.parse(req.url, true).query.cmd;
	var type = url.parse(req.url, true).query.type;

	switch (type) {
		case "user":
			switch (cmd) {
				case "get":
					var username = url.parse(req.url, true).query.username;
					res.writeHead(200);
					cs.getUser(username, res);
					break;
				case "add":
					res.writeHead(201);
					console.log(cmd + " " + type);
					var body;
					req.on('data', function(chunk) {
						var user = chunk.toString();
						cs.addUser(JSON.parse(user));
					});
					break;
				default: 
					res.writeHead(400);
					console.log("Invalid user command");
					break;
			}
			break;
*/

console.log("Server Running on " + port);