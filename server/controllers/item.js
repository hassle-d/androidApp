/*
** Created by HASSLER Damien
*/

var fs = require('fs');
var Item = require('../models/item');
var Image = require('../models/image');

exports.postItem = function(req, res) {
    var date1 = new Date(req.body.dateBuy + "T00:00:00.000Z");
    var date2 = new Date(req.body.dateEnd + "T00:00:00.000Z");
    var date3 = new Date(req.body.dateNotification + "T00:00:00.000Z");
    var timeDiff = date2.getTime() - date1.getTime();
    var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24)); 
    console.log(timeDiff);
    console.log(req.body.dateBuy);

    var item = new Item({
        authorId: req.user.userId,
        alias: req.body.alias.toLowerCase(),
        dateBuy: date1,
        dateEnd: date2,
        dateNotification: date3,
        dateCreation: new Date().toISOString(),
        duration: diffDays,
        emailNotification: req.body.emailNotification,
        smsNotification: req.body.smsNotification,
        images: []
    });
    item.save()
    .then(function(item) {
        res.status(201).send(item);
    })
    .catch(function(err) {
        res.status(400).send(err);
    })
};

exports.updateItem = function(req, res) {
    var updateFields = {};

    var date1 = new Date(req.body.dateBuy + "T00:00:00.000Z");
    var date2 = new Date(req.body.dateEnd + "T00:00:00.000Z");
    var date3 = new Date(req.body.dateNotification + "T00:00:00.000Z");
    var timeDiff = date2.getTime() - date1.getTime();
    var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24)); 

    updateFields.alias =  req.body.alias.toLowerCase();
    updateFields.dateBuy = date1;
    updateFields.dateEnd = date2;
    updateFields.dateNotification = date3;
    updateFields.duration = diffDays;
    updateFields.emailNotification = req.body.emailNotification;
    updateFields.smsNotification = req.body.smsNotification;

    Item.update({_id: req.params.id}, updateFields)
    .then(function(doc) {
        res.json(doc);
    })
    .catch(function(err) {
        res.status(400).json(err);
    })


};

exports.getItems = function(req, res){
    console.log(req.user);
    var options = null;
    var opsort = "dateEnd";
    if (req.query.orderby) {
        var field = req.query.orderby;
        var order = '';
        if (req.query.order == 'desc')
            order = '-';
        opsort = order+field;
        console.log(opsort);
    }
    if (req.query.overview !== undefined)
        options = 'alias dateBuy dateEnd dateNotification duration images'
    Item.find({'authorId':req.user.userId}, options, {sort: opsort})
    .then(function(items) {
        res.json(items);
    })
    .catch(function(err) {
        res.status(500).json(err);
    })
};

exports.getItemById = function(req, res){
    console.log(req.user);

    Item.findOne({'_id':req.params.id, 'authorId':req.user.userId})
    .then(function(itm) {
        res.json(itm);
    })
    .catch(function(err) {
        res.status(500).json(err);
    })
};