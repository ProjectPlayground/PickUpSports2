var http = require('http');
var fs = require('fs');
var cs = require('./commandinterpreter.js');
var url = require('url');

var port = 8080;
var path = require('path');
var mypath = "./storage.txt";

var event_path = "./events.json";
var user_path = "./users.json";

http.createServer(function(request, response) {
	var cmd = url.parse(request.url, true).query.cmd;
	var type = url.parse(request.url, true).query.type;

	switch (type) {
		case "user":
			switch (cmd) {
				case "get":
					var username = url.parse(request.url, true).query.username;
					response.writeHead(200);
					cs.getUser(username, response);
					break;
				case "add":
					response.writeHead(201);
					console.log(cmd + " " + type);
					var body;
					request.on('data', function(chunk) {
						var user = chunk.toString();
						cs.addUser(JSON.parse(user));
					});
					break;
				default: 
					response.writeHead(400);
					console.log("Invalid user command");
					break;
			}
			break;
		case "event":
			switch (cmd) {
				case "get":
					var filter = url.parse(request.url, true).query.filter;
					console.log(cmd + " " + type + ": filter -" + filter);
					if (filter == "none") {
						response.writeHead(200);
						cs.getAllEvents(response);
					} else if (filter == "name") {
						var name = url.parse(request.url, true).query.name;
						response.writeHead(200);
						cs.getEvent(name, response);
					} else {
						response.writeHead(400);
						console.log("not a valid filter type");
						response.end();
					}
					break;
				case "add":
					response.writeHead(201);
					console.log(cmd + " " + type);
					var body;
					request.on('data', function(chunk) {
						var event = chunk.toString();
						cs.addEvent(JSON.parse(event));
						response.end();
					});
					break;
				case "delete":
					var name = url.parse(request.url, true).query.name;
					response.writeHead(200);
					cs.deleteEvent(name);
					response.end();
					break;
				default: 
					response.writeHead(400);
					console.log("Invalid user command");
					response.end();
					break;
			}
			break;
		default:
			response.writeHead(400);
			console.log("Invalid type");
			break;
	}
}).listen(port);

console.log("Server Running on " + port);