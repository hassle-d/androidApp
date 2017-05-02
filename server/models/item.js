var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var ItemSchema = new mongoose.Schema({
    authorId: {
    	type: String, 
    	required: true
    },
    alias: {
        type: String,
        required: true
    },
    dateBuy: {
        type: Date,
        required: true
    },
    dateEnd: {
        type: Date,
        required: true
    },
    dateNotification: {
        type: Date,
        required: true
    },
    dateCreation: {
        type: Date,
        required: true
    },
    duration: {
        type: String,
        required: true
    },
    emailNotification: {
        type: Boolean,
        required: true
    },
    smsNotification: {
        type: Boolean,
        required: true
    },
	images: {
		type: [String],
		required: false
	}
});


module.exports = mongoose.model('Item', ItemSchema);