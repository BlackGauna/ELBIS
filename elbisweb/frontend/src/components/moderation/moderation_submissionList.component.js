import React, {Component} from 'react';
import "bootstrap/dist/css/bootstrap.min.css";
import ArticleDataService from "../../services/article.service";
import {ARTICLESTATUS} from "../../session/articleStatus.ice";
import MessageIcon from '@material-ui/icons/Message';
import IconButton from "@material-ui/core/IconButton";
import VisibilityIcon from '@material-ui/icons/Visibility';
import moment from "moment";
import EditIcon from "@material-ui/icons/Edit";
import DeleteIcon from "@material-ui/icons/Delete";
import {Container} from "react-bootstrap";
import BootstrapTable from "react-bootstrap-table-next";
import Modal from "react-bootstrap/Modal";
import HandleSubmission from "./moderation_handleSubmission.component";


//TODO create submission window to manage a submission with a comment

export default class moderation_submissionList extends Component {

    /********
     *
     * Constructor
     *
     ********/
    constructor(props) {
        super(props);
        this.state = {
            article: [],
            //MODALWORK: array with booleans for editUserModals
            submissionModal: [],
            //table columns
            columns: [
                {
                    dataField: 'title',
                    text: 'Titel',
                    sort: true,
                },
                {
                    dataField: 'status',
                    text: 'Status',
                    sort: true,
                },
                {
                    dataField: 'createdAt',
                    text: 'Erstelldatum',
                    sort: true,
                    formatter: this.dateFormatter,
                },
                {
                    dataField: 'updatedAt',
                    text: 'Bearbeitet',
                    sort: true,
                    formatter: this.dateFormatter,
                },
                {
                    dataField: 'expireDate',
                    text: 'Ablaufdatum',
                    sort: true,
                    formatter: this.dateFormatter,
                },
                {
                    dataField: 'topic',
                    text: 'Bereich',
                    sort: true,
                },
                {
                    dataField: 'author',
                    text: 'Autor',
                    sort: true,
                },
                {
                    dataField: 'publisher',
                    text: 'Veröffentlicher',
                    sort: true,
                },
                {
                    dataField: '_id',
                    text: 'Aktion',
                    sort: false,
                    formatter: this.buttonFormatter,
                },
            ],
        };
    }

    /********
     *
     * Modals
     *
     ********/
    //MODALWORK: a handler for the index of the modal to show
    handleSubmissionModal = (index) => {
        const submissionModal = this.state.submissionModal;
        submissionModal[index] = !this.state.submissionModal[index]
        this.setState({submissionModal: submissionModal});
    }
    refreshPage = (index)=>{
        this.handleSubmissionModal(index)
        //TODO if works for everyone (works fine for me(chrome))
        window.location.reload()
    }
    // let a mod write a comment here and decide about the release of the article
    handleSubmission = (id) => {
        console.log("implement me")
        //TODO update the status of an article with the given id to 'Autorisiert' oder 'Abgelehnt'
    }
    //show an article preview here
    showArticlePreview = (content) => {
        console.log("implement me")
        //TODO show the preview of an article here
    }

    /********
     *
     * Mounting
     *
     ********/
    componentDidMount() {
        ArticleDataService.findByState(ARTICLESTATUS.EINGEREICHT)
            .then(response => {
                this.setState({article: response.data})
            })
            .catch((error) => {
                console.log(error);
            })
    }

    /********
     *
     * Formatters
     *
     ********/
    dateFormatter = (cell) => {
        return moment(cell).format("DD.MM.YYYY HH:mm:ss")
    }

    // TODO: implement view Article (read only??) and create comment to author
    buttonFormatter = (cell, row, rowIndex, formatExtraData) => {
        //MODALWORK: give the array an boolean for every row
        this.state.submissionModal.push(false);
        return (
            <div>
                {/*View(Edit) and submit buttons in each row*/}
                <IconButton
                    aria-label="view"
                    href={"/login/edit/" + row._id}>
                    <VisibilityIcon/>
                </IconButton>

                <IconButton aria-label="message" href='#' onClick={() => {
                    this.handleSubmissionModal(rowIndex)}}>
                    <MessageIcon/>
                </IconButton>
            </div>
        )
    }

    /********
     *
     * Render
     *
     ********/
    render() {
        return (
            <div className="container">
                <h3>Neue Veröffentlichungen</h3>
                <Container style={{display: "flex"}}>
                </Container>

                {/*Print the table*/}
                <BootstrapTable
                    headerClasses="thead-light"
                    bordered={false}
                    bootstrap4={true}
                    //TODO KeyField needs to be uniqe - else error on updates!
                    keyField='createdAt'
                    data={this.state.article}
                    columns={this.state.columns}/>

                {/*Check the modals*/}
                {this.renderSubmissionModal()}
            </div>
        )
    }

    renderSubmissionModal=()=>{
        //MODALWORK: check each boolean of the table rows
        for (let index = 0; index < this.state.submissionModal.length; index++) {
            if (this.state.submissionModal[index]) {
                console.log("open SubmissionModal "+index)
                return (
                    <Modal
                        show={true}
                        onHide={() => this.refreshPage(index)} size="lg">
                        <Modal.Header><h3>Artikelveröffentlichung</h3></Modal.Header>
                        <Modal.Body>
                            {/*MODALWORK: set the prop of the EditUser component (Userarrayindex = TableRowIndex)*/}
                            <HandleSubmission article={this.state.article[index]}/>
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