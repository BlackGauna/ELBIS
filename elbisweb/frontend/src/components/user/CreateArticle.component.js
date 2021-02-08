import React, {useState} from "react";
import ReactDOM from "react-dom";
import { Component } from "react";
import { Editor } from "@tinymce/tinymce-react";
import axios from "axios";
import parse from 'html-react-parser';
import {Button, Container, Form} from "react-bootstrap";
import TopicService from "../../services/topic.service";
import UserTopicService from "../../services/userTopic.service";
import ArticleService from '../../services/article.service';
import {ARTICLESTATUS} from "../../session/articleStatus.ice";
import Flatpickr from "react-flatpickr";
import "flatpickr/dist/themes/material_green.css";
import articleStyle from '../../article_Terminal.module.css';



//TODO: Reuse uploaded images (?)

//TODO: valid | invalid for Form submission

export default class CreateArticle extends Component {
    constructor(props) {
        super(props);

        this.state = {
            imageFilename: "",
            showPreview:false,
            path:"",
            html:"",
            title:"",
            topic:"",
            status:"",
            author:"undefined",
            id: null,
            loggedUser:null,
            allowedTopics: [],
            statusOptions:[],
            date:new Date().toISOString(),
            initialized:false,


        };


    }

    componentDidMount() {

        // get current logged in user as email
        const loggedUser= sessionStorage.getItem("sessionEmail");
        this.setState({
            loggedUser: loggedUser,
        });

        const paramId=this.props.match.params.id;

        // create new article if no id in params
        if(paramId==null){
            console.log("Setting up new article in db");

            const article={
                title: "unnamed",       // set unnamed so that backend can save correctly
                content: this.state.html,
                status: "undefined",    // same as above
                topic: "undefined",     // same as above
                author: loggedUser,
                publisher: "",
                publisherComment: "",
                expireDate:this.state.date
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
                    console.log(res.data.article.expireDate)

                    // if previously undefined set as empty for form validation
                    if (res.data.article.title==="unnamed")
                    {
                        res.data.article.title="";
                    }
                    if (res.data.article.status==="undefined")
                    {
                        res.data.article.status="";
                    }
                    if (res.data.article.topic==="undefined")
                    {
                        res.data.article.topic="";
                    }
                    this.setState({
                        title: res.data.article.title,
                        path: res.data.article.path,
                        html: res.data.content,
                        status: res.data.article.status,
                        topic: res.data.article.topic,
                        author: res.data.article.author,
                        publisher: res.data.article.publisher,
                        publisherComment: res.data.article.publisherComment,
                        id: res.data.article._id,
                        date: res.data.article.expireDate,

                    });

                    this.setState({
                        initialized:true
                    });

                    this.getUserData();

                })
                .catch(err=>{
                    console.log("Couldn't load existing article!");
                    console.log(err);
                });

        }

        // setup beforeunload and unload events for deleting unnamed article
        window.addEventListener("beforeunload", this.onBeforeUnload);
        window.addEventListener("unload", this.onUnload,false);

    }

    // show alert when trying to leave page
    onBeforeUnload =(e)=>{
        e.preventDefault();
        e.returnValue= "Ungespeicherte Änderungen";

    }

    // delete unnamed article when leaving page
    onUnload= (e)=>{
        //console.log("unload")
        if (this.state.title===""){

            // delete request has to be synchronous
            let xhr = new XMLHttpRequest();
            xhr.open("DELETE", "/article/"+this.state.id,false);
            xhr.send();

        }
    }


    componentWillUnmount() {
        window.removeEventListener("unload", this.onUnload,false);
    }


    /**
     * get all user data from Db and call subfunctions for additional data.
     */
    getUserData=()=>{
        const currentUser=sessionStorage.getItem("sessionUserID");

        axios.get("/user/"+currentUser)
            .then(res=>{

                this.setState({
                    loggedUser: res.data
                }, ()=>{
                    this.getUserTopics();
                    this.getStatusOptions();
                });

            })

    }

    /**
     * Get available status options for current user role
     */
    getStatusOptions=() =>{
        const userRole=this.state.loggedUser.role;

        if (userRole!=="Nutzer"){
            this.setState({
                statusOptions: ARTICLESTATUS.getAll()
            }, ()=>{
                console.log("status: ")
                console.log(this.state.statusOptions)
            })
        }else{
            this.setState({
                statusOptions: ARTICLESTATUS.getUserOptions()
            })
        }
    }

    //update to use topic array in user object?
    /**
     * Get allowed topics of user and save to state.
     * If role is not user, have all topics
     */
    getUserTopics= () =>{
        const userRole=this.state.loggedUser.role;
        //console.log("Loading available topics.");

        if (userRole!=="Nutzer")
        {
            TopicService.getAll()
                .then(res=>{

                    const topics= res.data.map(topic=>({
                        "id": topic._id,
                        "name": topic.name,
                    }));
                    //console.log("TOPICS:");
                    //console.log(topics);

                    this.setState({
                        allowedTopics: topics,
                    });
                })
        }else{
            UserTopicService.getAllByMail(this.state.loggedUser.email)
                .then(res=>{

                    const topics= res.data.map(topic=>({
                        "id": topic._id,
                        "name": topic.topic,
                    }));
                    console.log("TOPICS:");
                    console.log(topics);

                    this.setState({
                        allowedTopics: topics,
                    });
                })
        }
    }

