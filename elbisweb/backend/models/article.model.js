const mongoose = require('mongoose');

const Schema = mongoose.Schema;

const articleSchema = new Schema({
    //TODO Limits to variables (Trim,minlength etc)
    title: {type: String, required:  true},
    content: {type: String, required:  true},
    //TODO creation,expire and last edit date?
    //creationDate: {type: Date, required:  false},
    //expireDate: {type: Date, required:  false},
    //lastEdit: {type: Date, required:  false},
    status: {type: Number, required:  true},
    //TODO make sure the topicSchema import works
    topic: {type: topicSchema, required:  true},
    authorId: {type: Number, required:  true},
    publisherId: {type: Number, required:  false},
    publisherComment: {type: String, required:  false},

}, {
    timestamps: true,
});

const Article = mongoose.model('Article', articleSchema);

module.exports = Article;