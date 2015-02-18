var fs = require('fs');

var command_list = ['add', 'delete'];

var event_path = "./events.json";
var user_path = "./users.json";

//returns specified user
exports.getUser = function(username, response) {
	fs.readFile(user_path, function(err, users) {
		if (err) {
			console.log("error while reading " + user_path);
			response.write(null);
			response.end();
			return;
		} else {
			var user_list = JSON.parse(users.toString()).users;
			for (var i = 0; i < user_list.length; i++) {
				if (user_list[i].username == username) {
					console.log("found it");
					console.log(JSON.stringify(user_list[i]));
					response.write(JSON.stringify(user_list[i]));
					response.end();
					return;
				}
			}
			console.log("couldn't find user " + username);
			response.write(null);
			return;
		}
		console.log("Response Ending");
		response.end();
		return;
	});
}

exports.addUser = function(user) {
	fs.readFile(user_path, function(err, users) {
		if (err) {
			console.log("error while reading " + user_path);
		} else {
			console.log(JSON.stringify(user));
			var user_list = JSON.parse(users.toString()).users;
			user_list.push(user);
			fs.writeFile(user_path, 
				"{\"users\":" + JSON.stringify(user_list, null, 4) +"}", 
				function(err) {
					if (err) {
						console.log("Error while writing file");
					} else {
						console.log("Event written to " + user_path);
					}
			});
		}
		return;
	});
}

//returns specified user
exports.getEvent = function(name, response) {
	fs.readFile(event_path, function(err, events) {
		if (err) {
			console.log("error while reading " + event_path);
			response.write(null);
			response.end();
			return;
		} else {
			var event_list = JSON.parse(events.toString()).events;
			for (var i = 0; i < event_list.length; i++) {
				if (event_list[i].name == name) {
					console.log("found event " + name);
					console.log(JSON.stringify(event_list[i]));
					response.write(JSON.stringify(event_list[i]));
					response.end();
					return;
				}
			}
			console.log("couldn't find event " + name);
			response.write(null);
			return;
		}
		response.end();
		return;
	});
}

exports.addEvent = function(event) {
	fs.readFile(event_path, function(err, events) {
		if (err) {
			console.log("error while reading " + event_path);
		} else {
			console.log(JSON.stringify(event));
			var event_list = JSON.parse(events.toString()).events;
			event_list.push(event);
			fs.writeFile(event_path, 
				"{\"events\":" + JSON.stringify(event_list, null, 4) +"}", 
				function(err) {
					if (err) {
						console.log("Error while writing file");
					} else {
						console.log("Event written to " + event_path);
					}
			});
		}
		return;
	});
}

//returns specified user
exports.getAllEvents = function(response) {
	fs.readFile(event_path, function(err, events) {
		if (err) {
			console.log("error while reading " + event_path);
			response.write(null);
		} else {
			var event_list = JSON.parse(events.toString()).events;
			response.write(JSON.stringify(event_list));
			console.log(JSON.stringify(event_list));
		}
		response.end();
		return;
	});
}

//returns specified user
exports.deleteEvent = function(name) {
	fs.readFile(event_path, function(err, events) {
		if (err) {
			console.log("error while reading " + event_path);
			return;
		} else {
			var event_list = JSON.parse(events.toString()).events;
			for (var i = 0; i < event_list.length; i++) {
				if (event_list[i].name == name) {
					console.log("found event " + name);
					event_list.splice(i,1)
					fs.writeFile(event_path, 
						"{\"events\":" + JSON.stringify(event_list, 
						null, 4) +"}", function(err) {
							if (err) {
								console.log("Error while writing file");
							} else {
								console.log("Event deleted from " + event_path);
							}
					});
					return;
				}
			}
			console.log("couldn't find event " + name);
			return;
		}
		return;
	});
}