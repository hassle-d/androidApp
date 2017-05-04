/*
** Created by HASSLER Damien
*/

var express = require('express');
var router = express.Router();
var userController = require('../controllers/users');
var authController = require('../controllers/auth');

router.route('/auth/login')
	.post(authController.getNewToken);

router.route('/auth/logout')
	.get(authController.logout);

router.route('/auth/token')
	.get(authController.checkToken);

module.exports = router;
