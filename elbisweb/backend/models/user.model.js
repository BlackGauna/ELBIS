const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');
const Schema = mongoose.Schema;

const userSchema = new Schema({

    email: {type: String, required:  true,unique: true, trim:true, minlength: 5},
    password: {type: String, required:  false},
    role: {type: String, required:  false},
    fName: {type: String, required: false},
    lName: {type: String, required: false},
    street: {type: String, required: false},
    hNumber: {type: String, required: false},
    plz: {type: String, required: false},
    city: {type: String, required: false},
    gender: {type: String, required:  false},
    dateOfBirth: {type: Date, required:  false},
    // allowedTopics: [{
    //     type: String, required: false
    // }]
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