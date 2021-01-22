import React, {Component} from 'react';
import DatePicker from 'react-datepicker';
import "react-datepicker/dist/react-datepicker.css";
import "bootstrap/dist/css/bootstrap.min.css";
import UserDataService from "../../services/user.service";
import RoleDataService from "../../services/role.service";
import GenderDataService from "../../services/gender.service";
import Select from 'react-select';

// TODO: Geburtsdatum (DatePicker?!)
// TODO: wenn man ein Geschlecht/Rolle auswählt, aber dann danach ein anderes Geschlecht/Rolle auswählen möchte gibts einen Fehler

//##########Component imports##########

export default class moderation_createUser extends Component {
    //##########constructor##########
    constructor(props) {
        super(props);
        //prepare all fields and make sure the functions are bound to this object
        this.onChange_email = this.onChange_email.bind(this);
        this.onChange_password = this.onChange_password.bind(this);
        this.onChange_passwordCheck = this.onChange_passwordCheck.bind(this);
        this.onChange_foreName = this.onChange_foreName.bind(this);
        this.onChange_surName = this.onChange_surName.bind(this);
        this.onChange_address = this.onChange_address.bind(this);
        this.onChange_gender = this.onChange_gender.bind(this);
        this.onChange_role = this.onChange_role.bind(this);
        this.onChange_bday = this.onChange_bday.bind(this);

        this.onSubmit = this.onSubmit.bind(this);

        this.state = {
            email: '',
            password: '',
            passwordCheck: '',
            foreName: '',
            surName: '',
            address: '',
            gender: [],
            role: [],
            // bDay: new Date(),

            submitted: false
        }
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

    // Get role options for dropdown
    async getRoleOptions() {
        const res = await RoleDataService.getAll()
        const data = res.data

        const options = data.map(d => ({
            "label": d.name
        }))

        this.setState({role: options})
    }

    //##########Mount method (equals initialize!)##########
    componentDidMount() {
        this.getGenderOptions()
        this.getRoleOptions()
    }

    //##########Render##########
    render() {
        return (
            <div className="container">
                <h3>Nutzer erstellen</h3>
                <form onSubmit={this.onSubmit}>
                    <div className="form-group">
                        <label>E-Mail: </label>
                        <br/>
                        <input type="email" className="form-control" value={this.state.email}
                               onChange={this.onChange_email}/>
                    </div>

                    <div className="form-group">
                        <label>passwort: </label>
                        <br/>
                        <input type="password" className="form-control" value={this.state.password}
                               onChange={this.onChange_password}/>
                    </div>
                    <div className="form-group">
                        <label>passwort bestätigen: </label>
                        <br/>
                        <input type="password" className="form-control" value={this.state.passwordCheck}
                               onChange={this.onChange_passwordCheck}/>
                    </div>
                    <div className="form-group">
                        <label>Vorname: </label>
                        <br/>
                        <input type="name" className="form-control" value={this.state.foreName}
                               onChange={this.onChange_foreName}/>
                    </div>
                    <div className="form-group">
                        <label>Nachname: </label>
                        <br/>
                        <input type="name" className="form-control" value={this.state.surName}
                               onChange={this.onChange_surName}/>
                    </div>
                    <div className="form-group">
                        <label>Anschrift: </label>
                        <br/>
                        <input type="address" className="form-control" value={this.state.address}
                               onChange={this.onChange_address}/>
                    </div>
                    <div className="form-group">
                        <label>Geschlecht: </label>
                        <Select options={this.state.gender} onChange={this.onChange_gender}/>
                    </div>
                    <div className="form-group">
                        <label>Rolle: </label>
                        <Select options={this.state.role} onChange={this.onChange_role}/>
                    </div>
                    <div className="form-group">
                        <input type="submit" value="Nutzer erstellen" className="btn btn-primary"/>
                    </div>
                </form>
            </div>
        )
    }

    //##########submit method##########
    onSubmit(e) {
        //don't let any other submit run
        e.preventDefault();
        //create the object
        const user = {
            email: this.state.email,
            //TODO make password check
            password: this.state.password,
            name: this.state.foreName + " " + this.state.surName,
            address: this.state.address,
            gender: this.state.gender,
            role: this.state.role,
            bDay: this.state.bday
        }

        UserDataService.create(user)
            .then(res => {
                this.setState({
                    email: res.data.email,
                    password: res.data.password,
                    name: res.data.foreName + " " + res.data.surName,
                    address: res.data.address,
                    gender: res.data.gender,
                    role: res.data.role,
                    // TODO: bday

                    submitted: true
                });
                console.log(res.data);
            })
            .catch(e => {
                console.log(e);
            });

        //go back to the moderationView TODO: funktioniert nicht
        // window.location = '/login/moderation';
    }

    //##########change methods##########
    onChange_email(e) {
        this.setState({
            email: e.target.value //target equals a textBox target, value its value; Changes just the given state value
        })
    }

    onChange_password(e) {
        this.setState({
            password: e.target.value
        })
    }

    onChange_passwordCheck(e) {
        this.setState({
            passwordCheck: e.target.value
        })
    }

    onChange_foreName(e) {
        this.setState({
            foreName: e.target.value
        })
    }

    onChange_surName(e) {
        this.setState({
            surName: e.target.value
        })
    }

    onChange_address(e) {
        this.setState({
            address: e.target.value
        })
    }

    onChange_gender(e) {
        this.setState({
            gender: e.label
        })
    }

    onChange_role(e) {
        this.setState({
            role: e.label
        })
    }

    onChange_bday(date) {
        this.setState({
            bday: date
        })
    }
}