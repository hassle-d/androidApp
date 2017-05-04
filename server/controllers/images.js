var fs = require('fs');
var Item = require('../models/item');
var Image = require('../models/image');

exports.postImage = function(req, res) {
    if (req.file) {
        images = null;
        Item.findById(req.body.itemId)
        .then(function(itm) {
            if (itm) {
                var image = new Image({
                    itemId: req.body.itemId,
                    filename: req.file.filename,
                    type: req.file.mimetype,
                });
                images = itm.images;
                return image.save();
            }
            else 
                res.status(400).json({message:"Pas d'item correspondant"});
        })
        .then(function(img) {
            images.push(img.filename);
            return Item.update({_id: req.body.itemId}, {images: images});
        })
        .then(function(itm) {
            return Item.findById(req.body.itemId);
        })
        .then(function(itm) {
            res.json(itm);
        })
        .catch(function(err) {
            res.status(500).json(err);
        })
    }
    else
        res.json({message:"Pas de ficher"});
};

exports.getImage = function(req, res) {
    var file = req.params.filename;
    console.log(file);
    Image.findOne({'filename': file})
    .then(function(img) {
        if (img) {
            console.log(img.itemId);
            var data = fs.readFileSync('./uploads/' + file);
            res.contentType(img.type);
            res.send(data);                
        }
        else
            res.status(404).json();
    })
    .catch(function(err) {
        res.json(err);
    })

    //fs.unlinkSync('./uploads/e72aca600d53aa37789740f692f72260')
};