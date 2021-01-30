const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');
const Schema = mongoose.Schema;

const userSchema = new Schema({

    email: {type: String, required:  true,unique: true, trim:true, minlength: 5},
    password: {type: String, required:  true},
    role: {type: String, required:  false},
    name: {type: String, required:  true},
    address: {type: String, required:  false},
    gender: {type: String, required:  false},
    dateOfBirth: {type: Date, required:  false},
    allowedTopics: [{
        type: String, required: false
    }]
}, {
    timestamps: true,
});

userSchema.pre("save", async function (next) {
const user = this;

if (user.isModified("password")) {
    user.password = await bcrypt.hash(user.password, 10);
}
});

const User = mongoose.model('User', userSchema);

module.exports = User;