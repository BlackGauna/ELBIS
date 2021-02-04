import React, {Component} from 'react';
import "bootstrap/dist/css/bootstrap.min.css";
import ArticleService from "../services/article.service";
import {Container, Tab, Tabs} from "react-bootstrap";
import parse from "html-react-parser";
import style from '../article_Terminal.module.css'
import {Helmet} from "react-helmet";


export default class ELBISweb extends Component {

    constructor(props) {
        super(props);

        this.state={
            articles:[]
        }

    }


    componentDidMount() {
       ArticleService.getAll()
           .then(res=>{
               console.log(res.data)
               this.setState({
                   articles:res.data
               })
           })
    }


//##########Render##########
    render() {
        return (
        <div className={"all"}>
            <Helmet>
                <style type={"text/css"}>
                    {`
                        body {
                        overflow: hidden;
                        }
                    `}
                </style>
            </Helmet>
            <div className={"container"}>

                <Tabs>
                    {
                        this.state.articles.map(
                            article=> <Tab eventKey={article._id} title={article.title} key={article._id}>
                                        <Container className={"d-flex justify-content-center content"}>
                                            <div>
                                                {parse(article.content)}
                                            </div>
                                        </Container>
                                    </Tab>
                        )
                    }
                </Tabs>

            </div>

            <div className={style.banner}>
                <div className={"container"}>
                    <p className={style.banner}>
                        Lesen Sie den vollständigen Artikel auf unserer Homepage: "QR Placeholder"
                    </p>
                </div>
            </div>
        </div>
        )
    }
}