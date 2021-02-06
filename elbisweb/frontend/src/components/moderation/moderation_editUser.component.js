import React, {Component} from "react";
import UserDataService from "../../services/user.service";
import "bootstrap/dist/css/bootstrap.min.css";
import Select from 'react-select';
import {Button, FormText} from "react-bootstrap";
import {GENDER} from "../../session/gender.ice";
import {ROLE} from "../../session/userRoles.ice";
import DatePicker from "react-datepicker";

export default class moderation_editUser extends Component {
    //TODO Password
    /********
     *
     * Constructor
     *
     ********/
    constructor(props) {
        super(props);
        this.state = {
            //this user was given by the usertable (only used to load the user from the db by id)
            givenUserByTable: this.props.user,
            //in form: please use the loaded user(currentUser) down below due to its loaded directly from the db and might store newer information
            //
            //this will be the loaded User from the DB and the user to submit:
            currentUser: {
                id: null,
                email: '',
                password: '',
                passwordCheck: '',
                fName: '',
                lName: '',
                street: '',
                hNumber: '',
                plz: '',
                city: '',
                choosenGender: '',
                choosenRole: '',
                dateOfBirth: '',
                allowedTopics: [],
                choosenTopics: [],
            },
            updateSession: false,
            role: [],
            gender: [],
            stateText: '',
            toggles: {
                email: false,
                password: false,
                fName: false,
                lName: false,
                street: false,
                hNumber: false,
                plz: false,
                city: false,
                gender: false,
                role: false,
                dateOfBirth: false,
                topics: false,
            }

        };
        console.log("Opened EDITUSER with: ")
        console.log(this.props.user)
    }

    /********
     *
     * Mounting
     *
     ********/
    componentDidMount() {
        //this.getUser(this.props.match.params.id);
        this.getUser(this.props.user._id);
        this.getGenderOptions()
        this.getRoleOptions()
    }

