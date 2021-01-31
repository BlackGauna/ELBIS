import React, {Component} from "react";
import {BrowserRouter as Router} from "react-router-dom";
import axios from "axios";
import UserTopicService from "../services/userTopic.service";
import TopicService from "../services/topic.service";
import loggedUser from "../session/loggedUser";

export default class ArticleView extends Component {
//##########Render##########
    constructor(props) {
        super(props);

        this.state={
            content:"",
        }
    }

    componentDidMount() {

        const userMail=sessionStorage.getItem("sessionEmail");
        UserTopicService.getAllByMail(userMail)
            .then(res =>{
                if (res.data.length==0)
                {
                    TopicService.getAll()
                        .then(res=>{
                            console.log(res.data);
                        })
                }
            });

        console.log("Topics: ");
        axios.get("/article/"+this.props.match.params.id)
            .then(res=>{
                console.log("article: ");
                console.log(res.data);

                const test= res.data.content +"\n" + "Session: ";
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
                <div className="container">
                    {this.state.content}
                </div>
            </Router>
        )
    }
}