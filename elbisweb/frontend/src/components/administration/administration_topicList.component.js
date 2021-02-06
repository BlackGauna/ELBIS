import React, {Component} from 'react';
import "bootstrap/dist/css/bootstrap.min.css";
import TopicDataService from "../../services/topic.service";
import EditIcon from '@material-ui/icons/Edit';
import DeleteIcon from '@material-ui/icons/Delete';
import IconButton from '@material-ui/core/IconButton';
import AddCircleIcon from '@material-ui/icons/AddCircle';
import Modal from "react-bootstrap/Modal";
import AddTopic from '../administration/administration_createTopic.component';
import EditTopic from '../administration/administration_editTopic.component';
import BootstrapTable from 'react-bootstrap-table-next';
import {Grid} from "@material-ui/core";

//TODO close and refresh after creation of a topic
export default class administration_topicList extends Component {
    /********
     *
     * Constructor
     *
     ********/
    constructor(props) {
        super(props);
        this.state = {
            topic: [],
            showCreateTopic: false,
            showEditTopic: [],
            columns: [
                {
                    dataField: 'name',
                    text: 'Name',
                    sort: true,
                },
                {
                    dataField: 'parentTopic',
                    text: 'Elternbereich',
                    sort: true,
                },
                {
                    dataField: '_id',
                    text: '',
                    formatter: this.buttonFormatter,
                    headerFormatter: this.headerButtonFormatter,
                }

            ],
        };
    }

    /********
     *
     * Modals
     *
     ********/
    handleCreateModal() {
        this.setState({showCreateTopic: !this.state.showCreateTopic});
    }

    handleEditModal(index) {
        const showEditTopic = this.state.showEditTopic;
        showEditTopic[index] = !this.state.showEditTopic[index]
        this.setState({showEditUser: showEditTopic});
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
    buttonFormatter = (cell, row, rowIndex, formatExtraData) => {
        this.state.showEditTopic.push(false);
        return (
            <div>
                {/*Edit and delete buttons in each row*/}
                <div>
                    <Grid container justify="flex-end">
                        <IconButton
                            aria-label="edit"
                            onClick={() => this.handleEditModal(rowIndex)}>
                            <EditIcon/>
                        </IconButton>

                        <IconButton
                            aria-label="delete"
                            href='#'
                            onClick={() => {
                                this.deleteTopic(row._id)
                            }}>
                            <DeleteIcon/>
                        </IconButton>
                    </Grid>
                </div>
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
        TopicDataService.getAll()
            .then(response => {
                this.setState({topic: response.data})
            })
            .catch((error) => {
                console.log(error);
            })
    }

    /********
     *
     * other
     *
     ********/
    deleteTopic(id) {
        if (window.confirm('Möchten Sie den ausgewählten Bereich löschen?')) {
            TopicDataService.delete(id)
                .then(res => console.log(res.data));
            this.setState({
                topic: this.state.topic.filter(el => el._id !== id)
            })
        } else {

        }
    }

    /********
     *
     * Render
     *
     ********/
    render() {
        return (
            <div className="container">
                <h3>Bereichsverwaltung</h3>
                <BootstrapTable
                    headerClasses="thead-light"
                    bordered={false}
                    bootstrap4={true}
                    keyField='name'
                    data={this.state.topic}
                    columns={this.state.columns}/>

                {/*Check the modals*/}
                {this.renderCreateModal()}
                {this.renderEditModal()}
            </div>
        )
    }

    renderCreateModal = () => {
        if (this.state.showCreateTopic) {
            return (
                <Modal show={this.state.showCreateTopic} onHide={() => this.handleCreateModal()} size="lg">
                    <Modal.Body>
                        <AddTopic/>
                    </Modal.Body>
                    <Modal.Footer>
                        <button className="btn btn-primary btn-sm" onClick={
                            () => {
                                this.handleCreateModal()
                            }}>Zurück
                        </button>
                    </Modal.Footer>
                </Modal>
            )
        }
    }
    renderEditModal = () => {
        for (let index = 0; index < this.state.showEditTopic.length; index++) {
            if (this.state.showEditTopic[index]) {
                console.log("open EditTopicModal")
                return (<div>
                    <Modal
                        show={true}
                        onHide={() => this.refreshPage(index)} size="lg">
                        <Modal.Header><h3>Bereich bearbeiten</h3></Modal.Header>
                        <Modal.Body>
                            <EditTopic topic={this.state.topic[index]}/>
                        </Modal.Body>
                        <Modal.Footer>
                            <button className="btn btn-primary btn-sm" onClick={() => {
                                this.refreshPage(index)
                            }}>Zurück
                            </button>
                        </Modal.Footer>
                    </Modal>
                </div>)
            }
        }
    }
}