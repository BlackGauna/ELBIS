import React from "react";
import { Component } from "react";
import { Editor } from "@tinymce/tinymce-react";
import axios from "axios";
import { Button, Form } from "react-bootstrap";
import bsCustomFileInput from "bs-custom-file-input";

//TODO: delete Images uploaded and unused(?)
//TODO: Resuse uploaded images (?)

export default class CreateArticle extends Component {
    constructor(props) {
        super(props);
        this.state = {
            filename: "",
        };
    }

    handleEditorChange = (content, editor) => {
        console.log("Content was updated:", content);
    };

    onChangeImage = (e) => {
        this.setState({
            filename: e.target.files[0],
        });
        console.log("Image change: ");
        console.log(e.target.files[0]);
        console.log(this.state.filename);
    };

    imageSubmit = (e) => {
        e.preventDefault();
        console.log("Submitted file: ");
        console.log(this.state.filename);
        const formData = new FormData();
        formData.append("image", this.state.filename);

        axios
            .post("/images/add", formData)
            .then((res) => {
                console.log(res.data.path);
            })
            .catch((err) => {
                console.log(err);
            });
    };

    editorImageSubmit = (e) => {
        e.preventDefault();
    };

    render() {
        return (
            <div>
                {// image upload form for testing
                    /*<Form
                    onSubmit={this.imageSubmit}
                    encType={"multipart/form-data"}
                    className={"mb-3 ml-2"}
                >
                    <Form.Group>
                        <Form.File
                            id={"customFile"}
                            type={"file"}
                            accept={".png, .jpg, .jpeg"}
                            name={"image"}
                            onChange={this.onChangeImage}
                            label={"Bild hochladen..."}
                            custom
                            className={"w-50"}
                        />
                    </Form.Group>
                    <Form.Group>
                        <Button variant={"primary"} type={"submit"} className={"mt-2"}>
                            Hochladen
                        </Button>
                    </Form.Group>

                    <script>
                        $(document).ready(function() {bsCustomFileInput.init()});
                    </script>
                </Form>*/}

                <Editor
                    apiKey="0pg6bjj3shae8ys7qwuzkwo6jba2p7i7bs6onheyzqlhswen"
                    init={{
                        //skin: "oxide-dark",
                        //content_css:"dark",
                        plugins: [
                            "advlist autoresize autolink lists link image charmap print preview anchor",
                            "searchreplace visualblocks code fullscreen",
                            "insertdatetime media table paste code help wordcount",
                        ],
                        toolbar:
                        // eslint-disable-next-line no-multi-str
                            "undo redo | formatselect | bold italic backcolor | \
                                            alignleft aligncenter alignright alignjustify | \
                                            bullist numlist outdent indent | removeformat | help",
                        toolbar_mode: "floating",
                        autoresize_bottom_margin:100,
                        min_height: 800,
                        file_picker_types: "image",
                        paste_data_images: true,
                        images_upload_handler: function (
                            blobInfo,
                            success,
                            failure,
                            progress
                        ) {
                            const formData = new FormData();
                            console.log("Blobinfo");
                            console.log(blobInfo.blob());

                            console.log("filename: " + blobInfo.filename());

                            formData.append("image", blobInfo.blob());

                            axios
                                .post("/images/add", formData)
                                .then((res) => {
                                    console.log(res.data);
                                    console.log("return: " + res.data.location);
                                    success(res.data);
                                })
                                .catch((err) => failure("Upload failed"));

                            /*const image = {
                              path: blobInfo.filename(),
                            };
                            console.log(image.path);
                            axios
                              .post("/files/add", image)
                              .then((res) => console.log(res.data));*/
                        },
                    }}
                    onEditorChange={this.handleEditorChange}
                />
            </div>
        );
    }
}