    /********
     *
     * Load user from DB
     *
     ********/
    getUser = (id) => {
        UserDataService.get(id)
            .then(response => {
                this.setState({
                    currentUser: {
                        id: response.data._id,
                        email: response.data.email,
                        //!!!the hash will be loaded - dont change the password / !only write a new PW to DB if fiel changed
                        //password: response.data.password,
                        //passwordCheck: response.data.password,
                        fName: response.data.fName,
                        lName: response.data.lName,
                        street: response.data.street,
                        hNumber: response.data.hNumber,
                        plz: response.data.plz,
                        city: response.data.city,
                        choosenGender: response.data.gender,
                        choosenRole: response.data.role,
                        dateOfBirth: response.data.dateOfBirth,
                    }
                });
                console.log("Loaded User from DB:");
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    /********
     *
     * Load options from ice
     *
     ********/
    // Get gender options for dropdown
    async getGenderOptions() {
        const {currentUser} = this.state;
        const data = GENDER.getAll();
        const options = data.map(d => ({
            "label": d.name
        }))
        this.setState({gender: options})
    }

    // Get role options for dropdown
    async getRoleOptions() {
        let data
        const {currentUser} = this.state;
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

    /********
     *
     * Render
     *
     ********/
    //TODO maybe use send-method when enter is pressed in the inputfield
    render() {
        const {currentUser} = this.state;
        let {toggles} = this.state;
        //email field
        let email_field;
        if (!toggles.email) {
            email_field =
                <div><label>E-Mail</label>
                    <input
                        style={{
                            fontWeight: "600",
                            fontSize: "30px",
                            background: "none",
                            textAlign: "left",
                            marginLeft: "-14px"
                        }}
                        className="form-control border-0"
                        value={currentUser.email}
                        disabled={true}/>
                    <Button
                        variant="outline-primary"
                        size='sm'
                        onClick={this.toggle_email}>bearbeiten</Button>
                </div>;
        } else {
            email_field = <div>
                <label>E-Mail</label>
                <input
                    type="email"
                    className="form-control"
                    placeholder="E-Mail"
                    value={currentUser.email}
                    onChange={this.onChange_email}/>
                <Button variant="link" size='sm' onClick={this.toggle_email}>abbrechen</Button>
                <Button variant="link" size='sm' style={{color: '#229954'}}
                        onClick={this.send_email}>bestätigen</Button>
            </div>;
        }
        //role field
        let role_field;
        if (!toggles.role) {
            role_field =
                <div>
                    <input
                        style={{
                            fontWeight: "600",
                            fontSize: "20px",
                            background: "none",
                            textAlign: "left",
                            marginLeft: "-14px"
                        }}
                        className="form-control border-0"
                        value={currentUser.choosenRole}
                        disabled={true}/>
                    <Button
                        variant="outline-primary"
                        size='sm'
                        onClick={this.toggle_role}>neue Rolle zuweisen</Button>
                </div>;
        } else {
            role_field = <div>
                <Select
                    type="role"
                    placeholder="Rolle"
                    options={this.state.role}
                    //value={currentUser.choosenRole}
                    onChange={this.onChange_role}/>
                <Button variant="link" size='sm' onClick={this.toggle_role}>abbrechen</Button>
                <Button variant="link" size='sm' style={{color: '#229954'}}
                        onClick={this.send_role}>bestätigen</Button>
            </div>;
        }
        //fName field
        let fName_field;
        if (!toggles.fName) {
            fName_field =
                <div><label>Vorname</label>
                    <input
                        style={{
                            fontWeight: "600",
                            fontSize: "20px",
                            background: "none",
                            textAlign: "left",
                            marginLeft: "-14px"
                        }}
                        className="form-control border-0"
                        value={currentUser.fName}
                        disabled={true}/>
                    <Button variant="outline-primary"
                            size='sm'
                            onClick={this.toggle_fName}>bearbeiten</Button>
                </div>;
        } else {
            fName_field = <div>
                <label>Vorname</label>
                <input
                    type="name"
                    className="form-control"
                    placeholder="Vorname"
                    value={currentUser.fName}
                    onChange={this.onChange_fName}/>
                <Button variant="link" size='sm' onClick={this.toggle_fName}>abbrechen</Button>
                <Button variant="link" size='sm' style={{color: '#229954'}}
                        onClick={this.send_fName}>bestätigen</Button>
            </div>;
        }
        //lName field
        let lName_field;
        if (!toggles.lName) {
            lName_field =
                <div><label>Nachname</label>
                    <input
                        style={{
                            fontWeight: "600",
                            fontSize: "20px",
                            background: "none",
                            textAlign: "left",
                            marginLeft: "-14px"
                        }}
                        className="form-control border-0"
                        value={currentUser.lName}
                        disabled={true}/>
                    <Button variant="outline-primary"
                            size='sm'
                            onClick={this.toggle_lName}>bearbeiten</Button>
                </div>;
        } else {
            lName_field = <div>
                <label>Nachname</label>
                <input
                    type="nachname"
                    className="form-control"
                    placeholder="Nachname"
                    value={currentUser.lName}
                    onChange={this.onChange_lName}/>
                <Button variant="link" size='sm' onClick={this.toggle_lName}>abbrechen</Button>
                <Button variant="link" size='sm' style={{color: '#229954'}}
                        onClick={this.send_lName}>bestätigen</Button>
            </div>;
        }
        //gender field
        let gender_field;
        if (!toggles.gender) {
            gender_field =
                <div><label>Geschlecht</label>
                    <input
                        style={{
                            fontWeight: "600",
                            fontSize: "20px",
                            background: "none",
                            textAlign: "left",
                            marginLeft: "-14px"
                        }}
                        className="form-control border-0"
                        value={currentUser.choosenGender}
                        disabled={true}/>
                    <Button
                        variant="outline-primary"
                        size='sm'
                        onClick={this.toggle_gender}>ändern</Button>
                </div>;
        } else {
            gender_field = <div><label>Geschlecht</label>
                <Select
                    type="gender"
                    placeholder="Geschlecht"
                    options={this.state.gender}
                    //value={currentUser.choosenGender}
                    onChange={this.onChange_gender}/>
                <Button variant="link" size='sm' onClick={this.toggle_gender}>abbrechen</Button>
                <Button variant="link" size='sm' style={{color: '#229954'}}
                        onClick={this.send_gender}>bestätigen</Button>
            </div>;
        }
        //dateOfBirth field
        let dateOfBirth_field;
        if (!toggles.dateOfBirth) {
            dateOfBirth_field =
                <div><label>Geburtsdatum</label>
                    <input
                        style={{
                            fontWeight: "600",
                            fontSize: "20px",
                            background: "none",
                            textAlign: "left",
                            marginLeft: "-14px"
                        }}
                        className="form-control border-0"
                        value={currentUser.dateOfBirth}
                        disabled={true}/>
                    <Button
                        variant="outline-primary"
                        size='sm'
                        onClick={this.toggle_dateOfBirth}>ändern</Button>
                </div>;
        } else {
            dateOfBirth_field = <div><label>Geburtsdatum</label><br/>
                <DatePicker
                    selected={new Date()}
                    onChange={this.onChange_dateOfBirth}
                    peekNextMonth
                    showMonthDropdown
                    showYearDropdown
                    dropdownMode="select"
                    customInput={<Button variant="outline-secondary" size="lg"
                                         block>{currentUser.dateOfBirth}</Button>}
                /> <br/>
                <Button variant="link" size='sm' onClick={this.toggle_dateOfBirth}>abbrechen</Button>
                <Button variant="link" size='sm' style={{color: '#229954'}}
                        onClick={this.send_dateOfBirth}>bestätigen</Button>
            </div>;
        }
        //street field
        let street_field;
        if (!toggles.street) {
            street_field =
                <div><label>Straße</label>
                    <input
                        style={{
                            fontWeight: "600",
                            fontSize: "20px",
                            background: "none",
                            textAlign: "left",
                            marginLeft: "-14px"
                        }}
                        className="form-control border-0"
                        value={currentUser.street}
                        disabled={true}/>
                    <Button variant="outline-primary"
                            size='sm'
                            onClick={this.toggle_street}>bearbeiten</Button>
                </div>;
        } else {
            street_field = <div>
                <label>Straße</label>
                <input
                    type="straße"
                    className="form-control"
                    placeholder="Straße"
                    value={currentUser.street}
                    onChange={this.onChange_street}/>
                <Button variant="link" size='sm' onClick={this.toggle_street}>abbrechen</Button>
                <Button variant="link" size='sm' style={{color: '#229954'}}
                        onClick={this.send_street}>bestätigen</Button>
            </div>;
        }
        //hNumber field
        let hNumber_field;
        if (!toggles.hNumber) {
            hNumber_field =
                <div><label>Haus Nr.</label>
                    <input
                        style={{
                            fontWeight: "600",
                            fontSize: "20px",
                            background: "none",
                            textAlign: "left",
                            marginLeft: "-14px"
                        }}
                        className="form-control border-0"
                        value={currentUser.hNumber}
                        disabled={true}/>
                    <Button variant="outline-primary"
                            size='sm'
                            onClick={this.toggle_hNumber}>bearbeiten</Button>
                </div>;
        } else {
            hNumber_field = <div>
                <label>Haus Nr.</label>
                <input
                    type="hNummer"
                    className="form-control"
                    placeholder="Haus Nr."
                    value={currentUser.hNumber}
                    onChange={this.onChange_hNumber}/>
                <Button variant="link" size='sm' onClick={this.toggle_hNumber}>abbr.</Button>
                <Button variant="link" size='sm' style={{color: '#229954'}}
                        onClick={this.send_hNumber}>best.</Button>
            </div>;
        }
        //plz field
        let plz_field;
        if (!toggles.plz) {
            plz_field =
                <div><label>PLZ</label>
                    <input
                        style={{
                            fontWeight: "600",
                            fontSize: "20px",
                            background: "none",
                            textAlign: "left",
                            marginLeft: "-14px"
                        }}
                        className="form-control border-0"
                        value={currentUser.plz}
                        disabled={true}/>
                    <Button variant="outline-primary"
                            size='sm'
                            onClick={this.toggle_plz}>bearbeiten</Button>
                </div>;
        } else {
            plz_field = <div>
                <label>PLZ</label>
                <input
                    type="plz"
                    className="form-control"
                    placeholder="PLZ"
                    value={currentUser.plz}
                    onChange={this.onChange_plz}/>
                <Button variant="link" size='sm' onClick={this.toggle_plz}>abbr.</Button>
                <Button variant="link" size='sm' style={{color: '#229954'}}
                        onClick={this.send_plz}>best.</Button>
            </div>;
        }
        //city field
        let city_field;
        if (!toggles.city) {
            city_field =
                <div><label>Ort</label>
                    <input
                        style={{
                            fontWeight: "600",
                            fontSize: "20px",
                            background: "none",
                            textAlign: "left",
                            marginLeft: "-14px"
                        }}
                        className="form-control border-0"
                        value={currentUser.city}
                        disabled={true}/>
                    <Button variant="outline-primary"
                            size='sm'
                            onClick={this.toggle_city}>bearbeiten</Button>
                </div>;
        } else {
            city_field = <div>
                <label>Ort</label>
                <input
                    type="Ort"
                    className="form-control"
                    placeholder="Ort"
                    value={currentUser.city}
                    onChange={this.onChange_city}/>
                <Button variant="link" size='sm' onClick={this.toggle_city}>abbrechen</Button>
                <Button variant="link" size='sm' style={{color: '#229954'}}
                        onClick={this.send_city}>bestätigen</Button>
            </div>;
        }
        //full Render
        return (
            <div>
                {currentUser ? (
                    <div className="container">
                        {/************************************************************/}
                        <div className="form-row">
                            <div className="form-group col-md-9">
                                {email_field}
                            </div>
                            <div className="form-group col-md-3">
                                <Button
                                    variant="outline-primary"
                                    href="/login/resetPassword">
                                    Neues Passwort setzen
                                </Button>
                                <p/>
                                {role_field}
                            </div>
                        </div>
                        {/************************************************************/}
                        <hr/>
                        <div className="form-row">
                            <div className="form-group col-md-3">
                                {fName_field}
                            </div>
                            <div className="form-group col-md-3">
                                {lName_field}
                            </div>
                            <div className="form-group col-md-3">
                                {gender_field}

                            </div>
                            <div className="form-group col-md-3">
                                {dateOfBirth_field}
                            </div>


                        </div>
                        <br/>
                        {/************************************************************/}
                        <hr/>
                        <div className="form-row">
                            <div className="form-group col-md-auto">
                                {street_field}
                            </div>
                            <div className="form-group col-md-2">
                                {hNumber_field}
                            </div>
                            <div className="form-group col-md-2">
                                {plz_field}
                            </div>
                            <div className="form-group col-md-auto">
                                {city_field}
                            </div>
                        </div>
                    </div>
                ) : (
                    <div>
                        <p>Test...</p>
                    </div>
                )}
            </div>
        );
    }

    /********
     * (3) Changefunctions for every field
     *
     *  1.Changefunction -> write new input values
     *  2.togglefunction -> boolean if a field is editable
     *  3.sendfunction -> sends a specific field to be updated in DB
     ********/
        //email
    onChange_email = (e) => {
        const email = e.target.value;
        if (this.state.currentUser.email === sessionStorage.getItem("sessionEmail")) {
            this.setState({updateSession: true})
        }
        this.setState(function (prevState) {
            return {
                currentUser: {
                    ...prevState.currentUser,
                    email: email
                }
            };
        });
    }
    toggle_email = () => {
        let {toggles} = this.state;
        toggles.email = !toggles.email;
        this.setState({toggles: toggles})
        this.getUser(this.state.currentUser.id)
    }
    send_email = () => {
        UserDataService.update(
            this.state.currentUser.id,
            {email: this.state.currentUser.email}
        )
            .then(response => {

                if (this.state.updateSession) {
                    sessionStorage.setItem("sessionEmail", this.state.currentUser.email)
                    this.setState({updateSession: false})
                }

                console.log(response.data);
                this.toggle_email();
                this.setState({
                    message: "The user was updated successfully!"
                });
            })
            .catch(e => {
                console.log(e);
            });
    }
    //role
    onChange_role = (e) => {
        const role = e.label;
        this.setState(function (prevState) {
            return {
                currentUser: {
                    ...prevState.currentUser,
                    choosenRole: role
                }
            };
        });
    }
    toggle_role = () => {
        let {toggles} = this.state;
        toggles.role = !toggles.role;
        this.setState({toggles: toggles})
        this.getUser(this.state.currentUser.id)
    }
    send_role = () => {
        UserDataService.update(
            this.state.currentUser.id,
            {role: this.state.currentUser.choosenRole}
        )
            .then(response => {
                console.log(response.data);
                this.toggle_role();
                this.setState({
                    message: "The user was updated successfully!"
                });
            })
            .catch(e => {
                console.log(e);
            });
    }
    //fName
    onChange_fName = (e) => {
        const fName = e.target.value;
        this.setState(function (prevState) {
            return {
                currentUser: {
                    ...prevState.currentUser,
                    fName: fName
                }
            };
        });
    }
    toggle_fName = () => {
        let {toggles} = this.state;
        toggles.fName = !toggles.fName;
        this.setState({toggles: toggles})
        this.getUser(this.state.currentUser.id)
    }
    send_fName = () => {
        UserDataService.update(
            this.state.currentUser.id,
            {fName: this.state.currentUser.fName}
        )
            .then(response => {
                console.log(response.data);
                this.toggle_fName();
                this.setState({
                    message: "The user was updated successfully!"
                });
            })
            .catch(e => {
                console.log(e);
            });
    }
    //lName
    onChange_lName = (e) => {
        const lName = e.target.value;

        this.setState(function (prevState) {
            return {
                currentUser: {
                    ...prevState.currentUser,
                    lName: lName
                }
            };
        });
    }
    toggle_lName = () => {
        let {toggles} = this.state;
        toggles.lName = !toggles.lName;
        this.setState({toggles: toggles})
        this.getUser(this.state.currentUser.id)
    }
    send_lName = () => {
        UserDataService.update(
            this.state.currentUser.id,
            {lName: this.state.currentUser.lName}
        )
            .then(response => {
                console.log(response.data);
                this.toggle_lName();
                this.setState({
                    message: "The user was updated successfully!"
                });
            })
            .catch(e => {
                console.log(e);
            });
    }
    //gender
    onChange_gender = (e) => {
        const gender = e.label;
        this.setState(function (prevState) {
            return {
                currentUser: {
                    ...prevState.currentUser,
                    choosenGender: gender
                }
            };
        });
    }
    toggle_gender = () => {
        let {toggles} = this.state;
        toggles.gender = !toggles.gender;
        this.setState({toggles: toggles})
        this.getUser(this.state.currentUser.id)
    }
    send_gender = () => {
        UserDataService.update(
            this.state.currentUser.id,
            {gender: this.state.currentUser.choosenGender}
        )
            .then(response => {
                console.log(response.data);
                this.toggle_gender();
                this.setState({
                    message: "The user was updated successfully!"
                });
            })
            .catch(e => {
                console.log(e);
            });
    }
    //dateOfBirth
    onChange_dateOfBirth = (date) => {
        const myDate = date.toLocaleDateString()
        console.log("Writing BD: " + myDate)
        this.setState(function (prevState) {
            return {
                currentUser: {
                    ...prevState.currentUser,
                    dateOfBirth: myDate
                }
            };
        });
    }
    toggle_dateOfBirth = () => {
        let {toggles} = this.state;
        toggles.dateOfBirth = !toggles.dateOfBirth;
        this.setState({toggles: toggles})
        this.getUser(this.state.currentUser.id)
    }
    send_dateOfBirth = () => {
        UserDataService.update(
            this.state.currentUser.id,
            {dateOfBirth: this.state.currentUser.dateOfBirth}
        )
            .then(response => {
                console.log(response.data);
                this.toggle_dateOfBirth();
                this.setState({
                    message: "The user was updated successfully!"
                });
            })
            .catch(e => {
                console.log(e);
            });
    }
    //street
    onChange_street = (e) => {
        const street = e.target.value;

        this.setState(function (prevState) {
            return {
                currentUser: {
                    ...prevState.currentUser,
                    street: street
                }
            };
        });
    }
    toggle_street = () => {
        let {toggles} = this.state;
        toggles.street = !toggles.street;
        this.setState({toggles: toggles})
        this.getUser(this.state.currentUser.id)
    }
    send_street = () => {
        UserDataService.update(
            this.state.currentUser.id,
            {street: this.state.currentUser.street}
        )
            .then(response => {
                console.log(response.data);
                this.toggle_street();
                this.setState({
                    message: "The user was updated successfully!"
                });
            })
            .catch(e => {
                console.log(e);
            });
    }
    //hNumber
    onChange_hNumber = (e) => {
        const hNumber = e.target.value;

        this.setState(function (prevState) {
            return {
                currentUser: {
                    ...prevState.currentUser,
                    hNumber: hNumber
                }
            };
        });
    }
    toggle_hNumber = () => {
        let {toggles} = this.state;
        toggles.hNumber = !toggles.hNumber;
        this.setState({toggles: toggles})
        this.getUser(this.state.currentUser.id)
    }
    send_hNumber = () => {
        UserDataService.update(
            this.state.currentUser.id,
            {hNumber: this.state.currentUser.hNumber}
        )
            .then(response => {
                console.log(response.data);
                this.toggle_hNumber();
                this.setState({
                    message: "The user was updated successfully!"
                });
            })
            .catch(e => {
                console.log(e);
            });
    }
    //plz
    onChange_plz = (e) => {
        const plz = e.target.value;

        this.setState(function (prevState) {
            return {
                currentUser: {
                    ...prevState.currentUser,
                    plz: plz
                }
            };
        });
    }
    toggle_plz = () => {
        let {toggles} = this.state;
        toggles.plz = !toggles.plz;
        this.setState({toggles: toggles})
        this.getUser(this.state.currentUser.id)
    }
    send_plz = () => {
        UserDataService.update(
            this.state.currentUser.id,
            {plz: this.state.currentUser.plz}
        )
            .then(response => {
                console.log(response.data);
                this.toggle_plz();
                this.setState({
                    message: "The user was updated successfully!"
                });
            })
            .catch(e => {
                console.log(e);
            });
    }
    //city
    onChange_city = (e) => {
        const city = e.target.value;

        this.setState(function (prevState) {
            return {
                currentUser: {
                    ...prevState.currentUser,
                    city: city
                }
            };
        });
    }
    toggle_city = () => {
        let {toggles} = this.state;
        toggles.city = !toggles.city;
        this.setState({toggles: toggles})
        this.getUser(this.state.currentUser.id)

    }
    send_city = () => {
        UserDataService.update(
            this.state.currentUser.id,
            {city: this.state.currentUser.city}
        )
            .then(response => {
                console.log(response.data);
                this.toggle_city();
                this.setState({
                    message: "The user was updated successfully!"
                });
            })
            .catch(e => {
                console.log(e);
            });
    }

    onChange_password = (e) => {
        const password = e.target.value;

        this.setState(function (prevState) {
            return {
                currentUser: {
                    ...prevState.currentUser,
                    password: password
                }
            };
        });
    }

    onChange_passwordCheck = (e) => {
        const passwordCheck = e.target.value;

        this.setState(function (prevState) {
            return {
                currentUser: {
                    ...prevState.currentUser,
                    passwordCheck: passwordCheck
                }
            };
        });
    }
    /*
        onChange_dateOfBirth = (date) => {
            this.setState({
                dateOfBirth: date
            })
        } */
//##########OLD Render##########
    /*
    render() {
        const {currentUser} = this.state;
        return (
            <div>
                {currentUser ? (
                    <div className="container">
                        <form>
                            <div className="form-row">
                                <div className="form-group col-md-6">
                                    <label>Vorname</label>
                                    <input
                                        type="name"
                                        className="form-control"
                                        placeholder="Vorname"
                                        value={currentUser.fName}
                                        onChange={this.onChange_fName}/>
                                </div>
                                <div className="form-group col-md-6">
                                    <label>Nachname</label>
                                    <input
                                        type="name"
                                        className="form-control"
                                        placeholder="Nachname"
                                        value={currentUser.lName}
                                        onChange={this.onChange_lName}/>
                                </div>
                            </div>
                            <div className="form-group">
                                <label>E-Mail</label>
                                <input
                                    type="email"
                                    className="form-control"
                                    placeholder="E-Mail"
                                    value={currentUser.email}
                                    onChange={this.onChange_email}/>
                            </div>
                            <div className="form-row">
                                <div className="form-group col-md-6">
                                    <label>Passwort</label>
                                    <input
                                        type="password"
                                        className="form-control"
                                        placeholder="Passwort"
                                        value={currentUser.password}
                                        onChange={this.onChange_password}/>
                                </div>
                                <div className="form-group col-md-6">
                                    <label>Passwort bestätigen</label>
                                    <input
                                        type="password"
                                        className="form-control"
                                        placeholder="Passwort"
                                        value={currentUser.passwordCheck}
                                        onChange={this.onChange_passwordCheck}/>
                                </div>
                            </div>
                            <div className="form-row">
                                <div className="form-group col-md-10">
                                    <label>Straße</label>
                                    <input
                                        type="street"
                                        className="form-control"
                                        placeholder="Straße"
                                        value={currentUser.street}
                                        onChange={this.onChange_street}/>
                                </div>
                                <div className="form-group col-md-2">
                                    <label>Hausnummer</label>
                                    <input
                                        type="hNumber"
                                        className="form-control"
                                        placeholder="Hausnummer"
                                        value={currentUser.hNumber}
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
                                        value={currentUser.plz}
                                        onChange={this.onChange_plz}/>
                                </div>
                                <div className="form-group col-md-9">
                                    <label>Ort</label>
                                    <input
                                        type="ort"
                                        className="form-control"
                                        placeholder="Ort"
                                        value={currentUser.city}
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
                                        value={currentUser.choosenGender}
                                        onChange={this.onChange_gender}/>
                                </div>
                                <div className="form-group col-md-6">
                                    <label>Rolle</label>
                                    <Select
                                        type="role"
                                        placeholder="Rolle"
                                        options={this.state.role}
                                        value={currentUser.choosenRole}
                                        onChange={this.onChange_role}/>
                                </div>
                            </div>
                        </form>
                        <button
                            type="submit"
                            className="btn btn-primary"
                            onClick={this.updateUser}
                        >
                            Bestätigen
                        </button>
                    </div>
                ) : (
                    <div>
                        <p>Test...</p>
                    </div>
                )}
            </div>
        );
    }
    //##########unused update in DB method##########
    updateUser = (payload) => {
        UserDataService.update(
            this.state.currentUser.id,
            payload
        )
            .then(response => {
                console.log(response.data);
                this.setState({
                    message: "The user was updated successfully!"
                });
            })
            .catch(e => {
                console.log(e);
            });
    }
    */
}
