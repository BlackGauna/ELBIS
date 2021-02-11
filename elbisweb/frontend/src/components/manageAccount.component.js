import React, {Component} from 'react';
import "bootstrap/dist/css/bootstrap.min.css";
import UserTopicDataService from "../services/userTopic.service";
import logo from '../resources/ELBIS_logo/ELBIS_Ausgeschrieben.svg';
import "bootstrap/dist/css/bootstrap.min.css";
import Select from 'react-select';
import {ROLE} from "../session/userRoles.ice";
import EditUser from "./moderation/moderation_editUser.component"
import TopicDataService from "../services/topic.service";


export default class ManageAccount extends Component {

    /********
     *
     * Constructor
     *
     ********/
    constructor(props) {
        super(props);
        this.state = {
            allowedTopics: []
        }
    }
    /********
     *
     * Mount
     *
     ********/
    componentDidMount() {
        this.getAllowedTopics()
    }
    async getAllowedTopics() {
        if(sessionStorage.getItem("sessionRole") === ROLE.ADMINISTRATOR || sessionStorage.getItem("sessionRole")=== ROLE.MODERATOR){
            const res = await TopicDataService.getAll()
            const data = res.data

            const options = data.map(d => ({
                "label": d.name
            }))
            this.setState({allowedTopics: options})
        } else{
        const mail = sessionStorage.getItem("sessionEmail")
        const res = await UserTopicDataService.getAllByMail(mail)
        const data = res.data

        const options = data.map(d => ({
            "label": d.topic
        }))
        this.setState({allowedTopics: options})}
    }
    /********
     *
     * Render
     *
     ********/
    render() {
        return (

            <div>
                <div className="container">
                    <img src={logo} style={{marginBlock: "-4%", marginLeft: "-5.5%", height: 150}} alt="ELBIS"/>
                    <h3>Accountverwaltung</h3>
                    <hr/>
                    <br/>
                    <h6>Accountinformationen</h6>
                </div>
                <div className="container border" style={{
                    background: "#F5F8FB",
                    width: "85%",
                    float: "center",
                }}>
                    <br/>
                    <EditUser user={{_id: sessionStorage.getItem("sessionUserID")}}/>
                    <br/>
                </div>
                <br/>
                <div className="container">
                    <h6>Meine Bereiche</h6>
                </div>
                <div className="container border" style={{
                    background: "#F5F8FB",
                    width: "85%",
                    float: "center",
                }}>
                    <br/>
                    {
                        this.state.allowedTopics === null ? "" : this.state.allowedTopics.map(v => <span
                            className="badge badge-pill badge-info row-cols-3"
                            style={{fontSize: "130%"}}>{v.label}</span>)
                    }
                    <br/>
                    <br/>
                </div>
            </div>
        )
    }
}
