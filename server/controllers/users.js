/*
** Created by HASSLER Damien
*/

var User = require('../models/users');

exports.postUsers = function(req, res) {
	var error = false;
	console.log(req);
	if (req.body.password.length < 8)
		{ error = true; res.status(400).json({name: 'ValidationError', message: 'PasswordTooShort'}); }
	if (!error) {
		var user = new User({
			username: req.body.username.toLowerCase(),
			password: req.body.password,
			email: req.body.email,
			date: new Date()
		});
		user.save(function(err, user) {
			if (err)
				if (err.name && err.name == 'ValidationError')
					res.status(400).json({name: 'ValidationError', message: 'MissingFields'});
				else if (err.code && err.code == 11000)
					res.status(400).json({name: 'ValidationError', message: 'UsernameAlreadyExist'});
				else
					res.status(409).send(err);
			else
				res.status(201).json({message: 'User added'});
		});
	}
};

exports.getProfil = function(req, res) {
	User.findById(req.user.userId, function(err, user) {
		if (err)
			res.status(500).send(err);
		else
			res.json(user);
	});	
};

exports.updateProfil = function(req, res) {
	if (!req.user.userId) { res.status(400).json({name: 'ValidationError', message: 'MissingFields'}); return;}
	var updateFields = {};
	if (req.body.password)
		updateFields.password = req.body.password;
	if (req.body.email)
		updateFields.email = req.body.email;
	console.log(updateFields);
	User.update({_id: req.user.userId}, updateFields, function (err, doc){
		if (err) { res.json(err); return; }
		res.json(doc);
	});
};
