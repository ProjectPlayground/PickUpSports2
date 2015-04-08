#!/bin/env node
//  OpenShift sample Node application
var express     = require('express');
var fs          = require('fs');
var url         = require('url');
var ObjectID    = require('mongodb').ObjectID;
var MongoClient = require('mongodb').MongoClient;
var Soundex 	= require('soundex')

/**
 *  Define the sample application.
 */
var PickUplocations2 = function() {

    //  Scope.
    var self = this;


    /*  ================================================================  */
    /*  Helper functions.                                                 */
    /*  ================================================================  */

    /**
     *  Set up server IP address and port # using env variables/defaults.
     */
    self.setupVariables = function() {
        //  Set the environment variables we need.
        self.ipaddress = process.env.OPENSHIFT_NODEJS_IP;
        self.port      = process.env.OPENSHIFT_NODEJS_PORT || 8080;

        //mongodb configuration
        self.mongoHost = process.env.OPENSHIFT_MONGODB_DB_HOST;
        self.mongoPort = process.env.OPENSHIFT_MONGODB_DB_PORT || 27017;

        if (typeof self.ipaddress === "undefined") {
            //  Log errors on OpenShift but continue w/ 127.0.0.1 - this
            //  allows us to run/test the app locally.
            console.warn('No OPENSHIFT_NODEJS_IP var, using 127.0.0.1');
            self.ipaddress = "127.0.0.1";
        };

        // default to a 'localhost' configuration:
        //127.12.206.130:27017
        //127.0.0.1:27017
        self.connection_string = '127.0.0.1:27017/PickUpSports2';

        // if OPENSHIFT env variables are present, use the available connection info:
        if(process.env.OPENSHIFT_MONGODB_DB_PASSWORD){
            self.connection_string = 
            process.env.OPENSHIFT_MONGODB_DB_USERNAME + ":" +
            process.env.OPENSHIFT_MONGODB_DB_PASSWORD + "@" +
            process.env.OPENSHIFT_MONGODB_DB_HOST + ':' +
            process.env.OPENSHIFT_MONGODB_DB_PORT + '/' +
            "PickUpSports2";
        }
    };


    /**
     *  Populate the cache.
     */
    self.populateCache = function() {
        if (typeof self.zcache === "undefined") {
            self.zcache = { 'index.html': '' };
        }

        //  Local cache for static content.
        self.zcache['index.html'] = fs.readFileSync('./index.html');
    };


    /**
     *  Retrieve entry (content) from cache.
     *  @param {string} key  Key identifying content to retrieve from cache.
     */
    self.cache_get = function(key) { return self.zcache[key]; };


    /**
     *  terminator === the termination handler
     *  Terminate server on receipt of the specified signal.
     *  @param {string} sig  Signal to terminate on.
     */
    self.terminator = function(sig){
        if (typeof sig === "string") {
            console.log('%s: Received %s - terminating sample app ...',
                Date(Date.now()), sig);
            process.exit(1);
        }
        self.db.close();
        console.log('%s: Node server stopped.', Date(Date.now()) );
    };


    /**
     *  Setup termination handlers (for exit and a list of signals).
     */
    self.setupTerminationHandlers = function(){
        //  Process on exit and signals.
        process.on('exit', function() { self.terminator(); });

        // Removed 'SIGPIPE' from the list - bugz 852598.
        ['SIGHUP', 'SIGINT', 'SIGQUIT', 'SIGILL', 'SIGTRAP', 'SIGABRT',
            'SIGBUS', 'SIGFPE', 'SIGUSR1', 'SIGSEGV', 'SIGUSR2', 'SIGTERM'
        ].forEach(function(element, index, array) {
            process.on(element, function() { self.terminator(element); });
        });
    };


    /*  ================================================================  */
    /*  App server functions (main app logic here).                       */
    /*  ================================================================  */

    /**
     *  Create the routing table entries + handlers for the application.
     */
    self.createRoutes = function() {
        self.routes = { };

        self.routes['/'] = function(req, res) {
            res.setHeader('Content-Type', 'text/html');
            res.send(self.cache_get('index.html') );
        };
    };


    /**
     *  Initialize the server (express) and create the routes and register
     *  the handlers.
     */
    self.initializeServer = function() {
        self.createRoutes();
        self.app = express();
        self.app.use(express.bodyParser());

        self.app.get('/', function(req, res) {
            res.writeHead(200);
            res.write("Server Running");
            res.end();
        });

        /*
         * Express code to use module to delete an event from the commandinterpreter
         * uses a query filter field to indicate the breadth of the 'get' call
         */
        self.app.get('/event/', function(req, res) {
            console.log("get " + url.parse(req.url, true).path);
            var filter = url.parse(req.url, true).query.filter;
            switch (filter) {
                case 'none': 
                    self.getFromDBAndSort('events', null, {'timeLong':1},
                    	function(err, data) {
                        if (err) {
                            console.log(err);
                            res.send(err);
                        } else {
                            console.log(data);
                            res.json(data);
                        }
                    });
                    break;
                case 'id': 
                    var id = url.parse(req.url, true).query.id;
                    self.getFromDB('events', {'_id': new ObjectID(id)},
                    	function(err, data) {
                        if (err) {
                            console.log(err);
                            res.send(err);
                        } else {
                            res.json(data[0]);
                        }
                    });
                    break;
                case 'name':
                    var name = url.parse(req.url, true).query.name;
                    var search_query = 'this.name.toLowerCase()  == \'' + name.toLowerCase().trim() + '\'';
                    console.log(search_query);
                    self.getFromDB('events', { '$where' : search_query}, function(err, data) {
                        if (err) {
                            console.log(err);
                            res.send(err);
                        } else {
                            console.log(data);
                            res.json(data);
                        }
                    });
                    break;
                case 'dateRange':
                    var date1 = Number(url.parse(req.url, true).query.date1);
                    var date2 = Number(url.parse(req.url, true).query.date2);
                    //date 1 and 2
                    if (date1 < date2) {
                        var temp = date1;
                        date2 = date1;
                        date1 = temp;
                    }
                    console.log("greater than" + date2);
                    console.log("less than" + date1);
                    self.getFromDB('events', {'timeLong': {'$gte': date2, '$lte': date1ls}}, {'timeLong':1},
                    	function(err, data) {
                    		if (err) {
	                            console.log(err);
	                            res.send(err);
	                        } else {
	                            console.log(data);
	                            res.json(data);
	                        }
                    	});
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
        self.app.post('/event/', function(req, res) {
            if (url.parse(req.url, true).query.type == "new") {
                console.log("post " + url.parse(req.url, true).path);    
                self.addToDB('events', req.body, function(err, result) {
                    if(err) {
                        console.log(err);
                    } else {
                        res.send(result[0]);
                    }
                });
            } else if (url.parse(req.url, true).query.type == "existing") {
                self.replaceInDB('events', req.body, function(err, result) {
                    if(err) {
                        console.log(err);
                    } else {
                        console.log("replace successful");
                        res.send(result[0]);
                    }
                });
            }
        });

        /*
         * Express code to use module to delete an event from the commandinterpreter
         */
        self.app.delete('/event/', function(req, res) {
            console.log("delete " + url.parse(req.url, true).path);
            var id = url.parse(req.url, true).query.id;
            var attendees = (url.parse(req.url, true).query.attendees).split(",");
            for (var i = 0; i < attendees.length; ++i) {
                self.updateFieldInDB('users', {'_id': new ObjectID(attendees[i])},
                    {'$pull' : {'attendedEvents' : id}}, 
                    function(err, result) {
                        if(err) {
                            console.log(err);
                    }
                });
            }
            
            self.deleteFromDB('events', {'_id': new ObjectID(id)}, function(err, result) {
                if (err) {
                    console.log(err);
                    res.send(err);
                    res.status(400).end();
                } else {
                    res.status(200).end();
                    console.log(result);
                }
            });
        });

        /*
         * Express code to use module to get a user from the commandinterpreter
         */
        self.app.get('/user/', function(req, res) {
            var id = url.parse(req.url, true).query.id;
            //id is from facebook
            if (url.parse(req.url, true).query.id_type == "fb") {
                self.getFromDB('users', {'fb_id': id}, function(err, data) {
                    if (err) {
                        console.log(err);
                        res.send(err);
                    } else {
                        console.log(data[0]);
                        res.json(data[0]);
                    }
                });
            } else if (url.parse(req.url, true).query.id_type == "ps2") {
                //pickupsportsid
                console.log(id);
                self.getFromDB('users', {'_id': new ObjectID(id)}, function(err, data) {
                    if (err) {
                        console.log(err);
                        res.send(err);
                    } else {
                        console.log(data[0]);
                        res.json(data[0]);
                    }
                });
            } else if (id == null) {
                var name = url.parse(req.url, true).query.name;
                if (name != null) {
                    var search_query = 'this.firstname.toLowerCase() + \" \" + this.lastname.toLowerCase()  == \'' 
                        + name.toLowerCase().trim() + '\'';
                    console.log(search_query);
                    //
                    self.getFromDB('users', { '$where' : search_query}, function(err, data) {
                        if (err) {
                            console.log(err);
                            res.send(err);
                        } else {
                            console.log(data);
                            res.json(data);
                        }
                    });
                }
            }
        });

        /*
         * Express code to use module to add a user from the commandinterpreter
         */
        self.app.post('/user/', function(req, res) {
            if (url.parse(req.url, true).query.type == "new") {
                console.log("post " + url.parse(req.url, true).path);        
                self.addToDB('users', req.body, function(err, result) {
                    if(err) {
                        console.log(err);
                    } else {
                        res.send(result[0]);
                    }
                });
            } else if (url.parse(req.url, true).query.type == "existing") {
                self.replaceInDB('users',req.body, function(err, result) {
                    if(err) {
                        console.log(err);
                    } else {
                        console.log("replace successful");
                        res.send(result[0]);
                    }
                });
            }
        });

        /*
         * Express code to use module to add a user from the commandinterpreter
         */
        self.app.delete('/user/', function(req, res) {
            console.log("delete " + url.parse(req.url, true).path);
            var id = url.parse(req.url, true).query.id;
            self.deleteFromDB('users', {'_id': new ObjectID(id)}, function(err, result) {
                if (err) {
                    console.log(err);
                    res.send(err);
                } else {
                    console.log(result);
                }
            });
        });

        /*
         * Express code to use module to get a sport from the commandinterpreter
         */
        self.app.get('/sport/', function(req, res) {
            var id = url.parse(req.url, true).query.id;
            self.getFromDB('sports', {'_id': new ObjectID(id)},
            	function(err, data) {
                if (err) {
                    console.log(err);
                    res.send(err);
                } else {
                    console.log(data[0]);
                    res.json(data[0]);
                }
            });
        });

        /*
         * Express code to use module to add a sport from the commandinterpreter
         */
        self.app.post('/sport/', function(req, res) {
            console.log("post " + url.parse(req.url, true).path); 
            self.addToDB('sports', req.body, function(err, result) {
                if(err) {
                    console.log(err);
                } else {
                    res.send(result[0]);
                }
            });
        });

        /*
         * Express code to use module to add a sport from the commandinterpreter
         */
        self.app.delete('/sport/', function(req, res) {
            console.log("delete " + url.parse(req.url, true).path);
            var id = url.parse(req.url, true).query.id;
            self.deleteFromDB('sports', {'_id': new ObjectID(id)}, function(err, result) {
                if (err) {
                    console.log(err);
                    res.send(err);
                } else {
                    console.log(result);
                }
            });
        });

        /*
         * Express code to use module to get a user from the commandinterpreter
         */
        self.app.get('/location/', function(req, res) {
            var id = url.parse(req.url, true).query.id;
            self.getFromDB('locations', {'_id': new ObjectID(id)},
            	function(err, data) {
                if (err) {
                    console.log(err);
                    res.send(err);
                } else {
                    console.log(data[0]);
                    res.json(data[0]);
                }
            });
        });

        /*
         * Express code to use module to add a user from the commandinterpreter
         */
        self.app.post('/location/', function(req, res) {
            console.log("post " + url.parse(req.url, true).path);
            self.addToDB('locations', req.body, function(err, result) {
                if(err) {
                    console.log(err);
                } else {
                    res.send(result[0]);
                }
            });
        });

        /*
         * Express code to use module to add a user from the commandinterpreter
         */
        self.app.delete('/location/', function(req, res) {
            console.log("delete " + url.parse(req.url, true).path);
            var id = url.parse(req.url, true).query.id;
            self.deleteFromDB('locations', {'_id': new ObjectID(id)}, function(err, result) {
                if (err) {
                    console.log(err);
                    res.send(err);
                } else {
                    console.log(result);
                }
            });
        });

        /*
         * Express code to use module to get a invite for a user
         */
        self.app.get('/invitation/', function(req, res) {
            var filter = url.parse(req.url, true).query.filter;
            if (filter == "id") {
                var id = url.parse(req.url, true).query.id;
                self.getFromDB('invitations', {'_id': new ObjectID(id)},
                    function(err, data) {
                    if (err) {
                        console.log(err);
                        res.send(err);
                    } else {
                        console.log(data[0]);
                        res.json(data[0]);
                    }
                });
            } else if (filter == "user") {
                var user_id = url.parse(req.url, true).query.user_id;
                self.getFromDB('invitations', {'invitee_id': user_id},
                    function(err, data) {
                    if (err) {
                        console.log(err);
                        res.send(err);
                    } else {
                        console.log(data);
                        res.json(data);
                    }
                });
            }
        });

        /*
         * Express code to use module to add a invite from the commandinterpreter
         */
        self.app.post('/invitation/', function(req, res) {
            console.log("post " + url.parse(req.url, true).path); 
            self.addToDB('invitations', req.body, function(err, result) {
                if(err) {
                    console.log(err);
                    res.send(err);
                } else {
                    res.send("result");
                }
            });
        });

        /*
         * Express code to use module to delete a invite from the commandinterpreter
         */
        self.app.delete('/invitation/', function(req, res) {
            console.log("delete " + url.parse(req.url, true).path);
            var invitation_id = url.parse(req.url, true).query.invitation_id;
            self.deleteFromDB('invitations', {'_id': new ObjectID(invitation_id)}, function(err, result) {
                if (err) {
                    console.log(err);
                    res.send(err);
                } else {
                	res.send(result);
                    console.log(result);
                }
            });
        });

        /*
         * Express code to use module to edit attendance information
         */
        self.app.post('/attendance/', function(req, res) {
            var event_id = url.parse(req.url, true).query.event_id;
            var user_id = url.parse(req.url, true).query.user_id;
            
            if (url.parse(req.url, true).query.type == "add") {
                console.log("Adding Event " + event_id + " to User " + user_id);
                self.updateFieldInDB('users', {'_id': new ObjectID(user_id)},
                    {'$addToSet' : {'attendedEvents' : event_id}}, 
                    function(err, result) {
                    if(err) {
                        console.log(err);
                    } else {
                        res.send(result[0]);
                    }
                });
                self.updateFieldInDB('events', {'_id': new ObjectID(event_id)},
                    {'$addToSet' : {'attendees' : user_id}}, 
                    function(err, result) {
                    if(err) {
                        console.log(err);
                    } else {
                        res.send(result[0]);
                    }
                });
            } else if (url.parse(req.url, true).query.type == "remove") {
                console.log("Removing Event " + event_id + " to User " + user_id);
                self.updateFieldInDB('users', {'_id': new ObjectID(user_id)},
                    {'$pull' : {'attendedEvents' : event_id}}, 
                    function(err, result) {
                    if(err) {
                        console.log(err);
                    } else {
                        res.send(result[0]);
                    }
                });
                self.updateFieldInDB('events', {'_id': new ObjectID(event_id)},
                    {'$pull' : {'attendees' : user_id}}, 
                    function(err, result) {
                    if(err) {
                        console.log(err);
                    } else {
                        res.send(result[0]);
                    }
                });

            }
        });
    };

    /*
     * Get from Database
     */
    self.getFromDB = function(collection, object, callback) {
        console.log('attempt to get ' + object._id);
        self.db.collection(collection).find(object).toArray(callback);
    }

    /*
     * Get from Database and Sort
     */
    self.getFromDBAndSort = function(collection, object, sortby, callback) {
        self.db.collection(collection).find(object).sort(sortby).toArray(callback);
    }

    self.searchInDB = function(collection, soundex_sentence, callback) {
    	console.log("Searching for " + soundex_sentence + " in " + collection);

    }

    /*
     * Add to Database
     */
    self.addToDB = function(collection, object, callback) {
        console.log('attempt to insert ' + object._id);
        self.db.collection(collection).insert(object, callback);
    }

    /*
     * Replace existing item in Database by ObjectId
     */
    self.replaceInDB = function(collection, object, callback) {
        console.log('attempt to replace ' + object._id);
        var object_id = object._id;
        delete object._id;
        self.db.collection(collection).update({'_id': new ObjectID(object_id)}, 
            object, callback);
    }

    /*
     * Update field of existing item in Database by ObjectId
     */
    self.updateFieldInDB = function(collection, object, updates, callback) {
        console.log('attempt to update ' + object._id);
        self.db.collection(collection).update(object, 
            updates, callback);
    }

    /*
     * Delete from Database
     */
    self.deleteFromDB = function(collection, object, callback) {
        console.log('attempt to delete ' + object._id);
        self.db.collection(collection).remove(object, callback);
    }

    /*
     * Connect to Database
     */
    self.connectDB = function() {
        MongoClient.connect('mongodb://' + self.connection_string, function(err, db) {
              if(err) {
                  console.log("unable to connect to database");
                  throw err;
              }
              self.db = db;
        });
    }

    /**
     *  Initializes the sample application.
     */
    self.initialize = function() {
        self.setupVariables();
        self.populateCache();
        self.setupTerminationHandlers();

        // Create the express server and routes.
        self.initializeServer();

        // Connect Database
        self.connectDB();
    };

    /**
     *  Start the server
     */
    self.start = function() {
        //  Start the app on the specific interface (and port).
        self.app.listen(self.port, self.ipaddress, function() {
            console.log('%s: Node server started on %s:%d ...',
                Date(Date.now() ), self.ipaddress, self.port);
        });
    };
/*
    self.generateFuzzyList = function(sentence) {
    	var words = sentence.split(" ");
    	var output = [];
    	for (var i = 0; i < words.length; ++i) {
    		output.push(Soundex(words[i]));
    	}
    	return output;
    }

    self.matchFuzzySentence = function(sent1, sent2) {
    	if (sent1 === "" || sent2 == "" || sent1 == null || sent2 == null) {
    		return false;
    	}
    	var matchcount = 0;
    	for (var i = 0; i < sent1.length; ++i) {
    		for (var j = 0; j < sent2.length; ++j) {
    			if (sent1[i] === sent2[j]){
    				matchcount += 1;
    				break;
    			}
    		}
    	}
    	if (matchcount >= sent1.length/2) {
    		return true;
    	} else {
    		return false;
    	}
    }
    */
}; 


/**
 *  main():  Main code.
 */
var zapp = new PickUplocations2();
zapp.initialize();
zapp.start();

