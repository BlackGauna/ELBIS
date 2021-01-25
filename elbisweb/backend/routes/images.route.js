const express = require("express");
const router = express.Router();
// const cors = require("cors");
const multer = require("multer");
const path = require("path");

// image model
let Image = require("../models/image.model");
const fs = require("fs");

const storage = multer.diskStorage({
  // config destination for uploads
  destination: function (req, file, cb) {
    cb(null, "images");
  },
  // append the current datetime to filename to make every upload unique
  filename: function (req, file, cb) {
    cb(
      null,
      path.basename(file.originalname, path.extname(file.originalname)) +
        "-" +
        Date.now() +
        path.extname(file.originalname)
    );
  },
});

const fileFilter = (req, file, cb) => {
  const allowedFileTypes = ["image/jpeg", "image/jpg", "image/png"];
  if (allowedFileTypes.includes(file.mimetype)) {
    cb(null, true);
  } else {
    cb(null, false);
  }
};

let upload = multer({ storage, fileFilter });


/**
 * @route GET image
 */
router.route("/:filepath").get((req, res) => {
  Image.find({ filepath: req.params.filepath }).then((image) => {
    var im = image[0];
    console.log(im);
    console.log(im.filepath);
    res.contentType("image/jpeg");
    res.download("images/" + im.filepath);
  });
});

router.route("/:filepath").delete(((req, res) => {
  Image.findOneAndDelete({filepath: req.params.filepath})
      .then(image =>{
        console.log("DB entry deleted!")
        const filepath="images/"+ image.filepath;
        console.log("filepath: "+filepath);

        fs.unlink(filepath, (err => {
          if (err) throw err;

        }));
      })
      .catch(err => res.status(400).json('Error: '+err));
}));


/**
 * @route POST images
 * @desc upload an image
 */
router.route("/add").post(upload.single("image"), (req, res) => {
  const path = req.file.filename;

  console.log("backend path: ");
  console.log(path);

  const newImage = new Image({
    filepath: path,
  });

  var localIp = req.ip + ":" + req.socket.localPort;

  if (localIp.includes("::ffff:")) {
    localIp = localIp.substring(7);
  }
  localIp = "http://" + localIp + "/images/";

  var result = {
    location: localIp + path,
  };

  //console.log("IP: " + localIp);
  //console.log("result: ");
  //console.log(result);

  newImage
    .save()
    //.then((image) => res.json(image))
    .then(() => res.send(result.location))
    .catch((err) => res.status(400).json("Error: " + err));
});

module.exports = router;
