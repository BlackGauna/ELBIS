import React, {Component} from 'react';
import "bootstrap/dist/css/bootstrap.min.css";
import UserDataService from "../services/user.service";
import GenderDataService from "../services/gender.service";
import "bootstrap/dist/css/bootstrap.min.css";
import Select from 'react-select';
export default class manageAccount extends Component {

    constructor(props){
        super(props);
        this.onChange_email = this.onChange_email.bind(this);
        this.onChange_password = this.onChange_password.bind(this);
        this.onChange_passwordCheck = this.onChange_passwordCheck.bind(this);
        this.onChange_foreName = this.onChange_foreName.bind(this);
        this.onChange_surName = this.onChange_surName.bind(this);
        this.onChange_street = this.onChange_street.bind(this);
        this.onChange_houseNumber = this.onChange_houseNumber.bind(this);
        this.onChange_plz = this.onChange_plz.bind(this);
        this.onChange_place = this.onChange_place.bind(this);
        this.onChange_gender = this.onChange_gender.bind(this);

        this.getUser = this.getUser.bind(this);
        this.updateUser = this.updateUser.bind(this);

        this.state = {
            currentUser: {
                id: null,
                email: '',
                password: '',
                passwordCheck: '',
                foreName: '',
                surName: '',
                street: '',
                houseNumber: '',
                plz: '',
                place: '',
                gender: [],
            }

        };
    }

    componentDidMount() {
        this.getUser(this.props.match.params.id);
        this.getGenderOptions()
    }

    getUser(){
        UserDataService.get(sessionStorage.getItem("sessionUserID"))
            .then(response => {
                this.setState({
                    currentUser: {
                        email: response.data.email,
                        password: response.data.password,
                        passwordCheck: response.data.password,
                    }
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    // Get gender options for dropdown
    async getGenderOptions() {
        const res = await GenderDataService.getAll()
        const data = res.data


        const options = data.map(d => ({
            "label": d.name
        }))

        this.setState({gender: options})
    }

    //##########update in DB method########## //TODO not working yet
    updateUser() {
        UserDataService.update(
            this.state.currentUser.id,
            this.state.currentUser
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

    //##########Render##########
    render() {
        const {currentUser} = this.state;
        return (
            <div>
                {currentUser ? (
                    <div className="container">
                        <h3>Hallo {sessionStorage.getItem("sessionEmail")} </h3>
                        Hier kannst du deinen Account verwalten. <p/>
                        <form>
                            <div className="form-row">
                                <div className="form-group col-md-6">
                                    <label>Vorname</label>
                                    <input
                                        type="name"
                                        className="form-control"
                                        placeholder="Vorname"
                                        value={currentUser.foreName}
                                        onChange={this.onChange_foreName}/>
                                </div>
                                <div className="form-group col-md-6">
                                    <label>Nachname</label>
                                    <input
                                        type="name"
                                        className="form-control"
                                        placeholder="Nachname"
                                        value={currentUser.surName}
                                        onChange={this.onChange_surName}/>
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
                                        type="houseNumber"
                                        className="form-control"
                                        placeholder="Hausnummer"
                                        value={currentUser.houseNumber}
                                        onChange={this.onChange_houseNumber}/>
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
                                        value={currentUser.place}
                                        onChange={this.onChange_place}/>
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

//##########change methods##########
    onChange_email(e) {
        const email = e.target.value;

        this.setState(function(prevState){
            return{
                currentUser: {
                    ...prevState.currentUser,
                    email: email
                }
            };
        });
    }


    onChange_password(e) {
        const password = e.target.value;

        this.setState(function(prevState){
            return{
                currentUser: {
                    ...prevState.currentUser,
                    password: password
                }
            };
        });
    }

    onChange_passwordCheck(e) {
        const passwordCheck = e.target.value;

        this.setState(function(prevState){
            return{
                currentUser: {
                    ...prevState.currentUser,
                    passwordCheck: passwordCheck
                }
            };
        });
    }

    onChange_foreName(e) {
        const foreName = e.target.value;

        this.setState(function(prevState){
            return{
                currentUser: {
                    ...prevState.currentUser,
                    foreName: foreName
                }
            };
        });
    }

    onChange_surName(e) {
        const surName = e.target.value;

        this.setState(function(prevState){
            return{
                currentUser: {
                    ...prevState.currentUser,
                    surName: surName
                }
            };
        });
    }

    onChange_street(e) {
        const street = e.target.value;

        this.setState(function(prevState){
            return{
                currentUser: {
                    ...prevState.currentUser,
                    street: street
                }
            };
        });
    }

    onChange_houseNumber(e) {
        const houseNumber = e.target.value;

        this.setState(function(prevState){
            return{
                currentUser: {
                    ...prevState.currentUser,
                    houseNumber: houseNumber
                }
            };
        });
    }

    onChange_plz(e) {
        const plz = e.target.value;

        this.setState(function(prevState){
            return{
                currentUser: {
                    ...prevState.currentUser,
                    plz: plz
                }
            };
        });
    }

    onChange_place(e) {
        const place = e.target.value;

        this.setState(function(prevState){
            return{
                currentUser: {
                    ...prevState.currentUser,
                    place: place
                }
            };
        });
    }

    onChange_gender(e) {
        const gender = e.target.value;

        this.setState(function(prevState){
            return{
                currentUser: {
                    ...prevState.currentUser,
                    gender: gender
                }
            };
        });
    }
}