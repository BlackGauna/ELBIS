import React, {Component} from 'react';
import "bootstrap/dist/css/bootstrap.min.css";
import UserDataService from "../../services/user.service";
import EditIcon from '@material-ui/icons/Edit';
import DeleteIcon from '@material-ui/icons/Delete';
import IconButton from '@material-ui/core/IconButton';
import Modal from "react-bootstrap/Modal";
import CreateUser from '../moderation/moderation_createUser.component';
import EditUser from '../moderation/moderation_editUser.component';
import {ROLE} from "../../session/userRoles.ice";
import BootstrapTable from 'react-bootstrap-table-next';
import 'react-bootstrap-table-next/dist/react-bootstrap-table2.min.css';
import {Container} from "react-bootstrap";

//TODO: make sure an administrator can add and remove topics from a user
//TODO: show the dateOfBirth as "LocaleDate" somehow

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

        // TODO: Vorname+Nachname in eine Spalte; Straße, Hausnummer, PLZ und Ort in eine Spalte

        const columns = [
            {
                dataField: 'email',
                text: 'E-Mail',
                sort: 'true',
            },
            {
                dataField: 'fName',
                text: 'Vorname',
                sort: 'true',
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
                dataField: 'gender',
                text: 'Geschlecht',
                sort: 'true'
            },
            {
                dataField: '_id',
                text: 'Aktion',
                sort: 'true',
                formatter: this.buttonFormatter,
                // formatExtraData: {}
            },

        ];

        this.state = {
            showCreateUser: false,
            showEditUser: false,
            user: [],
            columns: columns
        }
    }

    handleCreateModal() {
        this.setState({showCreateUser: !this.state.showCreateUser});
    }

    handleEditModal(){
        this.setState({showEditUser: !this.state.showEditUser});
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
        if(window.confirm('Möchten Sie den ausgewählten Nutzer löschen?')){
            UserDataService.delete(id)
                .then(res => console.log(res.data));
            this.setState({
                user: this.state.user.filter(el => el._id !== id)
            })
        } else {

        }

    }

    buttonFormatter = (cell, row, rowIndex, formatExtraData) => {
        return (
            <div>

                {/*Edit and delete buttons in each row*/}

                <div>
                    <IconButton
                        aria-label="edit"
                        onClick={() => this.handleEditModal(row._id)}
                        // href={"/login/mod/editUser/" + row._id}
                        disabled={checkMod(row.role)}> <EditIcon/>
                    </IconButton>

                    <IconButton aria-label="delete" href='#' onClick={() => {
                        this.deleteUser(row._id)}}>
                    <DeleteIcon/>
                </IconButton>
                </div>


                {/*Open modal to edit a user*/}
                {/*TODO: implement edit modal)*/}

                {/*Old code:*/}
                {/*<IconButton aria-label="edit" href={"/login/mod/editUser/" + props.user._id}*/}
                {/*            disabled={checkMod(props.user.role)}>*/}
                {/*    <EditIcon/>*/}
                {/*</IconButton>*/}

                <Modal show={this.state.showEditUser} onHide={() => this.handleEditModal() + row._id} size="lg">
                    <Modal.Header>Nutzer bearbeiten</Modal.Header>
                    <Modal.Body>
                        <EditUser/>
                    </Modal.Body>
                    <Modal.Footer>
                        <button className="btn btn-primary btn-sm" onClick={() => {
                            this.handleEditModal()
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

                    {/*+ Button to open the create modal*/}

                    <button className="btn btn-primary btn-sm" style={{marginLeft: "auto"}} onClick={() => {
                        this.handleCreateModal()
                    }}>+
                    </button>

                </Container>

                {/*Print the table*/}

                <BootstrapTable
                    headerClasses="thead-light"
                    bordered={false}
                    bootstrap4={true}
                    keyField='email'
                    data={this.state.user}
                    columns={this.state.columns}/>

                {/*Open modal to create a new user*/}
                {/*TODO: Modal currently has two buttons (one to submit, one to close)*/}

                <Modal show={this.state.showCreateUser} onHide={() => this.handleCreateModal()} size="lg">
                    <Modal.Header>Nutzer anlegen</Modal.Header>
                    <Modal.Body>
                        <CreateUser/>
                    </Modal.Body>
                    <Modal.Footer>
                        <button className="btn btn-primary btn-sm" onClick={() => {
                            this.handleCreateModal()
                        }}>Close
                        </button>
                    </Modal.Footer>
                </Modal>


            </div>
        )
    }
}