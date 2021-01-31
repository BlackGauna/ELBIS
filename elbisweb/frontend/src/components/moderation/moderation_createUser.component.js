import React, {Component} from 'react';
import DatePicker from 'react-datepicker';
import "react-datepicker/dist/react-datepicker.css";
import "bootstrap/dist/css/bootstrap.min.css";
import Select from 'react-select';
import {Redirect} from "react-router-dom";
import {Button, FormLabel} from "react-bootstrap";
import TopicDataService from "../../services/topic.service";
import UserDataService from "../../services/user.service";
import {ROLE} from "../../session/userRoles.ice";
import {GENDER} from "../../session/gender.ice";

// TODO: TopicOptions is buggy yet
//  - just one is chooseable?
//  - crashes on delete?


export default class moderation_createUser extends Component {

    constructor(props) {
        super(props);

        this.state = {
            email: '',
            password: '',
            passwordCheck: '',
            fName: '',
            lName: '',
            street: '',
            hNumber: '',
            plz: '',
            city: '',
            gender: [],
            choosenGender: '',
            role: [],
            choosenRole: '',
            dateOfBirth: new Date(),
            allowedTopics: [],
            choosenTopics: [],

            stateText: '',
            redirect: false,
            submitted: false
        }
    }

    redirect = () => {
        this.setState({redirect: true})
    }

    // Get gender options for dropdown
    async getGenderOptions() {
        const data = GENDER.getAll();
        const options = data.map(d => ({
            "label": d.name
        }))
        this.setState({gender: options})
    }

    // Get role options for dropdown
    async getRoleOptions() {
        let data
        if (sessionStorage.getItem("sessionRole") === ROLE.ADMINISTRATOR) {
            data = ROLE.getAll()
        } else {
            data = ROLE.getModeratorOptions()
        }
        const options = data.map(d => ({
            "label": d.name
        }))
        this.setState({role: options})
    }

    async getTopicOptions(){
        const res = await TopicDataService.getAll()
        const data = res.data

        const options = data.map(d => ({
            "value": d.id,
            "label": d.name
        }))

        this.setState({allowedTopics: options})
    }

    //##########Mount method (equals initialize!)##########
    componentDidMount() {
        this.getGenderOptions()
        this.getRoleOptions()
        this.getTopicOptions()
    }

    //##########Render##########
    render() {
        if (this.state.redirect) {
            return <Redirect to="/login/mod/manageUsers"/>
        } else {
            return (
                <div className="container">
                    {/*<h3>Nutzer erstellen</h3>*/}
                    <form onSubmit={this.onSubmit}>

                        <div className="form-group">
                            <label>E-Mail</label>
                            <input
                                type="email"
                                className="form-control"
                                placeholder="E-Mail"
                                value={this.state.email}
                                onChange={this.onChange_email}/>
                        </div>
                        <div className="form-row">
                            <div className="form-group col-md-6">
                                <label>Passwort</label>
                                <input
                                    type="password"
                                    className="form-control"
                                    placeholder="Passwort"
                                    value={this.state.password}
                                    onChange={this.onChange_password}/>
                            </div>
                            <div className="form-group col-md-6">
                                <label>Passwort bestätigen</label>
                                <input
                                    type="password"
                                    className="form-control"
                                    placeholder="Passwort"
                                    value={this.state.passwordCheck}
                                    onChange={this.onChange_passwordCheck}/>
                            </div>
                        </div>
                        <div className="form-group">
                            <label>Geburtsdatum</label>
                            <div className="form-row">
                                <DatePicker
                                    selected={this.state.dateOfBirth}
                                    onChange={this.onChange_dateOfBirth}
                                    peekNextMonth
                                    showMonthDropdown
                                    showYearDropdown
                                    dropdownMode="select"
                                    customInput={<Button variant="outline-secondary" size="lg"
                                                         block>{this.state.dateOfBirth.toLocaleDateString()}</Button>}
                                /></div>
                        </div>
                        <div className="form-row">
                            <div className="form-group col-md-6">
                                <label>Vorname</label>
                                <input
                                    type="name"
                                    className="form-control"
                                    placeholder="Vorname"
                                    value={this.state.fName}
                                    onChange={this.onChange_fName}/>
                            </div>
                            <div className="form-group col-md-6">
                                <label>Nachname</label>
                                <input
                                    type="name"
                                    className="form-control"
                                    placeholder="Nachname"
                                    value={this.state.lName}
                                    onChange={this.onChange_lName}/>
                            </div>
                        </div>
                        <div className="form-row">
                            <div className="form-group col-md-10">
                                <label>Straße</label>
                                <input
                                    type="street"
                                    className="form-control"
                                    placeholder="Straße"
                                    value={this.state.street}
                                    onChange={this.onChange_street}/>
                            </div>
                            <div className="form-group col-md-2">
                                <label>Hausnummer</label>
                                <input
                                    type="houseNumber"
                                    className="form-control"
                                    placeholder="Hausnummer"
                                    value={this.state.hNumber}
                                    onChange={this.onChange_hNumber}/>
                            </div>
                        </div>
                        <div className="form-row">
                            <div className="form-group col-md-3">
                                <label>PLZ</label>
                                <input
                                    type="plz"
                                    className="form-control"
                                    placeholder="PLZ"
                                    value={this.state.plz}
                                    onChange={this.onChange_plz}/>
                            </div>
                            <div className="form-group col-md-9">
                                <label>Ort</label>
                                <input
                                    type="ort"
                                    className="form-control"
                                    placeholder="Ort"
                                    value={this.state.city}
                                    onChange={this.onChange_city}/>
                            </div>
                        </div>
                        <div className="form-row">
                            <div className="form-group col-md-6">
                                <label>Geschlecht</label>
                                <Select
                                    type="geschlecht"
                                    placeholder="Geschlecht"
                                    options={this.state.gender}
                                    onChange={this.onChange_gender}/>
                            </div>
                            <div className="form-group col-md-6">
                                <label>Rolle</label>
                                <Select
                                    type="role"
                                    placeholder="Rolle"
                                    options={this.state.role}
                                    onChange={this.onChange_role}/>
                            </div>
                        </div>
                        <div className="form-group">
                            <label>Erlaubte Bereiche</label>
                            <Select
                                type="allowedTopics"
                                isMulti
                                placeholder="Bereiche auswählen..."
                                options={this.state.allowedTopics}
                                onChange={this.onChange_allowedTopics}
                            />

                        </div>
                        <div className="form-group">
                            <input type="submit" value="Nutzer erstellen" className="btn btn-primary"/>
                            <FormLabel className="m-3" style={{color: "red"}}>{this.state.stateText}</FormLabel>
                        </div>
                    </form>
                </div>
            )
        }
    }

