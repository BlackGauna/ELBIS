import React, {Component} from "react";
import {BrowserRouter as Router} from "react-router-dom";
import axios from "axios";

export default class ArticleView extends Component {
//##########Render##########
    constructor(props) {
        super(props);

        this.state={
            content:"",
        }
    }

    componentDidMount() {
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