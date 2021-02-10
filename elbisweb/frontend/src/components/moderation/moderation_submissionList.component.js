import React, {Component} from 'react';
import "bootstrap/dist/css/bootstrap.min.css";
import ArticleDataService from "../../services/article.service";
import {ARTICLESTATUS} from "../../session/articleStatus.ice";
import MessageIcon from '@material-ui/icons/Message';
import IconButton from "@material-ui/core/IconButton";
import VisibilityIcon from '@material-ui/icons/Visibility';
import moment from "moment";
import {Container} from "react-bootstrap";
import BootstrapTable from "react-bootstrap-table-next";
import Modal from "react-bootstrap/Modal";
import HandleSubmission from "./moderation_handleSubmission.component";
import ReactDOM from "react-dom";
import $ from 'jquery'

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
                    style: {
                        verticalAlign: 'middle'
                    },
                },
                {
                    dataField: 'status',
                    text: 'Status',
                    sort: true,
                    style: {
                        verticalAlign: 'middle'
                    },
                    headerStyle: () => {
                        return {
                            width: '8%',
                        };
                    },
                },
                {
                    dataField: 'topic',
                    text: 'Bereich',
                    sort: true,
                    style: {
                        verticalAlign: 'middle'
                    },
                    headerStyle: () => {
                        return {
                            width: '8%',
                        };
                    },
                },
                {
                    dataField: 'updatedAt',
                    text: 'Bearbeitet',
                    sort: true,
                    formatter: this.dateFormatter,
                    style: {
                        verticalAlign: 'middle'
                    }
                },
                {
                    dataField: 'publishDate',
                    text: 'Veröffentlichung',
                    sort: true,
                    formatter: this.dateFormatter,
                    style: {
                        verticalAlign: 'middle'
                    }
                },
                {
                    dataField: 'expireDate',
                    text: 'Ablauf',
                    sort: true,
                    formatter: this.dateFormatter,
                    style: {
                        verticalAlign: 'middle'
                    }
                },
                {
                    dataField: 'author',
                    text: 'Autor',
                    sort: true,
                    style: {
                        verticalAlign: 'middle'
                    }
                },
                {
                    dataField: 'publisher',
                    text: 'Veröffentlicher',
                    sort: true,
                    style: {
                        verticalAlign: 'middle'
                    }
                },
                {
                    dataField: '_id',
                    text: 'Aktion',
                    sort: false,
                    formatter: this.buttonFormatter,
                    style: {
                        verticalAlign: 'middle'
                    }
                },
            ],
        };
        //article Preview
        this.containerEl = document.createElement('div');
        this.externalWindow = null;
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
        window.location.reload()
    }
    showArticlePreview = (row) => {
        const id= row._id
        const url = '/article/'+id
        const width =  $(document).width()*0.6
        this.externalWindow = window.open( url , '', 'width='+width+',height=700,left=200,top=200');
        this.externalWindow.document.body.appendChild(this.containerEl);
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

    buttonFormatter = (cell, row, rowIndex, formatExtraData) => {
        //MODALWORK: give the array an boolean for every row
        this.state.submissionModal.push(false);
        return (
            <div>
                {/*View(Edit) and submit buttons in each row
                <IconButton
                    aria-label="view"
                    href={"/login/edit/" + row._id}>
                    <VisibilityIcon/>
                </IconButton> */}

                <IconButton
                    aria-label="view"
                    onClick={()=>{this.showArticlePreview(row)}}
                >
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
            <div className="container-fluid">
                <h3>Neue Veröffentlichungen</h3>
                <Container style={{display: "flex"}}>
                </Container>

                {/*Print the table*/}
                <BootstrapTable
                    headerClasses="thead-light"
                    bordered={false}
                    bootstrap4={true}
                    keyField='createdAt'
                    data={this.state.article}
                    columns={this.state.columns}/>

                {/*Check the modals*/}
                {this.renderSubmissionModal()}
                {ReactDOM.createPortal(this.props.children, this.containerEl)}
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