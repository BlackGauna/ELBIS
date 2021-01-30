const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const userTopicSchema = new Schema({
    //TODO Limits to variables (Trim,minlength etc)
    email: {type: String, required:  true},
    topic: {type: String, required: true},
}, {
    timestamps: true,
});

UserTopic = mongoose.model('UserTopic', userTopicSchema);

module.exports = UserTopic;