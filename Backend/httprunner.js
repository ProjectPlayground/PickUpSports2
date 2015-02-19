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
 * Express code to use module to delete an event from the commandinterpreter
 * uses a query filter field to indicate the breadth of the 'get' call
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

/*
 * Express code to use module to add an event from the commandinterpreter
 */
app.post('/event/', function(req, res) {
	console.log("post " + url.parse(req.url, true).path);
	res.writeHead(201);
	req.on('data', function(chunk) {
		cs.addEvent(JSON.parse(chunk.toString()));
	});
	res.end();
});

/*
 * Express code to use module to delete an event from the commandinterpreter
 */
app.delete('/event', function(req, res) {
	console.log("delete " + url.parse(req.url, true).path);
	var name = url.parse(req.url, true).query.name;
	res.writeHead(200);
	cs.deleteEvent(name);
	res.end();
});

/*
 * Express code to use module to get a user from the commandinterpreter
 */
app.get('/user/', function(req, res) {
	console.log("get " + url.parse(req.url, true).path);
	var username = url.parse(req.url, true).query.username;
	res.writeHead(200);
	cs.getUser(username, res);
});

/*
 * Express code to use module to add a user from the commandinterpreter
 */
app.post('/user/', function(req, res) {
	console.log("post " + url.parse(req.url, true).path);
	res.writeHead(201);
	req.on('data', function(chunk) {
		cs.addUser(JSON.parse(chunk.toString()));
	});
	res.end();
});

/*
 * Express code to use module to add a user from the commandinterpreter
 */
app.delete('/user/', function(req, res) {
	console.log("delete " + url.parse(req.url, true).path);
	var name = url.parse(req.url, true).query.name;
	res.writeHead(200);
	cs.deleteUser(name);
	res.end();
});

http.createServer(app).listen(port);

console.log("Server Running on " + port);