    //##########submit method##########
    onSubmit= (e)=> {
        //don't let any other submit run
        e.preventDefault();
        //create the object
        if (this.state.email) {
            if (this.state.password) {
                if (this.state.password === this.state.passwordCheck) {
                    const user = {
                        email: this.state.email,
                        password: this.state.password,
                        fName: this.state.fName,
                        lName: this.state.lName,
                        street: this.state.street,
                        hNumber: this.state.hNumber,
                        plz: this.state.plz,
                        city: this.state.city,
                        gender: this.state.choosenGender,
                        role: this.state.choosenRole,
                        dateOfBirth: this.state.dateOfBirth,
                        allowedTopics: this.state.choosenTopics
                    }

                    UserDataService.create(user)
                        .then(res => {
                            this.setState({
                                email: res.data.email,
                                password: res.data.password,
                                fName: res.data.fName,
                                lName: res.data.lName,
                                street: res.data.street,
                                hNumber: res.data.hNumber,
                                plz: res.data.plz,
                                city: res.data.city,
                                gender: res.data.gender,
                                role: res.data.role,
                                dateOfBirth: res.data.dateOfBirth,
                                allowedTopics: res.data.allowedTopics,

                                submitted: true
                            });
                            console.log(res.data);
                        })
                        .catch(e => {
                            console.log(e);
                        });

                    //go back to the moderationView
                    this.redirect();
                } else {
                    this.setState({
                        passwordCheck: '',
                        stateText: 'Die Passwörter stimmen nicht überein.'
                    })
                }
            } else {
                this.setState({
                    stateText: 'Geben sie ein Passwort ein'
                })
            }
        } else {
            this.setState({
                stateText: 'Geben sie eine Email-Addresse ein'
            })
        }
    }

    //##########change methods##########
    onChange_email=(e)=> {
        this.setState({
            email: e.target.value //target equals a textBox target, value its value; Changes just the given state value
        })
    }

    onChange_password=(e)=> {
        this.setState({
            password: e.target.value
        })
    }

    onChange_passwordCheck=(e)=> {
        this.setState({
            passwordCheck: e.target.value
        })
    }

    onChange_fName=(e)=> {
        this.setState({
            fName: e.target.value
        })
    }

    onChange_lName=(e)=> {
        this.setState({
            lName: e.target.value
        })
    }

    onChange_street=(e)=> {
        this.setState({
            street: e.target.value
        })
    }

    onChange_hNumber=(e)=> {
        this.setState({
            hNumber: e.target.value
        })
    }

    onChange_plz=(e)=> {
        this.setState({
            plz: e.target.value
        })
    }

    onChange_city=(e)=> {
        this.setState({
            city: e.target.value
        })
    }

    onChange_gender=(e)=> {
        this.setState({
            choosenGender: e.label
        })
    }

    onChange_role= (e) =>{
        this.setState({
            choosenRole: e.label
        })
    }

    onChange_dateOfBirth = (date) => {
        this.setState({
            dateOfBirth: date
        })
    }

    onChange_allowedTopics = (e) => {
        this.setState({
            choosenTopics: this.state.choosenTopics.push(e.label)
        })
    }


}