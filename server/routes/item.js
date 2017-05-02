/**
 * Created by Yilmaz Emrah on 30/10/2015.
 */

var express = require('express');
var router = express.Router();
var multer = require('multer');
var upload = multer({ dest: '../uploads/'});

var imageController = require('../controllers/images');
var itemController = require('../controllers/item');
var authController = require('../controllers/auth');

router.route('/item')
	.post(authController.isValidToken, itemController.postItem)
	.get(authController.isValidToken, itemController.getItems);

router.route('/item/:id')
	.get(authController.isValidToken, itemController.getItemById)
	.put(authController.isValidToken, itemController.updateItem);

router.route('/image')
	.post(authController.isValidToken, imageController.postImage);

router.route('/image/:filename')
	.get(imageController.getImage);

module.exports = router;