import React, {Component} from "react";
import {BrowserRouter as Router} from "react-router-dom";
import axios from "axios";
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
        }
    }

    componentDidMount() {

        axios.get("/article/"+this.props.match.params.id)
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
                <Container className={"d-flex justify-content-center"}>
                    <div>
                        {parse(this.state.content)}
                    </div>

                </Container>
            </Router>
        )
    }
}