import React, {Component} from "react";
import {BrowserRouter as Router} from "react-router-dom";
import parse from "html-react-parser";
import "bootstrap/dist/css/bootstrap.min.css";
import {ListGroup} from "react-bootstrap";
import ArticleDataService from '../services/article.service';
import wappen from '../resources/ElkenrothWappen.png';

/**
 * Homepage of ELBIS which loads all published articles in a list and displays them
 */
export default class Index extends Component {
    constructor(props) {
        super(props);

        this.state = {
            content: "",
            articles: [],
            listEntries: [],
            selectedArticleIndex: null,
        }
    }

    componentDidMount() {
        ArticleDataService.getAllPublished()
            .then(res => {
                this.setState({
                    articles: res.data
                })

                this.buildList();
            })
    }

    /**
     * build the list from article array
     */
    buildList = () => {
        const listEntries = this.state.articles.map(
            (article, index) =>
                <ListGroup.Item
                    key={index}
                    action
                    href={"#" + article._id}
                    onClick={() => this.onClick(index)}
                >
                    {article.title}
                </ListGroup.Item>
        );

        this.setState({
            listEntries: listEntries,
            selectedArticleIndex: 0,
        });

    }

    onClick = (index) => {
        this.setState({
            selectedArticleIndex: index
        })
        console.log(this.state.selectedArticleIndex)
    }

    render() {
        return (
            <Router>
                <div className={"row"} style={{float: "center", marginLeft: +315}}>
                <img src={wappen}
                     alt={"Wappen"}
                     style={{position: "absolute", height: 90, width: 90, marginLeft: -220, marginBlock: +25}}/>

                <h1>Aktuelle Veröffentlichungen</h1>
                </div>
                <div className={"container-fluid"}>
                    <div className={"m-4 d-flex"}>
                        <div style={{}}>
                            <ListGroup className={"list-group-flush"}>
                                {this.state.listEntries}
                            </ListGroup>
                        </div>

                        <div className={"m-5 flex"} style={{width: "100%", height: "100%", background: "#F5F8FB"}}>
                            <br/>
                            {this.state.selectedArticleIndex != null &&
                            parse(this.state.articles[this.state.selectedArticleIndex].content)}
                            <hr/>
                            {this.state.selectedArticleIndex != null && "Artikelautor: " + this.state.articles[this.state.selectedArticleIndex].author}
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