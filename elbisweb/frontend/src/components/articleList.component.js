import React, {Component} from "react";
import {BrowserRouter as Router} from "react-router-dom";
import axios from "axios";
import parse from "html-react-parser";
import "bootstrap/dist/css/bootstrap.min.css";
import {Container, ListGroup} from "react-bootstrap";
import ArticleDataService from '../services/article.service';
import wappen from '../resources/ElkrothWappen.png';

export default class ArticleList extends Component {
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
            listEntries: listEntries,
            selectedArticleIndex: 0,
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
                <img src={wappen} style={{position:"absolute", height: 80, width: 80, marginLeft: +80, marginBlock: -10}}/>
                <div className={"container-fluid"}>
                <h1>Aktuelle Veröffentlichungen</h1>
                <div className={"m-3 d-flex"}>
                    <div style={{}}>
                        <ListGroup className={"list-group-flush"}>
                            {this.state.listEntries}
                        </ListGroup>
                    </div>

                    <div className={"m-5 flex"} style={{width:"100%", height:"100%",background: "#F5F8FB"}}>
                        <br/>
                        {this.state.selectedArticleIndex !=null &&
                        parse(this.state.articles[this.state.selectedArticleIndex].content)}
                        <hr/>
                        {this.state.selectedArticleIndex !=null && "Artikelautor: "+ this.state.articles[this.state.selectedArticleIndex].author}
                        {/*{this.state.selectedArticleIndex !=null &&
                        <iframe
                            width={"100%"}
                            height={"maxHeight"}
                            style={{border:"none"}}
                            src={"/article/"+this.state.articles[this.state.selectedArticleIndex]._id}>

                        </iframe>}*/}
                    </div>
                </div>
                </div>
            </Router>
        )
    }
}