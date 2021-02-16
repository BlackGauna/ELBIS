import React, {Component} from "react";
import Index from "./index.component";
import {Nav} from "react-bootstrap";
import IconButton from "@material-ui/core/IconButton";
import DescriptionIcon from '@material-ui/icons/Description';
import ListIcon from '@material-ui/icons/List';
import PersonIcon from '@material-ui/icons/Person';
import ComputerIcon from '@material-ui/icons/Computer';
import EmojiEmotionsIcon from '@material-ui/icons/Attachment';
import MessageIcon from '@material-ui/icons/Message';
import {ROLE} from "../session/userRoles.ice";
import ArticleDataService from "../services/article.service";
import {ARTICLESTATUS} from "../session/articleStatus.ice";

export default class Dasboard extends Component {
    /********
     *
     * Constructor
     *
     ********/
    constructor(props) {
        super(props);
        this.state = {
            article: [],
        }
    }

    /********
     *
     * Mount
     *
     ********/
    componentDidMount() {
        ArticleDataService.findByState(ARTICLESTATUS.EINGEREICHT)
            .then(response => {
                this.setState({article: response.data})
            })
            .catch((error) => {
                console.log(error);
            })
    }

    /********
     *
     * Render
     *
     ********/
    render() {
        const userrole = sessionStorage.getItem("sessionRole")
        return (<div>
                <div className={"container-fluid"} style={{background: "#F5F8FB", float: "center",}}>
                    <br/>

                    <h5>Hallo {sessionStorage.getItem("sessionEmail")}, du bist als {userrole} angemeldet, hier deine
                        Schnellzugriffe <EmojiEmotionsIcon/></h5>
                    <div className="row">
                        <Nav.Link href={"/login/edit"}>Sofort Artikel erstellen <IconButton
                            aria-label="edit"
                            size='small'>
                            <DescriptionIcon/>
                        </IconButton>
                        </Nav.Link>
                        <label style={{fontSize: "20px"}}>|</label>
                        <Nav.Link href={"/login/user/myArticles"}>meine Artikel anzeigen
                            <IconButton
                                aria-label="list"
                                size='small'>
                                <ListIcon/>
                            </IconButton>
                        </Nav.Link>
                        <label style={{fontSize: "20px"}}>|</label>
                        <Nav.Link href={"/login/manageAccount"}>meinen Account bearbeiten
                            <IconButton
                                aria-label="person"
                                size='small'>
                                <PersonIcon/>
                            </IconButton>
                        </Nav.Link>

                        {(userrole === ROLE.MODERATOR || userrole === ROLE.ADMINISTRATOR) ?
                            <>
                                <label style={{fontSize: "20px"}}>|</label>
                                <Nav.Link href={"/login/mod/manageSubmissions"}><label style={{
                                fontSize: "25px",
                                fontWeight: "Bold",
                                marginTop: "-10px"
                            }}>{this.state.article.length}</label> neue Veröffentlichung(en) verwalten
                                <IconButton
                                    aria-label="person"
                                    size='small'>
                                    <MessageIcon/>
                                </IconButton>
                            </Nav.Link></> : ''}

                        {(userrole === ROLE.MODERATOR || userrole === ROLE.ADMINISTRATOR) ?
                            <>
                                <label style={{fontSize: "20px"}}>|</label>
                                <Nav.Link href={"/login/terminal"}> Terminalansicht öffnen
                                <IconButton
                                    aria-label="person"
                                    size='small'>
                                    <ComputerIcon/>
                                </IconButton>
                            </Nav.Link></> : ''}

                    </div>
                    <br/>
                </div>
                <div className={"container-fluid"}>
                    <Index/>
                </div>
            </div>
        )
    }
}