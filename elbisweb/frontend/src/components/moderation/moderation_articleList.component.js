import React, {Component} from 'react';
import "bootstrap/dist/css/bootstrap.min.css";
import ArticleDataService from "../../services/article.service";
import IconButton from "@material-ui/core/IconButton";
import EditIcon from "@material-ui/icons/Edit";
import DeleteIcon from "@material-ui/icons/Delete";
import BootstrapTable from 'react-bootstrap-table-next';
import 'react-bootstrap-table-next/dist/react-bootstrap-table2.min.css';
import moment from "moment";
import $ from "jquery";
import {ARTICLESTATUS} from "../../session/articleStatus.ice";
import VisibilityIcon from "@material-ui/icons/Visibility";
import Modal from "react-bootstrap/Modal";
import EditUser from "./moderation_editUser.component";
import BackspaceIcon from "@material-ui/icons/Backspace";


export default class moderation_articleList extends Component {
    /********
     *
     * Constructor
     *
     ********/
    constructor(props) {
        super(props);

        const columns = [
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
                dataField: 'publisherComment',
                text: 'Kommentar',
                sort: true,
                formatter: this.commentFormatter,
                style: {
                    verticalAlign: 'middle'
                }
            },
            {
                dataField: '_id',
                text: '',
                sort: false,
                formatter: this.buttonFormatter,
                style: {
                    verticalAlign: 'middle'
                },
                headerStyle: () => {
                    return {
                        width: '11%',
                    };
                },
            },
        ];

        this.state = {
            article: [],
            showComment: [],
            columns: columns
        }
        //article Preview
        this.containerEl = document.createElement('div');
        this.externalWindow = null;
    }

    /********
     *
     * Modals
     *
     ********/
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
        console.log(index)
        const showComment = this.state.showComment;
        showComment[index] = !this.state.showComment[index]
        this.setState({showComment: showComment});
    }

    showArticlePreview = (row) => {
        const id = row._id
        const url = '/preview/' + id
        const width = $(document).width() * 0.6
        this.externalWindow = window.open(url, '', 'width=' + width + ',height=700,left=200,top=200');
        this.externalWindow.document.body.appendChild(this.containerEl);
    }

    /********
     *
     * Mounting
     *
     ********/
    componentDidMount() {
        ArticleDataService.getAll()
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
            return <div><a href='#' onClick={() => this.handleCommentModal(rowIndex)}>{cell.substring(0, 20) + ("(...)")}</a></div>
        } else return <div><a href='#' onClick={() => this.handleCommentModal(rowIndex)}>{cell}</a></div>

    }

    buttonFormatter = (cell, row, rowIndex, formatExtraData) => {
        const submitDisable = (row.status !== ARTICLESTATUS.ENTWURF)
        const editDisable = (row.status === ARTICLESTATUS.ÖFFENTLICH)
        return (
            <div>
                <IconButton
                    disabled={editDisable}
                    size='small'
                    aria-label="edit"
                    href={"/login/edit/" + row._id}>
                    <EditIcon/>
                </IconButton>

                <IconButton
                    aria-label="view"
                    onClick={() => {
                        this.showArticlePreview(row)
                    }}
                >
                    <VisibilityIcon/>
                </IconButton>

                <IconButton size='small' aria-label="delete" href='#' onClick={() => {
                    this.deleteArticle(row._id)
                }}>
                    <DeleteIcon/>
                </IconButton>

                    <IconButton
                        disabled={!editDisable}
                        aria-label="Rückruf"
                        onClick={() => {
                        this.updateArticleStatus_bringBack(row)
                    }}>
                        <BackspaceIcon/>
                    </IconButton>
            </div>
        )
    }
    updateArticleStatus_bringBack = (row) => {
        row.status = ARTICLESTATUS.ENTWURF;
        if (window.confirm('Möchten Sie den ausgewählten Artikel wirklich zurück rufen?\nACHTUNG, er muss anschließend erneut eingereicht werden!')) {
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
    refreshPage = () => {
        window.location.reload()
    }

    /********
     *
     * Render
     *
     ********/
    render() {
        return (
            <div className="articleTable">
                <h3>Artikelverwaltung</h3>
                <BootstrapTable
                    headerClasses="thead-light"
                    bordered={false}
                    bootstrap4={true}
                    //KeyField needs to be uniqe - else error on updates!
                    keyField='createdAt'
                    data={this.state.article}
                    columns={this.state.columns}/>
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