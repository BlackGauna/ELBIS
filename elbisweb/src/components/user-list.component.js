import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import axios from 'axios';
import "bootstrap/dist/css/bootstrap.min.css";

const User = props => (
    <tr>
        <td>{props.user.email}</td>
        <td>{props.user.password}</td>
        <td>{props.user.role}</td>
        <td>{props.user.name}</td>
        <td>{props.user.address}</td>
        <td>{props.user.gender}</td>
        <td>{props.user.dateOfBirth}</td>
        <td>
            <Link to={"/edit/"+props.user._id}>edit</Link> | <a href='#' onClick={() => {props.deleteUser(props.user._id)}}>delete</a>
        </td>
    </tr>
)

export default class ShowUser extends Component{
    constructor(props){
        super(props);

        this.deleteUser = this.deleteUser.bind(this);
        this.state = {user: []};
    }

    componentDidMount() {
        axios.get('http://localhost:5000/user/')
            .then(response => {
                this.setState({user: response.data})
            })
            .catch((error) => {
                console.log(error);
            })
    }

    deleteUser(id){
        axios.delete('http://localhost:5000/user/'+id)
            .then(res => console.log(res.data));
        this.setState({
            user: this.state.user.filter(el => el._id !== id)
        })
    }

    userList(){
        return this.state.user.map(currentUser => {
            return <User user={currentUser} deleteUser={this.deleteUser} key={currentUser._id}>;
            </User>
        })
    }

    render(){
        return (
            <div>
                <h3>Users</h3>
                <table className="userTable">
                    <thead className="thead-light">
                    <tr>
                        <th>E-Mail</th>
                        <th>Passwort</th>
                        <th>Rolle</th>
                        <th>Name</th>
                        <th>Adresse</th>
                        <th>Gender</th>
                        <th>Geburtsdatum</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.userList()}
                    </tbody>
                </table>
            </div>
            )
    }
}