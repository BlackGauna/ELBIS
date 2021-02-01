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

export default class administration_topicList extends Component {
    // Constructor
    constructor(props) {
        super(props);

        const columns = [
            {
                dataField: 'name',
                text: 'Name',
                sort: 'true',
            },
            {
                dataField: 'parentTopic',
                text: 'Elternbereich',
                sort: 'true',
            },
            {
                dataField: '_id',
                text: 'Aktion',
                formatter: this.buttonFormatter
            }

        ];

        this.state = {
            topic: [],
            showCreateTopic: false,
            showEditTopic: false,
            columns: columns
        };
    }

    handleCreateModal(){
        this.setState({showCreateTopic:!this.state.showCreateTopic});
    }

    handleEditModal(){
        this.setState({showEditTopic: !this.state.showEditTopic});
    }

    // Mount method
    componentDidMount() {
        TopicDataService.getAll()
            .then(response => {
                this.setState({topic: response.data})
            })
            .catch((error) => {
                console.log(error);
            })
    }

    // Delete topic
    deleteTopic(id) {
        if(window.confirm('Möchten Sie den ausgewählten Bereich löschen?')){
            TopicDataService.delete(id)
                .then(res => console.log(res.data));
            this.setState({
                topic: this.state.topic.filter(el => el._id !== id)
            })
        } else {

        }
    }

    buttonFormatter = (cell, row, rowIndex, formatExtraData) => {
        return (
            <div>

                {/*Edit and delete buttons in each row*/}

                <div>

                    <Grid container justify="flex-end">
                        <IconButton
                            aria-label="edit"
                            onClick={() => this.handleEditModal(row._id)}>
                            <EditIcon/>
                        </IconButton>

                        <IconButton aria-label="delete" href='#' onClick={() => {
                            this.deleteTopic(row._id)}}>
                            <DeleteIcon/>
                        </IconButton>
                    </Grid>

                </div>


                {/*Open modal to edit a topic*/}
                {/*TODO: implement edit modal)*/}

                {/*Old code:*/}
                {/*<IconButton aria-label="edit" href={"/login/admin/editTopic/" + props.topic._id}>*/}
                {/*    <EditIcon/>*/}
                {/*</IconButton>*/}

                <Modal show={this.state.showEditTopic} onHide={() => this.handleEditModal() + row._id} size="lg">
                    <Modal.Header>Bereich bearbeiten</Modal.Header>
                    <Modal.Body>
                        <EditTopic/>
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

    // ############ Render ################
    render() {
        return (
            <div className="container">
                <h3>Bereichsverwaltung</h3>

                <Grid container justify="flex-end">
                    <IconButton aria-label="add" href='#' onClick={() => {
                        this.handleCreateModal()
                    }}>
                        <AddCircleIcon/>
                    </IconButton>
                </Grid>

                <BootstrapTable
                    headerClasses="thead-light"
                    bordered={false}
                    bootstrap4={true}
                    keyField='name'
                    data={this.state.topic}
                    columns={this.state.columns}/>


                <Modal show={this.state.showCreateTopic} onHide={()=>this.handleCreateModal()} size="lg">
                    <Modal.Body>
                        <AddTopic/>
                    </Modal.Body>
                    <Modal.Footer>
                        <button className="btn btn-primary btn-sm" onClick={
                            ()=>{this.handleCreateModal()
                            }}>Close
                        </button>
                    </Modal.Footer>
                </Modal>
            </div>
        )
    }
}