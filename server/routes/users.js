/*
** Created by HASSLER Damien
*/

var express = require('express');
var router = express.Router();
var passport = require('passport');
var userController = require('../controllers/users');
var authController = require('../controllers/auth');

router.route('/user')
	.post(userController.postUsers)
	.get(authController.isValidToken, userController.getProfil)
	.put(authController.isValidToken, userController.updateProfil)

module.exports = router;
