import React, {Component} from 'react';
import "bootstrap/dist/css/bootstrap.min.css";
import EditIcon from '@material-ui/icons/Edit';
import DeleteIcon from '@material-ui/icons/Delete';
import IconButton from '@material-ui/core/IconButton';
import ForwardIcon from '@material-ui/icons/Forward';
import ArticleDataService from "../../services/article.service";
import {ARTICLESTATUS} from "../../session/articleStatus.ice";
import moment from "moment";
import {Container} from "react-bootstrap";
import BootstrapTable from "react-bootstrap-table-next";
import $ from "jquery";
import VisibilityIcon from "@material-ui/icons/Visibility";
import {Grid} from "@material-ui/core";
import AddCircleIcon from "@material-ui/icons/AddCircle";
import Modal from "react-bootstrap/Modal";


export default class user_myArticles extends Component {
    /********
     *
     * Constructor
     *
     ********/
    constructor(props) {
        super(props);
        this.state = {
            article: [],
            showComment: [],
            //table columns
            columns: [
                {
                    dataField: 'title',
                    text: 'Titel',
                    sort: true,
                    style:{
                        verticalAlign: 'middle'
                    },
                },
                {
                    dataField: 'status',
                    text: 'Status',
                    sort: true,
                    style:{
                        verticalAlign: 'middle'
                    },
                    headerStyle: () => {
                        return {width: '8%',
                        };
                    }
                },
                {
                    dataField: 'topic',
                    text: 'Bereich',
                    sort: true,
                    style:{
                        verticalAlign: 'middle'
                    }
                },
                {
                    dataField: 'updatedAt',
                    text: 'Bearbeitet',
                    sort: true,
                    formatter: this.dateFormatter,
                    style:{
                        verticalAlign: 'middle'
                    }
                },
                {
                    dataField: 'publishDate',
                    text: 'Veröffentlichung',
                    sort: true,
                    formatter: this.dateFormatter,
                    style:{
                        verticalAlign: 'middle'
                    }
                },
                {
                    dataField: 'expireDate',
                    text: 'Ablauf',
                    sort: true,
                    formatter: this.dateFormatter,
                    style:{
                        verticalAlign: 'middle'
                    }
                },
                {
                    dataField: 'publisher',
                    text: 'Veröffentlicher',
                    sort: true,
                    style:{
                        verticalAlign: 'middle'
                    }
                },
                {
                    dataField: 'publisherComment',
                    text: 'Kommentar',
                    sort: true,
                    formatter: this.commentFormatter,
                    style:{
                        verticalAlign: 'middle'
                    }
                },
                {
                    dataField: '_id',
                    text: 'Aktion',
                    sort: false,
                    formatter: this.buttonFormatter,
                    headerFormatter: this.headerButtonFormatter,
                    style:{
                        verticalAlign: 'middle'
                    },
                    headerStyle: () => {
                        return {width: '10%',
                        };
                    },
                },
            ],
        };
        //article Preview
        this.containerEl = document.createElement('div');
        this.externalWindow = null;
    }

    statusRowStyle = (row, rowIndex) => {
        const style = {};
        if (row.status === ARTICLESTATUS.AUTORISIERT || row.status === ARTICLESTATUS.ÖFFENTLICH) {
            style.backgroundColor = '#A3E4D7';
        } else if (row.status === ARTICLESTATUS.EINGEREICHT) {
            style.backgroundColor = '#AED6F1';
        } else if (row.status === ARTICLESTATUS.ABGELEHNT) {
            style.backgroundColor = '#E6B0AA';
        } else {

        }
        return style
    }

    /********
     *
     * Modals
     *
     ********/
    refreshPage = () => {
        window.location.reload()
    }

