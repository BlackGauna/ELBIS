import React, {Component} from 'react';
import "bootstrap/dist/css/bootstrap.min.css";
import UserDataService from "../../services/user.service";
import EditIcon from '@material-ui/icons/Edit';
import DeleteIcon from '@material-ui/icons/Delete';
import IconButton from '@material-ui/core/IconButton';
import Modal from "react-bootstrap/Modal";
import CreateUser from '../moderation/moderation_createUser.component';
import EditUser from '../moderation/moderation_createUser.component';
import {ROLE} from "../../session/userRoles.ice";
import BootstrapTable from 'react-bootstrap-table-next';
import 'react-bootstrap-table-next/dist/react-bootstrap-table2.min.css';
import {Container} from "react-bootstrap";

//TODO: make sure an administrator can add and remove topics from a user

//TODO: show the dateOfBirth as "LocaleDate" somehow

const User = props => (
    <tr>
        <td>{props.user.email}</td>
        <td>{props.user.fName} {props.user.lName}</td>
        <td>{props.user.gender}</td>
        <td>{props.user.role}</td>
        <td>{props.user.street} {props.user.hNumber} <br/> {props.user.plz} {props.user.city}</td>
        <td>{props.user.dateOfBirth}</td>
        <td align="right">
            <IconButton aria-label="edit" href={"/login/mod/editUser/" + props.user._id}
                        disabled={checkMod(props.user.role)}>
                <EditIcon/>
            </IconButton>
            <IconButton aria-label="delete" disabled={checkMod(props.user.role)} href='#' onClick={() => {
                props.deleteUser(props.user._id)
            }}>
                <DeleteIcon/>
            </IconButton>
        </td>
    </tr>
)

function checkMod(againstRole) {
    if (sessionStorage.getItem("sessionRole") === ROLE.MODERATOR && (againstRole === ROLE.MODERATOR || againstRole === ROLE.ADMINISTRATOR)) {
        return true
    } else {
        return false
    }
}

export default class moderation_userList extends Component {

    // Constructor
    constructor(props) {
        super(props);

        const columns = [{
            dataField: 'email',
            text: 'E-Mail',
            sort: 'true',
        },
            {
                dataField: 'fName',
                text: 'Vorname',
                sort: 'true',
                headerStyle: {}
            },
            {
                dataField: 'lName',
                text: 'Nachname',
                sort: 'true'
            },
            {
                dataField: 'role',
                text: 'Rolle',
                sort: 'true'
            },
            {
                dataField: '_id',
                text: 'Rolle',
                sort: 'true',
                formatter: this.buttonFormatter,
                formatExtraData: {}
            },

        ];

        this.state = {
            showCreateUser: false,
            user: [],
            columns: columns
        }

    }

    handleModal() {
        this.setState({showCreateUser: !this.state.showCreateUser});
    }

    // Mount method
    componentDidMount() {
        UserDataService.getAll()
            .then(response => {
                this.setState({user: response.data})
            })
            .catch((error) => {
                console.log(error);
            });
    }

    // delete User method
    deleteUser = (id) => {
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

    buttonFormatter = (cell, row, rowIndex, formatExtraData) => {
        //TODO define delete/edit buttons via ID
        console.log(row)
        return (
            <div>
                <IconButton aria-label="delete" onClick={() => {
                    this.handleModal()
                }}> {row.email}
                </IconButton>


                <Modal show={this.state.showCreateUser} onHide={() => this.handleModal()} size="lg">
                    <Modal.Header>Nutzer anlegen</Modal.Header>
                    <Modal.Body>
                        <CreateUser/>
                    </Modal.Body>
                    <Modal.Footer>
                        <button className="btn btn-primary btn-sm" onClick={() => {
                            this.handleModal()
                        }}>Close
                        </button>
                    </Modal.Footer>
                </Modal>
            </div>
        )
    }

//##########Render##########
    render() {
        return (
            <div className="container">
                <h3>Nutzerverwaltung</h3>
                <Container style={{display: "flex"}}>
                    Level: {sessionStorage.getItem("sessionRole")}
                    <button className="btn btn-primary btn-sm" style={{marginLeft: "auto"}} onClick={() => {
                        this.handleModal()
                    }}>+
                    </button>
                </Container>

                <BootstrapTable
                    headerClasses="thead-light"
                    bordered={false}
                    bootstrap4={true}
                    keyField='email'
                    data={this.state.user}
                    columns={this.state.columns}/>

                <Modal show={this.state.showCreateUser} onHide={() => this.handleModal()} size="lg">
                    <Modal.Header>Nutzer anlegen</Modal.Header>
                    <Modal.Body>
                        <CreateUser/>
                    </Modal.Body>
                    <Modal.Footer>
                        <button className="btn btn-primary btn-sm" onClick={() => {
                            this.handleModal()
                        }}>Close
                        </button>
                    </Modal.Footer>
                </Modal>
            </div>
        )
    }

}

