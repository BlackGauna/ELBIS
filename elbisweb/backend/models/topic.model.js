const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const topicSchema = new Schema({
    //TODO Limits to variables (Trim,minlength etc)
    name: {type: String, required:  true},
    //TODO make sure the topicSchema import works
    ParentTopic: {type: topicSchema, required:  true},
}, {
    timestamps: true,
});

Topic = mongoose.model('Topic', topicSchema);

module.exports = Topic;