/**################# Handle Editor Changes ###############################**/
    deleteRemovedImg = (oldHtml, newHtml)=>{
        let re=/(<img [\s\S]*?\/>)/g;

        let oldImages=oldHtml.match(re);
        let newImages=newHtml.match(re);

        //console.log("old: ");
        //console.log(oldImages);
        //console.log("new: ");
        //console.log(newImages);

        if(oldImages!==null){

            if(newImages==null){
                for (let i=0; i<oldImages.length;i++){
                    let imgPath = oldImages[i].substring(oldImages[i].indexOf('images/'));
                    imgPath= imgPath.substring(0, imgPath.indexOf(' ') -1);
                    axios.delete("/"+imgPath)
                        .then(res => console.log(res.data));

                    //console.log("path to delete: ");
                    //console.log(imgPath);
                }

            }else if (oldImages.length> newImages.length){
                for (let i=0; i<oldImages.length;i++) {
                    if (newImages.includes(oldImages[i])===false) {
                        let imgPath = oldImages[i].substring(oldImages[i].indexOf('images/'));
                        imgPath= imgPath.substring(0, imgPath.indexOf(' ') -1);
                        axios.delete("/"+imgPath)
                            .then(res => console.log(res.data));

                        //console.log("path to delete: ");
                        //console.log(imgPath);
                    }
                }
            }
        }

        //console.log("match: ");
        //console.log(oldImages);
        // console.log(oldImages.length);
    }


    /**
     * When editor content changes, send changes to server/Db
     * @param content - current content inside editor
     */
    handleEditorChange = (content) => {
        //console.log("Content was updated:", content);
        //console.log(this.state)
        console.log(this.state.initialized)

        let forms= document.querySelectorAll(".needs-validation");
        //console.log("forms");
        //console.log(forms);

        // check if all form entries are valid
        let allValid=true;
        Array.prototype.slice.call(forms)
            .forEach(function (form) {
                    if (!form.checkValidity()) {
                        allValid=false;
                        //console.log(allValid);
                    }

                    form.classList.add('was-validated')
            })

        let oldHtml=this.state.html;

        this.deleteRemovedImg(oldHtml,content);

        this.setState(state=>({
            ...state,
            html: content,
        }));

        if (allValid){

            //console.log("current id: "+this.state.id);
            console.log("Setting up update to Db.");

            // update article in db
            if (this.state.title!=="")
            {
                const article= {
                    id: this.state.id,
                    title: this.state.title,
                    path: this.state.path,
                    content: this.state.html,
                    status: this.state.status,
                    topic: this.state.topic,
                    author: this.state.author,
                    publisher: this.state.publisher,
                    publisherComment: this.state.publisherComment,
                    expireDate: this.state.date
                }

                //console.log(article);
                axios.put('/article/'+this.state.id, article)
                    .then(res =>{
                        console.log("Saved article successfully!");
                        console.log(res.data);
                        this.setState({
                            path: res.data.path,
                        });
                    })
                    .catch(err=> console.log(err));

                window.removeEventListener("beforeunload",this.onBeforeUnload)

            }
            // TODO: if title empty and oldtitle not empty, delete file (?)

        }



    }

    manualSave=()=>{
        console.log(this.state.html);
        this.handleEditorChange(this.state.html);
    }

