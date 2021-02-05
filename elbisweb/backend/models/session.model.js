const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const sessionSchema = new Schema({

    token: {type: String, required: true,unique: true},
    userid: {type: String, required:true},
    email: {type: String, required: true},
    role: {type: String, required: true},
}, {
    timestamps: true,
});

Session = mongoose.model('Session', sessionSchema);

module.exports = Session;