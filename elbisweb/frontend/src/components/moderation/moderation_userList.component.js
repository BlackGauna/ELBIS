import React, {Component} from 'react';
import {BrowserRouter as Router, Link} from 'react-router-dom';
import "bootstrap/dist/css/bootstrap.min.css";
import UserDataService from "../../services/user.service";
import EditIcon from '@material-ui/icons/Edit';
import DeleteIcon from '@material-ui/icons/Delete';
import IconButton from '@material-ui/core/IconButton';
import Modal from "react-bootstrap/Modal";
import AddUser from '../moderation/moderation_createUser.component'

//TODO: edit User (initial values of the form should be loaded from db)
//TODO: make sure moderators can just edit and create users
//TODO: make sure an administrator can edit and create users, mods and admins
//TODO: make sure an administrator can add and remove topics from a user

//TODO: show the dateOfBirth as "LocaleDate" somehow

const User = props => (
    <tr>
        <td>{props.user.email}</td>
        <td>{props.user.name}</td>
        <td>{props.user.gender}</td>
        <td>{props.user.role}</td>
        <td>{props.user.address}</td>
        <td>{props.user.dateOfBirth}</td>
        <td align="right">
            <IconButton aria-label="edit" href={"/login/mod/editUser/" + props.user._id}>
                <EditIcon />
            </IconButton>
            <IconButton aria-label="delete" href='#' onClick={() => { props.deleteUser(props.user._id)}}>
                <DeleteIcon />
            </IconButton>
        </td>
    </tr>
)



export default class moderation_userList extends Component {

    // Constructor
    constructor(props) {
        super(props);

        this.deleteUser = this.deleteUser.bind(this);
        this.state = {
            show: false,
            user: []
        }

    }

    handleModal(){
        this.setState({show:!this.state.show});
    }

    // Mount method
    componentDidMount() {
        UserDataService.getAll()
            .then(response => {
                this.setState({user: response.data})
            })
            .catch((error) => {
                console.log(error);
            })
    }

    // delete User method
    deleteUser(id) {
        UserDataService.delete(id)
            .then(res => console.log(res.data));
        this.setState({
            user: this.state.user.filter(el => el._id !== id)
        })
    }

    // get user list
    userList() {
        return this.state.user.map(currentUser => {
            return <User user={currentUser} deleteUser={this.deleteUser} key={currentUser._id}/>;
        })
    }

//##########Render##########
    render() {
        return (
            <div className="container">
                <h3>Nutzerverwaltung</h3> Level: {sessionStorage.getItem("sessionRole")}
                <table className="userTable table" >
                    <thead className="thead-light">
                    <tr>
                        <th data-mdb-sort="true">E-Mail</th>
                        <th>Name</th>
                        <th>Geschlecht</th>
                        <th>Rolle</th>
                        <th>Anschrift</th>
                        <th>Geburtsdatum</th>


                        <th className={"text-right"}>
                            <button className="btn btn-primary btn-sm" onClick={()=>{this.handleModal()}}>+</button>
                        </th>

                        <Modal show={this.state.show} onHide={()=>this.handleModal()} size="lg">
                            <Modal.Header>Nutzer anlegen</Modal.Header>
                            <Modal.Body>
                                <AddUser/>
                            </Modal.Body>
                            <Modal.Footer>
                                <button className="btn btn-primary btn-sm" onClick={()=>{this.handleModal()}}>Close</button>
                            </Modal.Footer>
                        </Modal>


                        {/*<th className={"text-right"}><Link to="/login/mod/createUser">*/}
                        {/*    <button className="btn btn-primary btn-sm">+</button>*/}
                        {/*</Link></th>*/}
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

