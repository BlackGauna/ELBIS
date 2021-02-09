import React, {Component} from "react";
import {BrowserRouter as Router} from "react-router-dom";
import axios from "axios";
import UserTopicService from "../services/userTopic.service";
import TopicService from "../services/topic.service";
import parse from "html-react-parser";
import "bootstrap/dist/css/bootstrap.min.css";
import '../article_Terminal.module.css';
import {Container} from "react-bootstrap";

export default class ArticleView extends Component {
//##########Render##########
    constructor(props) {
        super(props);

        this.state={
            content:"",
            loggedUser:null
        }
    }

    componentDidMount() {

        const userMail=sessionStorage.getItem("sessionEmail");
        this.setState({
            loggedUser: userMail
        });

        UserTopicService.getAllByMail(userMail)
            .then(res =>{
                if (res.data.length===0)
                {
                    TopicService.getAll()
                        .then(res=>{
                            console.log(res.data);
                        })
                }
            });

        axios.get("/article/"+this.props.match.params.id)
            .then(res=>{
                console.log("article: ");
                console.log(res.data);

                const test= res.data.content;
                console.log(test);
                console.log(sessionStorage.getItem("sessionEmail"));


                this.setState({
                    content: res.data.content,
                })
            })
            .catch(err=>{
                console.log("Error loading article: "+err);
            })
    }


    render() {
        return (
            <Router>
                <Container className={"d-flex justify-content-center"}>
                    <div>
                        {parse(this.state.content)}
                    </div>

                </Container>
            </Router>
        )
    }
}