/**################# OnChange ###############################**/
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

    onChangeTopic= (e)=>{
        //console.log(e.target);

        // get value inside 'data-key' of form item. Equals id of topic
        //const topicId=e.target[e.target.selectedIndex].dataset.key;
        // name of topic
        const topicName= e.target.value;

        this.setState({
            topic: topicName,
        })
    }

    onChangeStatus= (e)=>{
        //console.log(e.target);

        // get value inside 'data-key' of form item. Equals id of topic
        //const topicId=e.target[e.target.selectedIndex].dataset.key;
        // name of topic
        const statusName= e.target.value;

        this.setState({
            status: statusName,
        })
    }

    onChangeDate= (e) =>{
        const date=e[0].toISOString();
        this.setState({
            date:date
        });
        //console.log(date)
    }

    loadEditorContent=()=>{
        // console.log("loaded: "+ this.state.html);
        return this.state.html;
    }

/**##########################Render########################################**/
    render() {

        return (
            <div>

                <Container>
                    <Form noValidate className={"needs-validation"} >
                        <Form.Group className={"w-50"}>
                            <Form.Label>Titel</Form.Label>
                            <Form.Control placeholder="Titel eingeben"
                                          value={this.state.title}
                                          onChange={this.onChangeTitle}
                                          required
                            />
                            <Form.Control.Feedback className={"valid-feedback"}>
                                In Ordnung.
                            </Form.Control.Feedback>
                            <Form.Control.Feedback className={"invalid-feedback"} type="invalid">
                                Bitte einen Titel eingeben.
                            </Form.Control.Feedback>
                        </Form.Group>

                        <Form.Row>
                        <Form.Group className={"col-4"}>
                            <Form.Label>Bereich</Form.Label>
                            <Form.Control as={"select"} custom required value={this.state.topic} onChange={this.onChangeTopic}>
                                <option disabled value="">Bereich auswählen:</option>
                                {
                                    this.state.allowedTopics.map(
                                        topic=> <option value={topic.name} data-key={topic.id} key={topic.id}>{topic.name}</option>
                                    )
                                }
                            </Form.Control>
                        </Form.Group>

                        <Form.Group className={"col-4"}>
                            <Form.Label>Status</Form.Label>
                            <Form.Control as={"select"} required custom value={this.state.status} onChange={this.onChangeStatus}>
                                <option disabled value="">Status auswählen:</option>
                                {
                                    this.state.statusOptions.map(
                                        (status, index)=> <option value={status.name} key={index}>{status.name}</option>
                                    )
                                }
                            </Form.Control>
                        </Form.Group>
                        </Form.Row>

                        <Form.Group>
                            <Form.Label>Ablaufdatum</Form.Label>
                            <div/>
                            <Flatpickr
                                data-enable-time
                                options={{dateFormat: "Y-m-d H:i", defaultDate:this.state.date, time_24hr: true}}
                                value={this.state.date}
                                onReady={this.onChangeDate}
                                onChange={this.onChangeDate}
                                />
                        </Form.Group>

                    </Form>
                </Container>



                {this.state.showPreview && (
                <PreviewWindow>
                    {parse(this.state.html)}
                </PreviewWindow>
                )}


                <Container>
                    <div style={{display:"flex"}}>
                        <Button className={" mb-2"} onClick={this.manualSave}>
                        Manuell speichern</Button>
                        <Button className={" mb-2 ml-auto"} onClick={this.togglePreview}>
                            {this.state.showPreview ? 'Live-Preview schließen':'Live-Preview öffnen'}
                        </Button>

                    </div>

                <Editor
                    id={"editor"}
                    initialValue={this.loadEditorContent()}
                    apiKey="0pg6bjj3shae8ys7qwuzkwo6jba2p7i7bs6onheyzqlhswen"
                    init={{
                        //skin: "oxide-dark",
                        //content_css:"dark",
                        //content_css: `${process.env.PUBLIC_URL}/article.css`,
                        content_css: articleStyle,
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

                            let config = {
                                onUploadProgress: function(progressEvent) {
                                    let percentCompleted = Math.round( (progressEvent.loaded * 100) / progressEvent.total );
                                    console.log("Upload progress: "+percentCompleted);
                                }
                            };

                            axios
                                .post("/images/add", formData, config)
                                .then((res) => {
                                    console.log("location: "+ res.data);
                                    //console.log("return: " + res.data.location);
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
                </Container>
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
