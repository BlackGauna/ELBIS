const mongoose = require('mongoose');

const Schema = mongoose.Schema;

const articleSchema = new Schema({
    //TODO Limits to variables (Trim,minlength etc)
    title: {type: String, required:  true},
    content: {type: String, required:  true},
    //TODO creation,expire and last edit date?
    //creationDate: {type: Date, required:  false},
    // expireDate: {type: Date, required:  false},
    //lastEdit: {type: Date, required:  false},
    // status: {type: Number, required:  true},
    status: {type: String, required: true},
    //TODO make sure the topicSchema import works
    // topic: {type: mongoose.Schema.Types.ObjectId, ref: 'Topic', required:  true},
    topic: {type: String, required: true},
    author: {type: String, required: true},
    publisher: {type: String, required: false},
    // authorId: {type: Number, required:  true},
    // publisherId: {type: Number, required:  false},
    publisherComment: {type: String, required:  false},

}, {
    timestamps: true,
});

const Article = mongoose.model('Article', articleSchema);

module.exports = Article;