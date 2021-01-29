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
        axios.get("/articles/"+this.props.match.params.id)
            .then(res=>{
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