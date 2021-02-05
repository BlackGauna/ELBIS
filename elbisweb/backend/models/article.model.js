const mongoose = require('mongoose');

const Schema = mongoose.Schema;

const articleSchema = new Schema({
    title: {type: String, required: true},
    path: {type: String, required: true},
    expireDate: {type: Date, required: true},
    status: {type: String, required: true},
    topic: {type: String, required: true},
    author: {type: String, required: true},
    publisher: {type: String, required: false},
    publisherComment: {type: String, required: false},
    authorizeDate: {type: String, required: false},
}, {
    timestamps: true,
});

const Article = mongoose.model('Article', articleSchema);

module.exports = Article;
