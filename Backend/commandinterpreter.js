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