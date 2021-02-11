import React, {Component} from 'react';
import "bootstrap/dist/css/bootstrap.min.css";
import loggedUser from '../session/loggedUser';
import logo from '../resources/ELBIS_logo/ELBIS_Ausgeschrieben.svg';
import UserDataService from "../services/user.service";
import SessionDataService from "../services/session.service";
import ELBIS_loginInputfield from "./ELBIS_loginInputfield.component";
import {Link} from "react-router-dom";
import Form from "react-bootstrap/Form";
import "./loginView.css";
import Grid from "@material-ui/core/Grid";

export default class loginViewComponent extends Component {
    /********
     *
     * Constructor
     *
     ********/
    constructor(props) {
        super(props);
        this.state = {
            email: '',
            password: '',
            mounted: true,
            buttonDisabled: false,
            submitted: false,
            // loginstate: "geben sie ihre Anmeldedaten ein."
        }
    }
    /********
     *
     * Mounting
     *
     ********/
    componentWillUnmount() {
        console.log("unmounted login")
    }

    setInputValue(property, val) {
        val = val.trim();
        if (val.length > 24) {
            return;
        }
        this.setState({
            [property]: val
        })
    }

    resetForm(msg) {
        this.setState({
            email: '',
            password: '',
            buttonDisabled: false,
            loginstate: msg
        })
    }

    /********
     *
     * Logging functions
     *
     ********/
    async doLogin() {
        if (!this.state.email) {
            return;
        }
        if (!this.state.password) {
            return;
        }
        //make sure the user can't double click the submit button
        this.setState({
            buttonDisabled: true
        })
        const authEmail = this.state.email;
        const authPassword = this.state.password;


        //authenticate a user
        UserDataService.authenticate(authEmail, authPassword).then(res => {
            //authenticate successfull
            if (res.data.success) {
                //Delete old sessions
                try {
                    SessionDataService.delete(authEmail)
                        .then(res => console.log(res.data));
                } catch (e) {
                    console.log("No old session deleted")
                }
                /*
                    * create a session
                 */
                //frontend session
                const token = Math.random().toString(36).substr(2);
                //const token = bcrypt.hash(((Math.random().toString(36).substr(2)) + (Date.now.toString)), 10);
                const sessUserID = res.data.data._id.toString();
                const sessEmail = res.data.data.email;
                const sessRole = res.data.data.role;
                sessionStorage.setItem("sessionToken", token);
                sessionStorage.setItem("sessionUserID", sessUserID);
                sessionStorage.setItem("sessionEmail", sessEmail);
                sessionStorage.setItem("sessionRole", sessRole);
                //backend session
                const session = {
                    token: token,
                    userid: sessUserID,
                    email: sessEmail,
                    role: sessRole,

                }
                SessionDataService.create(session)
                    .then(res => {
                        this.setState({
                            token: res.data.token,
                            userid: res.data.userid,
                            email: res.data.email,
                            role: res.data.role,
                        });
                        console.log(res.data);
                        //pageskip variables
                        loggedUser.loading = false;
                        loggedUser.isLoggedIn = true;
                       // this.setState({submitted: true})
                    })
                    .catch(e => {
                        console.log(e);
                    });
                //authenticate failed
            } else if (res.data.success === false) {
                loggedUser.loading = false;
                loggedUser.isLoggedIn = false;
                this.resetForm("Das angegebene Passwort war falsch.");
            }
        })
            .catch((error) => {
                loggedUser.loading = false;
                loggedUser.isLoggedIn = false;
                this.resetForm("Keine gültigen Logindaten.");
                console.log(error);
            })
    }

    handleKeyPress = (event) => {
        if(event.key === 'Enter'){
            this.doLogin()
        }
    }

    handleRedirect = ()=>{
        this.setState({ submitted: true})
    }

    /********
     *
     * Render
     *
     ********/
    render() {
        if(this.state.submitted){
            window.location.reload();
        }
        else {
        return (
            <div className="Login">
                <Form onSubmit={this.onSubmit}>
                    <img src={logo} height={150} style={{marginBlock: "-4%", marginLeft: "-1%"}} alt="ELBIS"/>
                    <hr/>
                      <Form.Group size="lg" controlId="email">
                          <Form.Label>E-Mail</Form.Label>
                          <ELBIS_loginInputfield
                              type="email"
                              // placeholder='email'
                              value={this.state.email ? this.state.email : ''}
                              onChange={(val) => this.setInputValue('email', val)}
                              onKeyPress={this.handleKeyPress}
                          />
                      </Form.Group>
                    <Form.Group size="lg" controlId="password">
                        <Form.Label>Passwort</Form.Label>
                        <ELBIS_loginInputfield
                            type="password"
                            value={this.state.password ? this.state.password : ''}
                            onChange={(val) => this.setInputValue('password', val)}
                            onKeyPress={this.handleKeyPress}
                        />
                    </Form.Group>

                    {/*<div>*/}
                    {/*    <label>{this.state.loginstate}</label>*/}
                    {/*</div>*/}

                    <Grid container>
                        <Grid item xs>
                            <Link to="/public/resetpassword" onClick={()=>{this.handleRedirect()}}>Passwort vergessen?
                            </Link>
                        </Grid>
                        <Grid item>
                            <Link to="/public/register" onClick={()=>{this.handleRedirect()}}>
                                Registrieren
                            </Link>
                        </Grid>
                    </Grid>
                    <br/>
                    <button
                        type="submit"
                        disabled={this.state.buttonDisabled}
                        onClick={() => this.doLogin()}
                        onSubmit={() => this.doLogin()}
                        onKeyPress={this.handleKeyPress}
                        className="btn btn-dark btn-lg btn-block">Anmelden</button>
                </Form>
            </div>
        )}
    }
}
