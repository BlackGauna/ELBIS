import React from "react";
import ReactDOM from "react-dom";
import { Component } from "react";
import { Editor } from "@tinymce/tinymce-react";
import axios from "axios";
import parse from 'html-react-parser';
import {Button, Container, Form} from "react-bootstrap";


//TODO: Resuse uploaded images (?)

// TODO: set topic, author (etc.) from current user


export default class CreateArticle extends Component {
    constructor(props) {
        super(props);

        this.state = {
            imageFilename: "",
            showPreview:false,
            path:"",
            html:"",
            title:"unnamed",
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
                    console.log(res.data);

                    // reload site with id as param
                    this.props.history.push("/login/edit/"+id);

                })
                .catch(err=> {
                    console.log("Couldn't create new article.");
                    console.log(err);
                });

        } else{
            // else load the article with id in params
            axios.get("/article/"+paramId)
                .then(res =>{
                    console.log(res.data);
                    this.setState({
                        title: res.data.article.title,
                        path: res.data.article.path,
                        html: res.data.content,
                        status: res.data.article.status,
                        topic: res.data.article.topic,
                        author: res.data.article.author,
                        publisher: res.data.article.publisher,
                        publisherComment: res.data.article.publisherComment,
                        id: res.data.article._id
                    });
                })
                .catch(err=>{
                    console.log("Couldn't load existing article!");
                    console.log(err);
                });
        }

    }


    deleteRemovedImg = (oldHtml, newHtml)=>{
        let re=/(<img [\s\S]*?\/>)/g;

        let oldImages=oldHtml.match(re);
        let newImages=newHtml.match(re);

        console.log("old: ");
        console.log(oldImages);
        console.log("new: ");
        console.log(newImages);

        if(oldImages!==null){

            if(newImages==null){
                for (let i=0; i<oldImages.length;i++){
                    let imgPath = oldImages[i].substring(oldImages[i].indexOf('images/'));
                    imgPath= imgPath.substring(0, imgPath.indexOf(' ') -1);
                    axios.delete("/"+imgPath)
                        .then(res => console.log(res.data));

                    console.log("path to delete: ");
                    console.log(imgPath);
                }

            }else if (oldImages.length> newImages.length){
                for (let i=0; i<oldImages.length;i++) {
                    if (newImages.includes(oldImages[i])===false) {
                        let imgPath = oldImages[i].substring(oldImages[i].indexOf('images/'));
                        imgPath= imgPath.substring(0, imgPath.indexOf(' ') -1);
                        axios.delete("/"+imgPath)
                            .then(res => console.log(res.data));

                        console.log("path to delete: ");
                        console.log(imgPath);
                    }
                }
            }
        }

        console.log("match: ");
        console.log(oldImages);
        // console.log(oldImages.length);
    }

    // when editor content changes
    handleEditorChange = (content) => {
        console.log("Content was updated:", content);

        let oldHtml=this.state.html;

        this.deleteRemovedImg(oldHtml,content);

        this.setState(state=>({
            ...state,
            html: content,
        }));

        console.log("current id: "+this.state.id);

        // update article in db
        if (this.state.title!=="")
        {
            const article= {
                title: this.state.title,
                path: this.state.path,
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
                    console.log(res.data);
                    this.setState({
                        path: res.data.path,
                    });
                })
            .catch(err=> console.log(err));

        }else{
            // TODO: if title empty and oldtitle not empty, delete file (?)
        }
    }

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
    }

    loadEditorContent=()=>{
        // console.log("loaded: "+ this.state.html);
        return this.state.html;
    }

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
                    initialValue={this.loadEditorContent()}
                    apiKey="0pg6bjj3shae8ys7qwuzkwo6jba2p7i7bs6onheyzqlhswen"
                    init={{
                        //skin: "oxide-dark",
                        //content_css:"dark",
                        content_css: `${process.env.PUBLIC_URL}/article.css`,
                        plugins: [
                            "advlist autoresize autolink lists link image charmap importcss print preview anchor",
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
                            failure
                        ) {
                            const formData = new FormData();
                            console.log(blobInfo);
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
                                    //TODO: what if image is not inserted i.e. pressed cancel
                                })
                                .catch((err) => failure("Upload failed: "+err));

                        },

                        setup: function(editor)
                        {
                            editor.on('BeforeSetContent', function (e) {
                                let html =e.content;

                                // resize width of image
                                if (html.includes("<img"))
                                {
                                    //TODO: for now always resizing when loading article
                                    // even if img size is changed by user

                                    //console.log("before: "+html);
                                    let re=/(width=")([\s\S]*?)(")/g;
                                    //console.log("match: "+html.match(re));
                                    html=html.replace(re,"$1600$3");
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

        // root element
        this.containerEl=document.createElement('div');
        this.containerEl.className="container";
    }

    componentDidMount() {
        this.externalWindow=window.open('','Artikel-Preview','width=600,height=720,left=500,top=400');

        let style=document.createElement("style");
        this.externalWindow?.document.head.appendChild(style);
        var Vcss= '\n' +
            '.container{\n' +
            '    background-color: aqua;\n' +
            '}\n' +
            '\n' +
            'p{\n' +
            '    background-color: aquamarine;\n' +
            '}';
        style.appendChild(document.createTextNode(Vcss));



        this.externalWindow.document.body.appendChild(this.containerEl);
    }

    componentWillUnmount() {
    this.externalWindow?.close();
    }

    render() {
        return ReactDOM.createPortal(this.props.children, this.containerEl);
    }


}
