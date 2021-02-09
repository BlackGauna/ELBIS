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
import {Grid} from "@material-ui/core";
import AddCircleIcon from "@material-ui/icons/AddCircle";

function checkMod(againstRole) {
    if (sessionStorage.getItem("sessionRole") === ROLE.MODERATOR && (againstRole === ROLE.MODERATOR || againstRole === ROLE.ADMINISTRATOR)) {
        return true
    } else {
        return false
    }
}

export default class moderation_userList extends Component {
    /********
     *
     * Constructor
     *
     ********/
    constructor(props) {
        super(props);
        this.state = {
            showCreateUser: false,
            //MODALWORK: array with booleans for editUserModals
            showEditUser: [],
            user: [],
            columns: [
                {
                    dataField: 'email',
                    text: 'E-Mail',
                    sort: true,
                    headerStyle: () => {
                        return {width: '20%',
                        };
                    }
                },
                {
                    dataField: 'fName',
                    text: 'Name',
                    sort: true,
                    formatter: this.nameFormatter,
                },
                {
                    dataField: 'gender',
                    text: 'Geschlecht',
                    sort: true
                },
                {
                    dataField: 'dateOfBirth',
                    text: 'Geburtsdatum',
                    sort: true,
                    formatter: this.dateFormatter,
                },
                {
                    dataField: 'street',
                    text: 'Adresse',
                    sort: true,
                    formatter: this.addressFormatter,
                },
                {
                    dataField: 'role',
                    text: 'Rolle',
                    sort: true
                },
                {
                    dataField: '_id',
                    text: '',
                    sort: false,
                    formatter: this.buttonFormatter,
                    headerFormatter: this.headerButtonFormatter,
                    //formatExtraData: {}
                },
            ]
        }
    }

    /********
     *
     * Modals
     *
     ********/
    handleCreateModal = () => {
        this.setState({showCreateUser: !this.state.showCreateUser});
    }
    //MODALWORK: a handler for the index of the modal to show
    handleEditModal = (index) => {
        const showEditUser = this.state.showEditUser;
        showEditUser[index] = !this.state.showEditUser[index]
        this.setState({showEditUser: showEditUser});
    }
    refreshPage = (index) => {
        this.handleEditModal(index)
        window.location.reload()
    }

    /********
     *
     * Formatters
     *
     ********/
    addressFormatter = (cell, row, index) => {
        return cell + " " + row.hNumber + " " + row.plz + " " + row.city;
    }

    nameFormatter = (cell, row, index) => {
        return cell + " " + row.lName;
    }

    dateFormatter = (cell) => {
        //return moment(cell).format("DD.MM.YYYY")
        return cell
    }

    buttonFormatter = (cell, row, rowIndex, formatExtraData) => {
        //MODALWORK: give the array an boolean for every row
        this.state.showEditUser.push(false);
        return (
            <div>
                {/*Edit and delete buttons in each row*/}
                <Grid container justify="flex-end">
                    <IconButton
                        aria-label="edit"
                        onClick={() => this.handleEditModal(rowIndex)}
                        disabled={checkMod(row.role)}> <EditIcon/>
                    </IconButton>

                    <IconButton
                        aria-label="delete"
                        href='#'
                        disabled={checkMod(row.role)}
                        onClick={() => {this.deleteUser(row._id)}}>
                        <DeleteIcon/>
                    </IconButton>
                </Grid>
            </div>
        )
    }

    headerButtonFormatter = () => {
        //TODO button crushes the header size
        return (
            <div>
                <Grid container justify="flex-end">
                    {/*+ Button to open the create modal*/}
                    <IconButton aria-label="add" href='#' onClick={() => {
                        this.handleCreateModal()
                    }}>
                        <AddCircleIcon/>
                    </IconButton>
                </Grid>
            </div>
        )
    }

    /********
     *
     * Mounting
     *
     ********/
    componentDidMount() {
        UserDataService.getAll()
            .then(response => {
                this.setState({user: response.data})
            })
            .catch((error) => {
                console.log(error);
            });
    }

    /********
     *
     * other
     *
     ********/
        // delete User method
    deleteUser = (id) => {
        if (window.confirm('Möchten Sie den ausgewählten Nutzer löschen?')) {
            UserDataService.delete(id)
                .then(res => console.log(res.data));
            this.setState({
                user: this.state.user.filter(el => el._id !== id)
            })
        } else {
            //nothing
        }
    }

    /********
     *
     * Render
     *
     ********/
    render() {
        return (
            <div className="container-fluid">
                <h3>Nutzerverwaltung</h3>
                Level: {sessionStorage.getItem("sessionRole")}
                {/*Print the table*/}
                <BootstrapTable
                    headerClasses="thead-light"
                    bordered={false}
                    bootstrap4={true}
                    keyField='email'
                    data={this.state.user}
                    columns={this.state.columns}/>

                {/*Check the modals*/}
                {this.renderCreateModal()}
                {this.renderEditModal()}
            </div>
        )
    }

    renderCreateModal = () => {
        //MODALWORK: check boolean for the CreateUser Modal
        if (this.state.showCreateUser) {
            console.log("open CreateModal")
            return (
                <Modal
                    show={true}
                    onHide={() => this.handleCreateModal()} size="lg">
                    <Modal.Header><h3>Nutzer anlegen</h3></Modal.Header>
                    <Modal.Body>
                        <CreateUser/>
                    </Modal.Body>
                    <Modal.Footer>
                        <button className="btn btn-primary btn-sm" onClick={() => {
                            this.handleCreateModal()
                        }}>Zurück
                        </button>
                    </Modal.Footer>
                </Modal>
            )
        }
    }
    renderEditModal = () => {
        //MODALWORK: check each boolean of the table rows
        for (let index = 0; index < this.state.showEditUser.length; index++) {
            if (this.state.showEditUser[index]) {
                console.log("open EditModal")
                return (
                    <Modal
                        show={true}
                        onHide={() => this.refreshPage(index)} size="lg">
                        <Modal.Header><h3>Nutzer bearbeiten</h3></Modal.Header>
                        <Modal.Body>
                            {/*MODALWORK: set the prop of the EditUser component (Userarrayindex = TableRowIndex)*/}
                            <EditUser user={this.state.user[index]}/>
                        </Modal.Body>
                        <Modal.Footer>
                            <button className="btn btn-primary btn-sm" onClick={() => {
                                this.refreshPage(index);
                            }}>Zurück
                            </button>
                        </Modal.Footer>
                    </Modal>)
            }
        }
    }
}