#!/bin/env node
//  OpenShift sample Node application
var express = require('express');
var fs      = require('fs');
var url     = require('url');
var cs      = require('./commandinterpreter.js');
var MongoClient = require('mongodb').MongoClient;

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
        self.connection_string = '127.0.0.1:27017/PickUplocations2';

        // if OPENSHIFT env variables are present, use the available connection info:
        if(process.env.OPENSHIFT_MONGODB_DB_PASSWORD){
          self.connection_string = process.env.OPENSHIFT_MONGODB_DB_USERNAME + ":" +
          process.env.OPENSHIFT_MONGODB_DB_PASSWORD + "@" +
          process.env.OPENSHIFT_MONGODB_DB_HOST + ':' +
          process.env.OPENSHIFT_MONGODB_DB_PORT + '/' +
          process.env.OPENSHIFT_APP_NAME;
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
        self.app.post('/event/', function(req, res) {
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
        self.app.delete('/event', function(req, res) {
            console.log("delete " + url.parse(req.url, true).path);
            var name = url.parse(req.url, true).query.name;
            res.writeHead(200);
            cs.deleteEvent(name);
            res.end();
        });

        /*
         * Express code to use module to get a user from the commandinterpreter
         */
        self.app.get('/user/', function(req, res) {
            console.log("get " + url.parse(req.url, true).path);
            var username = url.parse(req.url, true).query.username;
            res.writeHead(200);
            cs.getUser(username, res);
        });

        /*
         * Express code to use module to add a user from the commandinterpreter
         */
        self.app.post('/user/', function(req, res) {
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
        self.app.delete('/user/', function(req, res) {
            console.log("delete " + url.parse(req.url, true).path);
            var name = url.parse(req.url, true).query.name;
            res.writeHead(200);
            cs.deleteUser(name);
            res.end();
        });

                /*
         * Express code to use module to get a user from the commandinterpreter
         */
        self.app.get('/location/', function(req, res) {
            console.log("get " + url.parse(req.url, true).path);
            var location = url.parse(req.url, true).query.location;
            res.writeHead(200);
            cs.getLocation(location, res);
        });

        /*
         * Express code to use module to add a user from the commandinterpreter
         */
        self.app.post('/location/', function(req, res) {
            console.log("post " + url.parse(req.url, true).path);
            res.writeHead(201);
            req.on('data', function(chunk) {
                cs.addLocation(JSON.parse(chunk.toString()));
            });
            res.end();
        });

        /*
         * Express code to use module to add a user from the commandinterpreter
         */
        self.app.delete('/location/', function(req, res) {
            console.log("delete " + url.parse(req.url, true).path);
            var location = url.parse(req.url, true).query.location;
            res.writeHead(200);
            cs.deleteLocation(location, res);
            res.end();
        });
    };

    self.findDB = function() {
        MongoClient.connect('mongodb://'+self.connection_string, function(err, db) {
          if(err) throw err;
          self.events_c = db.collection('events').find().limit(10).toArray(function(err, docs) {
            console.dir(docs);
            db.close();
          })
        })
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

        //acquire collections
        //self.findDB();
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

}; 



/**
 *  main():  Main code.
 */
var zapp = new PickUplocations2();
zapp.initialize();
zapp.start();

