/*
** Created by HASSLER Damien
*/

//-S-- CONFIG CONSTANTS VARS
var dbServer	= 'localhost';
var dbPort		= '27017';
var dbName		= 'garanT';
var apiVersion 	= 1;
var done		= false;
//-E-- CONFIG CONSTANTS VARS

var express		= require('express');
var path 		= require('path');
var session 	= require('express-session');
var bodyParser 	= require('body-parser');
var mongoose 	= require('mongoose');
mongoose.Promise = require('bluebird');
var passport 	= require('passport');
var multer		= require('multer');

var port 		= process.env.PORT || 3000;
var app 		= express();
var router 		= express.Router();

//-S-- ROUTES FILES
var user 		= require('./routes/users');
var item 		= require('./routes/item');
var auth		= require('./routes/auth');
var token		= require('./routes/token');
//-E-- ROUTES FILES

mongoose.connect('mongodb://' + dbServer + ':' + dbPort + '/' + dbName);

app.use(multer({dest:'./uploads/'}).single('image'));

app.use(bodyParser.json({limit: '50mb'}));
app.use(bodyParser.urlencoded({limit: '50mb', extended: true}));

app.use(session({
	secret: 'lama',
	saveUninitialized: true,
	resave: true
}));

app.use(passport.initialize());
app.use(passport.session());
app.use('/api',
	user,
	item,
	auth,
	token,
	router);

app.listen(port);
