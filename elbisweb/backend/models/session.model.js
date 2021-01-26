const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const sessionSchema = new Schema({

    token: {type: String, required: true,unique: true},
    email: {type: String, required: true},
    //TODO expire date so that the person gets logged out after a day
    //expiredate: {type: Date, required: true}
    role: {type: String, required: true},
}, {
    timestamps: true,
});

Session = mongoose.model('Session', sessionSchema);

module.exports = Session;