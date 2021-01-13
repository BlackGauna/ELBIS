const express = require('express');
const cors = require('cors');
const mongoose = require('mongoose');

require('dotenv').config();

const app = express();
const port = process.env.PORT || 5000;

app.use(cors());
app.use(express.json());

const uri = "mongodb+srv://admin:admin@elbis8.ilafq.mongodb.net/ELBIS?retryWrites=true&w=majority";
mongoose.connect(uri, { useNewUrlParser: true, useCreateIndex: true, useUnifiedTopology: true}
);
const connection = mongoose.connection;
connection.once('open', () => {
    console.log("MongoDB connected");
})

const userRouter = require('./routes/user');

app.use('/user', userRouter);

app.listen(port, () => {
    console.log(`Server is running on port: ${port}`);
})