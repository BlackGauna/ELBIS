import React, {Component, useLayoutEffect} from 'react';
import "bootstrap/dist/css/bootstrap.min.css";
import ArticleService from "../services/article.service";
import {Container, Tab, Tabs} from "react-bootstrap";
import parse from "html-react-parser";
import style from '../article_Terminal.module.css'
import {Helmet} from "react-helmet";
import loggedUser from "../session/loggedUser";
import $ from 'jquery'


export default class ELBISweb extends Component {

    constructor(props) {
        super(props);

        this.state={
            articles:[],
            isOverflown:false,
            tabs:[]
        }
        this.myRef= React.createRef();


    }


    componentDidMount() {
       ArticleService.getAllAuthorized()
           .then(res=>{
               //console.log(res.data)
               this.setState({
                   articles:res.data
               })
               console.log(this.state.articles)

               this.buildTabsnew()

           })
    }

    buildTabsnew=() =>{
        const tabs=this.state.articles.map(
            article=> <Tab.Pane
                onLoad={e=>this.handleTabChange(e)}
                onEntered={e=>this.handleTabChange(e)}
                ref={this.myRef}
                eventKey={article._id} title={article.title} key={article._id}>
                <Container className={"d-flex justify-content-center"}>
                    <div id={"test"} className={style.content}>
                        {parse(article.content)}
                    </div>
                </Container>
            </Tab.Pane>
        )

        this.setState({
            tabs:tabs
        })
        this.handleTabChange()
    }

   /* buildTab=() =>{
        try {
            return this.state.articles.map(
                article=> <Tab.Pane
                    onChange={console.log($(document).height)}
                    onEntered={e=>this.handleTabChange(e)} ref={this.myRef}
                    eventKey={article._id} title={article.title} key={article._id}>
                    <Container className={"d-flex justify-content-center"}>
                        <div id={"test"} className={style.content}>
                            {parse(article.content)}
                        </div>
                    </Container>
                </Tab.Pane>
            )
        }finally {

        }
    }*/


    handleTabChange=(e) => {
        //console.log(e)
        console.log(document.getElementById("test").scrollHeight)
        //console.log($(document).height)
        console.log(window.innerHeight)


        if($(document).height()>window.innerHeight){
            this.setState({
                isOverflown:true
            })
        }else {
            this.setState({
                isOverflown:false
            })
        }



    }


//##########Render##########
    render() {

        return (

        <div id={"tes"} className={"all"}>
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

                <Tabs
                    unmountOnExit={true}
                >
                    {
                        this.state.tabs

                    }
                </Tabs>
            </div>


            {this.state.isOverflown && (
                <div className={style.banner}>
                    <div className={"container"}>
                        <p className={style.banner}>
                            Lesen Sie den vollständigen Artikel auf unserer Homepage: "QR Placeholder"
                        </p>
                    </div>
                </div>
            )}


        </div>
        )
    }
}