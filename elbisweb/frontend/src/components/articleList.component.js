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
            listEntries:[],
            selectedArticleIndex: null,
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
            (article, index) =>
                <ListGroup.Item
                    key={index}
                    action
                    href={"#"+article._id}
                    onClick={()=>this.onClick(index)}
                >
                    {article.title}
                </ListGroup.Item>
        );

        this.setState({
            listEntries: listEntries
        });

    }

    onClick=(index)=>{
        this.setState({
            selectedArticleIndex: index
        })
        console.log(this.state.selectedArticleIndex)
    }


    render() {


        return (
            <Router>
                <div className={"m-3 d-flex"}>
                    <div style={{}}>
                        <ListGroup>
                            {this.state.listEntries}
                        </ListGroup>
                    </div>

                    <div className={"m-5 flex"} style={{width:"100%", height:"100%"}}>
                        {this.state.selectedArticleIndex !=null &&
                        parse(this.state.articles[this.state.selectedArticleIndex].content)}

                        {/*{this.state.selectedArticleIndex !=null &&
                        <iframe
                            width={"100%"}
                            height={"maxHeight"}
                            style={{border:"none"}}
                            src={"/article/"+this.state.articles[this.state.selectedArticleIndex]._id}>

                        </iframe>}*/}
                    </div>

                </div>
            </Router>
        )
    }
}