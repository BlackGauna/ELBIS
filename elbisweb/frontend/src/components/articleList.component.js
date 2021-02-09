import React, {Component} from "react";
import {BrowserRouter as Router} from "react-router-dom";
import axios from "axios";
import parse from "html-react-parser";
import "bootstrap/dist/css/bootstrap.min.css";
import {Container, ListGroup} from "react-bootstrap";
import ArticleDataService from '../services/article.service';

export default class ArticleList extends Component {
//##########Render##########
    constructor(props) {
        super(props);

        this.state={
            content:"",
            articles:[],
            listEntries:[]
        }
    }

    componentDidMount() {

        ArticleDataService.getAllPublished()
            .then(res=>{
                this.setState({
                    articles: res.data
                })

                this.buildList();
            })
    }

    /**
     * build the list from article array
     */
    buildList=() =>{
        const listEntries= this.state.articles.map(
            (article, index) => <ListGroup.Item key={index}>
                <a href={"/article/"+article._id}>{article.title}</a>
            </ListGroup.Item>
        );

        this.setState({
            listEntries: listEntries
        });

    }

    render() {
        return (
            <Router>
                <Container className={"mt-3 d-flex"}>
                    <div>
                        <ListGroup>
                            {this.state.listEntries}
                        </ListGroup>
                    </div>

                </Container>
            </Router>
        )
    }
}