const express = require('express');
const cors = require('cors');
const mongoose = require('mongoose');
const Article = require("./models/article.model");
const cron = require('node-cron');
require('dotenv').config();

const app = express();

cron.schedule('* * * * *', function() {
    console.log('Database Refresh with routines');
    Article.updateMany({"expireDate":{"$lte":now}},{"$set":{"status":"Archiviert"}},{"multi":true},(err, writeResult) => {});
    Article.deleteMany({"path":{"$eq":""}},{"multi":true},(err) => {});
    Article.updateMany({"status":"Autorisiert"},{"$set":{"status":"Öffentlich"}},{"multi":true},(err, writeResult) => {});
});

const port = process.env.PORT || 5000;
let now = new Date().toISOString();

app.use(cors());
app.use(express.json());



const uri = "mongodb+srv://admin:admin@elbis8.ilafq.mongodb.net/ELBIS?retryWrites=true&w=majority";
mongoose.connect(uri, {useNewUrlParser: true, useCreateIndex: true, useUnifiedTopology: true});
mongoose.connection.once('open', () => {
    console.log("MongoDB connected");
})





const userRouter = require('./routes/user.route');
const articleRouter = require('./routes/article.route');
const topicRouter = require('./routes/topic.route.');
const roleRouter = require('./routes/role.route');
const genderRouter = require('./routes/gender.route');
const statusRouter = require('./routes/status.route');
const userTopicRouter = require('./routes/userTopic.route');
const images = require("./routes/images.route");
const sessionRouter = require('./routes/session.route');


app.use('/user', userRouter);
app.use('/article', articleRouter);
app.use('/topic', topicRouter);
app.use('/role', roleRouter);
app.use('/gender', genderRouter);
app.use('/status', statusRouter);
app.use('/userTopic', userTopicRouter);
app.use("/images", images);
app.use('/session', sessionRouter);

app.listen(port, () => {
    console.log(`Server is running on port: ${port}`);
})