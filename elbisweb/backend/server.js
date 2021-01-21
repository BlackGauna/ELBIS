const express = require('express');
const cors = require('cors');
const mongoose = require('mongoose');

require('dotenv').config();

const app = express();
const port = process.env.PORT || 5000;

app.use(cors());
app.use(express.json());

const uri = "mongodb+srv://admin:admin@elbis8.ilafq.mongodb.net/ELBIS?retryWrites=true&w=majority";
mongoose.connect(uri, {useNewUrlParser: true, useCreateIndex: true, useUnifiedTopology: true}
);
mongoose.connection.once('open', () => {
    console.log("MongoDB connected");
})

const userRouter = require('./routes/user.route');
const articleRouter = require('./routes/article');
const topicRouter = require('./routes/topic');
const roleRouter = require('./routes/role');
const genderRouter = require('./routes/gender');
const statusRouter = require('./routes/status');

app.use('/user', userRouter);
app.use('/article', articleRouter);
app.use('/topic', topicRouter);
app.use('/role', roleRouter);
app.use('/gender', genderRouter);
app.use('/status', statusRouter);

app.listen(port, () => {
    console.log(`Server is running on port: ${port}`);
})