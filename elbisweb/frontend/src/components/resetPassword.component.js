import React, {Component} from 'react';
import {BrowserRouter as Router, Link} from 'react-router-dom';
import {Button} from "react-bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import { Redirect } from 'react-router-dom';
import loggedUser from "../session/loggedUser";
import UserDataService from "../services/user.service";
import {FormLabel} from "react-bootstrap";

export default class resetPassword extends Component {
    /********
     *
     * Constructor
     *
     ********/
    constructor(props) {
        super(props);
        this.state = {
            redirect: false,
            email:'',
            password: '',
            passwordCheck: '',
            stateText: '',
            toggleemail: false,
            passwordLength: false,
            containsNumbers: false,
            isUpperCase: false,
            containsSymbols: false,

        }
    }

    redirect = () => {
        console.log("redirect")
        this.setState({redirect:true})
    }

    /********
     *
     * Password functions
     *
     ********/
    // check to see if there is any number in the PW
    checkForNumbers(string){
        const matches = string.match(/\d+/g);
        this.setState({
            containsNumbers: matches != null
        });
    }

    // check PW for upper case
    checkForUpperCase(string){
        const matches = string.match(/[A-Z]/);
        this.setState({
            isUpperCase: matches != null
        });
    }

    // check PW for symbols
    checkForSymbols(string){
        const symbols = new RegExp(/[^A-Z a-z0-9]/);
        this.setState({
            containsSymbols: symbols.test(string)
        });
    }
    /********
     *
     * Send update to DB
     *
     ********/
    send_password = () => {
        if (this.state.password) {
        if (this.state.password === this.state.passwordCheck) {
        UserDataService.update(
            this.props.match.params.id,
            {password: this.state.password}
        )
            .then(response => {
                console.log(response.data);
                this.setState({
                    message: "The user was updated successfully!",
                })
                window.alert("Passwort erfolgreich neu gesetzt.")
                this.redirect()
            })
            .catch(e => {
                console.log(e);
            });
        }else {
            this.setState({
                passwordCheck: '',
                stateText: 'Die Passwörter stimmen nicht überein.'
            })
        }
        }else {
            this.setState({
                stateText: 'Geben Sie ein Passwort ein'
            })
        }
    }
    /********
     *
     * Mounting
     *
     ********/
    componentDidMount() {
        this.setState({toggleemail: false})
    }

    /********
     *
     * Render
     *
     ********/
    render() {
        if(this.state.redirect){
            return <Redirect to="/"/>
        } else{
            let {
                password,
                passwordLength,
                containsNumbers,
                isUpperCase,
                containsSymbols
            } = this.state
            let btnStatus = !(passwordLength && containsNumbers && isUpperCase && containsSymbols);
            if(loggedUser.isLoggedIn){
                return (
                    <Router>
                        <div className="container"> <br/><br/>
                            <h4> Neues Passwort setzen </h4>
                            <input
                                type="password"
                                className="form-control"
                                placeholder="Passwort"
                                value={password}
                                onChange={this.onChange_password('password')}/>
                            <div>
                                <div className={passwordLength ? 'green' : null} style={{fontSize: "12px"}}>- Mehr als 8 Zeichen</div>
                                <div className={containsNumbers ? 'green' : null} style={{fontSize: "12px"}}>- Zahlen</div>
                                <div className={isUpperCase ? 'green' : null} style={{fontSize: "12px"}}>- Großbuchstaben</div>
                                <div className={containsSymbols ? 'green' : null} style={{fontSize: "12px"}}>- Sonderzeichen</div>
                            </div>
                            <br/>
                            <label>Passwort bestätigen</label>
                            <input
                                type="password"
                                className="form-control"
                                placeholder="Passwort"
                                value={this.state.passwordCheck}
                                onChange={this.onChange_passwordCheck}/>
                            <div className="form-group">
                                <Button variant={"primary"} disabled={btnStatus} className="btn btn-primary" onClick={()=>this.send_password()}> Bestätigen </Button>
                                <FormLabel className="m-3" style={{color: "red"}}>{this.state.stateText}</FormLabel>
                            </div>
                        </div>
                    </Router>
                )
            } else {
                let toggleemail = this.state.toggleemail;
                let emailfield;
                if(!toggleemail){
                    emailfield = <div>
                        <h6> Bitte geben sie ihre Email an: </h6>
                        <input
                            type ="email"
                            className="form-control"
                            value={this.state.email}
                            onChange={this.onChange_email}
                            placeholder="E-Mail"
                        /> <br/>
                        <Button onClick={this.toggle_email}> Absenden </Button>
                        <Link onClick={this.redirect}> Abbrechen </Link>
                    </div>
                } else if(toggleemail){
                    emailfield= <div>
                        <h6> Sie erhalten in kürze eine Email </h6>
                        (dies ist eine Dummyfunktion)
                        <Link onClick={this.redirect}> Zurück </Link>
                    </div>
                }

                return(
                    <Router>
                        <div className="container"> <br/><br/>
                            <h4> Passwort zurücksetzen </h4>
                            {emailfield}
                        </div>
                    </Router>
                )
         }
        }
    }
    /********
     *
     * Change methods
     *
     ********/
    onChange_password= input => e => {
        let targetValue = e.target.value.replace(/\s/g, '');
        this.checkForNumbers(targetValue);
        this.checkForUpperCase(targetValue);
        this.checkForSymbols(targetValue);
        this.setState({
            [input]: targetValue,
            passwordLength: targetValue.length > 7
        });
    }
    onChange_passwordCheck=(e)=> {
        this.setState({
            passwordCheck: e.target.value
        })
    }
    onChange_email = (e) => {
        const email = e.target.value;
        this.setState({email: email
        });
    }
    toggle_email = () => {
        let toggleemail = this.state.toggleemail;
        toggleemail = !toggleemail;
        this.setState({toggleemail: toggleemail})
        console.log(this.state.toggleemail)
    }
}