var http = require('http');
var fs = require('fs');
var cs = require('./commandinterpreter.js');
var url = require('url');

var port = 8080;
var path = require('path');
var mypath = "./storage.txt";

http.createServer(function(request, response) {
	var cmd = url.parse(request.url, true).query.cmd;
	var type = url.parse(request.url, true).query.type;

	switch (type) {
		case "user":
			switch (cmd) {
				case "get":
					var username = url.parse(request.url, true).query.username;
					console.log(cmd + " " + type + " : " + username);
					cs.getUser(username, response);
					break;
				case "add":
					var user = url.parse(request.url, true).query.user;
					console.log(cmd + " " + type + " : " + JSON.parse(user));
					//cs.addUser(user);
					break;
				default: 
					console.log("Invalid user command");
					break;
			}
			break;
		default:
			console.log("Invalid type");
			break;
	}

/*
	request.on('data', function(chunk) {
		if (!cs.interpret(chunk, mypath)) {
			console.log("Operation Failed.")
		}
	});

    var fooey = {sport:1, name:"foo", type:"something"};

    response.write(JSON.stringify());
*/

	response.end();
}).listen(port);

console.log("Server Running on " + port);