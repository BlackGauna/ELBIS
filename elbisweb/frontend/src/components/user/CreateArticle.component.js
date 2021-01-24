import React from "react";
import ReactDOM from "react-dom";
import { Component } from "react";
import { Editor } from "@tinymce/tinymce-react";
import axios from "axios";
import parse from 'html-react-parser';
import {Button, Container, Form, FormControl, FormGroup, FormLabel} from "react-bootstrap";
import bsCustomFileInput from "bs-custom-file-input";
import loggedUser from "../../session/loggedUser";
import {BrowserRouter as Router, Route} from "react-router-dom";

//TODO: delete Images uploaded and unused(?)
//TODO: Resuse uploaded images (?)

// TODO: set topic, author (etc.) from current user

// TODO: if topic, title changes, rename save html file

export default class CreateArticle extends Component {
    constructor(props) {
        super(props);

        this.state = {
            imageFilename: "",
            showPreview:false,
            html:"",
            title:"unnamed",
            oldTitle:"",
            topic:"unnamed",
            status:"default",
            author:"test",
            id: null,
            Article: null

        };


    }

    componentWillMount() {

        const paramId=this.props.match.params.id;
        // create new article if no id in params
        if(paramId==null){
            console.log("Setting up new article in db");

            const article={
                title: this.state.title,
                content: this.state.html,
                status: this.state.status,
                topic: this.state.topic,
                author: this.state.author,
                publisher: "",
                publisherComment: "",
            }

            let id=0;

            axios.post('/article/', article)
                .then(res => {
                    this.setState(state=>({
                        ...state,
                        id: res.data._id
                    }));
                    id=res.data._id;
                    console.log("Saved article successfully!");

                    this.props.history.push("/login/edit/"+id);


                })
                .catch(err=> {
                    console.log("Couldnt create new article.");
                    console.log(err);
                });

        } else{
            axios.get("/article/"+paramId)
                .then(res =>{
                    console.log(res.data.content);
                    this.setState({
                        title: res.data.title,
                        html: res.data.content,
                        status: res.data.status,
                        topic: res.data.topic,
                        author: res.data.author,
                        publisher: res.data.publisher,
                        publisherComment: res.data.publisherComment,
                        id: res.data._id
                    });
                })
        }

    }

    handleEditorChange = (content, editor) => {
        console.log("Content was updated:", content);
        this.setState(state=>({
            ...state,
            html: content,
        }));


        // TODO: when editing title, rename html in backend

        // update article in db
        if (this.state.title!=="")
        {
            const article= {
                title: this.state.title,
                content: this.state.html,
                status: this.state.status,
                topic: this.state.topic,
                author: this.state.author,
                publisher: "",
                publisherComment: "",
            }
            axios.put('/article/'+this.state.id, article)
                .then(res =>{
                    console.log("Saved article successfully!");
                    console.log(res);
                })
            .catch(err=> console.log(err));

        }else{
            // TODO: if title empty and oldtitle not empty, delete file
        }
    };

    togglePreview=() =>{
        this.setState(state =>({
            ...state,
            showPreview: !this.state.showPreview,
        }));
    }

    onChangeTitle=(e)=>{
        // console.log(e.target.value);
        this.setState(state =>({
            ...state,
            title: e.target.value
        }));

    }

    onChangeImage = (e) => {
        this.setState({
            imageFilename: e.target.files[0],
        });
        console.log("Image change: ");
        console.log(e.target.files[0]);
        console.log(this.state.imageFilename);
    };


    /*imageSubmit = (e) => {
        e.preventDefault();
        console.log("Submitted file: ");
        console.log(this.state.imageFilename);
        const formData = new FormData();
        formData.append("image", this.state.imageFilename);

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
    };*/

    render() {
        return (
            <div>
                <Container>
                    <Form>
                        <Form.Group>
                            <Form.Label>Titel</Form.Label>
                            <Form.Control placeholder="Titel eingeben" value={this.state.title} onChange={this.onChangeTitle}/>
                        </Form.Group>

                    </Form>
                </Container>

                <Container style={{display: "flex"}} className={"mr-0"}>
                    <Button className={"mb-2"} style={{marginLeft:"auto"}} onClick={this.togglePreview}>
                        {this.state.showPreview ? 'Live-Preview schließen':'Live-Preview öffnen'}
                    </Button>
                </Container>


                {this.state.showPreview && (
                <PreviewWindow>
                    {parse(this.state.html)}
                </PreviewWindow>
                )}

                <Editor
                    initialValue={this.state.html}
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

                        // custom image upload handler (with multer)
                        images_upload_handler: function (
                            blobInfo,
                            success,
                            failure,
                            progress
                        ) {
                            const formData = new FormData();
                            console.log("Blobinfo");
                            console.log(blobInfo.blob());

                            console.log("imageFilename: " + blobInfo.filename());

                            formData.append("image", blobInfo.blob());

                            axios
                                .post("/images/add", formData)
                                .then((res) => {
                                    console.log(res.data);
                                    console.log("return: " + res.data.location);
                                    success(res.data);
                                })
                                .catch((err) => failure("Upload failed"));

                        },

                        setup: function(editor)
                        {
                            editor.on('BeforeSetContent', function (e) {
                                let html =e.content;

                                if (html.includes("<img"))
                                {
                                    // resize width of image
                                    //console.log("before: "+html);
                                    let re=/(width=")([\s\S]*?)(")/g;
                                    //console.log("match: "+html.match(re));
                                    html=html.replace(re,"$1800$3");
                                    //console.log("html: "+html);

                                    // remove height tag to keep dimensions
                                    re=/(height="[\s\S]*?")/;
                                    html=html.replace(re,"");
                                }
                                e.content=html;
                                //console.log("after: ");
                                //console.log(e);
                            })
                        },




                    }}
                    onEditorChange={this.handleEditorChange}
                />
            </div>
        );
    }
}

// Live preview of article as a React Portal
class PreviewWindow extends Component{
    constructor(props) {
        super(props);

        this.externalWindow=null;
        this.containerEl=document.createElement('div');
    }

    componentDidMount() {
        this.externalWindow=window.open('','','width=600,height=720,left=200,top=200');

        this.externalWindow.document.body.appendChild(this.containerEl);

    }

    componentWillUnmount() {
    this.externalWindow?.close();
    }

    render() {
        return ReactDOM.createPortal(this.props.children, this.containerEl);
    }


}

// image upload form for testing
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
</Form>*/