import React, {Component} from 'react';
import "bootstrap/dist/css/bootstrap.min.css";
import ArticleService from "../services/article.service";
import {Container, Tab, Tabs} from "react-bootstrap";
import parse from "html-react-parser";
import style from '../article_Terminal.module.css'
import {Helmet} from "react-helmet";
import $ from 'jquery'
import qr from '../resources/qr-code.png'


export default class ELBISweb extends Component {

    constructor(props) {
        super(props);

        this.state={
            articles:[],
            isOverflown:false,
            tabs:[],
            activeTab:0,
            timer:0
        }
        this.myRef= React.createRef();


    }


    componentDidMount() {
        // load authorized articles into state
        ArticleService.getAllPublished()
           .then(res=>{
               //console.log(res.data)
               this.setState({
                   articles:res.data
               })
               //console.log(this.state.articles)

               this.buildTabs()

           })
    }

    /**
     * Build the tabs from the array of articles in state
     */
    buildTabs=() =>{
        const tabs=this.state.articles.map(
            (article,index)=> <Tab.Pane
                onEntering={e=>this.handleTabChange(e)}
                ref={this.myRef}
                eventKey={index} title={article.title} key={index}>
                <Container className={"d-flex justify-content-center"}>
                    <div className={style.content}>
                        {parse(article.content)}
                    </div>
                </Container>
            </Tab.Pane>
        )

        this.setState({
            tabs:tabs,
            activeTab: 0 // set as first article in array
        })
        this.handleTabChange()
    }


    /**
     * When tab is changed check size of article against window size.
     * If larger than window size, set isOverflown to true, so that banner is shown
     */
    handleTabChange=() => {
        //console.log(e)
        console.log($(document).height())
        //console.log(window.innerHeight)

        if($(document).height()>window.innerHeight){
            this.setState({
                isOverflown:true
            })
        }else {
            this.setState({
                isOverflown:false
            })
        }

        this.cylceOnTimer();


    }

    /**
     * Manual change of tab
     * @param e
     */
    onSelect=(e)=>{
        // reset timer, because of manual change
        clearTimeout(this.state.timer)
        this.setState({
            activeTab: parseInt(e)
        })

        this.handleTabChange(e)

    }

    /**
     * Sets up a timer for automatically changing the active tab.
     */
    cylceOnTimer= () =>{
        // reset timer
        clearTimeout(this.state.timer);

        let nextTab=0;
        const activeTab=this.state.activeTab;

        // if tab is last, go to first index
        if (activeTab===this.state.tabs.length-1)
        {
            nextTab=0;
        }else{
            nextTab= activeTab+1;

        }

        // set timer and change tab after resolving
        const t= setTimeout(() =>{

            this.setState({
                activeTab: nextTab
            });

        }, 15000); // TODO: relative to article length?

        // save the timer to state for manual reset
        this.setState({
            timer: t
        });
    }


//##########Render##########
    render() {

        return (

        <div className={"all"} style={{marginTop:"3rem"}}>
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
                    onSelect={this.onSelect}
                    activeKey={this.state.activeTab}
                    unmountOnExit={true}
                >
                    {
                        this.state.tabs

                    }
                </Tabs>
            </div>


            {this.state.isOverflown && (
                <div className={style.banner}>
                    <div style={{display:"flex"}} className={"container"}>
                        <p className={style.banner}>
                            Lesen Sie den vollständigen Artikel auf unserer Homepage:
                            <img src={qr} alt={"QR"} className={style.banner} />
                        </p>
                    </div>
                </div>
            )}


        </div>
        )
    }
}