    deleteArticle = (id) => {
        if (window.confirm('Möchten Sie den ausgewählten Artikel löschen?')) {
            ArticleDataService.delete(id)
                .then(res => console.log(res.data));
            this.setState({
                article: this.state.article.filter(el => el._id !== id)
            })
        } else {
        }
    }
    handleCommentModal = (index) => {
        const showComment = this.state.showComment;
        showComment[index] = !this.state.showComment[index]
        this.setState({showComment: showComment});
    }
    showArticlePreview = (row) => {
        const id= row._id
        const url = '/preview/'+id
        const width =  $(document).width()*0.6
        this.externalWindow = window.open( url , '', 'width='+width+',height=700,left=200,top=200');
        this.externalWindow.document.body.appendChild(this.containerEl);
    }
    updateArticleStatus = (row) => {
        row.status = ARTICLESTATUS.EINGEREICHT;
        if (window.confirm('Möchten Sie den ausgewählten Artikel zum veröffentlichen einreichen?')) {
            ArticleDataService.update(
                row._id, row)
                .then(response => {
                    console.log(response.data);
                    this.setState({
                        message: "The article was submitted successfully!"
                    });
                })
                .catch(e => {
                    console.log(e);
                });
        } else {
        }
        this.refreshPage()
    }


    /********
     *
     * Mounting
     *
     ********/
    componentDidMount() {
        ArticleDataService.findByEmail(sessionStorage.getItem("sessionEmail"))
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

    commentFormatter = (cell, row, rowIndex, formatExtraData) => {
        this.state.showComment.push(false);
        if (cell.length >= 20) {
            return <div><a href='#' onClick={() => this.handleCommentModal(rowIndex)}>{cell.substring(0, 20) + ("(...)")}</a>
            </div>
        } else {return <div><a href='#' onClick={() => this.handleCommentModal(rowIndex)}>{cell}</a></div>}

    }

    buttonFormatter = (cell, row, rowIndex, formatExtraData) => {
        const submitDisable = (row.status !== ARTICLESTATUS.ENTWURF)
        return (
            <div>
                {/*Edit and Delete buttons*/}
                <IconButton
                    size='small'
                    aria-label="edit"
                    href={"/login/edit/" + row._id}>
                    <EditIcon/>
                </IconButton>

                <IconButton
                    size='small'
                    aria-label="view"
                    onClick={()=>{this.showArticlePreview(row)}}
                >
                    <VisibilityIcon/>
                </IconButton>

                <IconButton
                    aria-label="delete"
                    size='small'
                    href='#' onClick={() => {
                    this.deleteArticle(row._id)
                }}>
                    <DeleteIcon/>
                </IconButton>

                <IconButton disabled={submitDisable} aria-label="forward" onClick={() => {
                    this.updateArticleStatus(row)
                }}>
                    <ForwardIcon/>
                </IconButton>

            </div>
        )
    }
    headerButtonFormatter = () => {
        return (
            <div>
                <Grid container justify="flex-end">
                    {/*+ Button to open the create modal*/}
                    <IconButton size='small' aria-label="add" href="/login/edit">
                        <AddCircleIcon/>
                    </IconButton>
                </Grid>
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
                <h3>Meine Artikel</h3>
                <Container style={{display: "flex"}}>
                </Container>

                {/*Print the table*/}
                <BootstrapTable
                    headerClasses="thead-light"
                    bordered={false}
                    bootstrap4={true}
                    //KeyField needs to be uniqe - else error on updates!
                    keyField='createdAt'
                    data={this.state.article}
                    columns={this.state.columns}
                    rowStyle={this.statusRowStyle}
                />
                {this.renderCommentModal()}
            </div>
        )
    }
    renderCommentModal = () => {
        for (let index = 0; index < this.state.showComment.length; index++) {
            if (this.state.showComment[index]) {
                console.log("open CommentModal")
                return (
                    <Modal
                        show={true}
                        onHide={()=>this.handleCommentModal(index)} size="lg">
                        <Modal.Header><h4>Kommentar von {this.state.article[index].publisher}</h4></Modal.Header>
                        <Modal.Body>
                            {this.state.article[index].publisherComment}
                        </Modal.Body>
                        <Modal.Footer>
                            <button className="btn btn-primary btn-sm" onClick={() => {
                                this.handleCommentModal(index);
                            }}>Zurück
                            </button>
                        </Modal.Footer>
                    </Modal>)
            }
        }
    }
}