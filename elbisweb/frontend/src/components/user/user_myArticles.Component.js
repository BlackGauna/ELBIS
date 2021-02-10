import React, {Component} from 'react';
import {Link,} from 'react-router-dom';
import "bootstrap/dist/css/bootstrap.min.css";
import EditIcon from '@material-ui/icons/Edit';
import DeleteIcon from '@material-ui/icons/Delete';
import IconButton from '@material-ui/core/IconButton';
import ForwardIcon from '@material-ui/icons/Forward';
import ArticleDataService from "../../services/article.service";
import {ARTICLESTATUS} from "../../session/articleStatus.ice";
import moment from "moment";
import MessageIcon from "@material-ui/icons/Message";
import {Container} from "react-bootstrap";
import BootstrapTable from "react-bootstrap-table-next";
import Alert from 'react-bootstrap/Alert'
import Modal from "react-bootstrap/Modal";
import HandleSubmission from "../moderation/moderation_handleSubmission.component";
import $ from "jquery";
import VisibilityIcon from "@material-ui/icons/Visibility";
import "./../tables.css";


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
                },
                {
                    dataField: 'topic',
                    text: 'Bereich',
                    sort: true,
                    style:{
                        verticalAlign: 'middle'
                    },
                },
                {
                    dataField: 'createdAt',
                    text: 'Erstellt',
                    sort: true,
                    formatter: this.dateFormatter,
                    style:{
                        verticalAlign: 'middle'
                    },
                },
                {
                    dataField: 'updatedAt',
                    text: 'Bearbeitet',
                    sort: true,
                    formatter: this.dateFormatter,
                    style:{
                        verticalAlign: 'middle'
                    },
                },
                {
                    dataField: 'publishDate',
                    text: 'Erscheint',
                    sort: true,
                    formatter: this.dateFormatter,
                    style:{
                        verticalAlign: 'middle'
                    },
                },
                {
                    dataField: 'expireDate',
                    text: 'Ablauf',
                    sort: true,
                    formatter: this.dateFormatter,
                    style:{
                        verticalAlign: 'middle'
                    },
                },
                {
                    dataField: 'publisher',
                    text: 'Veröffentlicher',
                    sort: true,
                    style:{
                        verticalAlign: 'middle'
                    },
                },
                {
                    dataField: 'publisherComment',
                    text: 'Kommentar',
                    sort: true,
                    formatter: this.commentFormatter,
                    style:{
                        verticalAlign: 'middle'
                    },
                },
                {
                    dataField: '_id',
                    text: '',
                    sort: false,
                    formatter: this.buttonFormatter,
                    style:{
                        verticalAlign: 'middle'
                    },
                    headerStyle: () => {
                        return {width: '12%',
                        };
                    }
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

    // delete Article method
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

    showComment = (cell) => {
        //TODO maybe toggle a modal here but thats good for now
        window.alert(cell)
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

    commentFormatter = (cell) => {
        if (cell.length >= 20) {
            if(this.state.commentAlert){

            }
            return <div><a href='#' onClick={() => this.showComment(cell)}>{cell.substring(0, 20) + ("(...)")}</a>
            </div>
        } else {return <div><a href='#' onClick={() => this.showComment(cell)}>{cell}</a></div>}

    }

    buttonFormatter = (cell, row, rowIndex, formatExtraData) => {
        const editDisable = (row.status !== ARTICLESTATUS.ENTWURF)

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

                <IconButton aria-label="delete" href='#' onClick={() => {
                    this.deleteArticle(row._id)
                }}>
                    <DeleteIcon/>
                </IconButton>

                <IconButton disabled={editDisable} aria-label="forward" onClick={() => {
                    this.updateArticleStatus(row)
                }}>
                    <ForwardIcon/>
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
            <div className="articleTable">
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
            </div>
        )
    }

    /* OLD
const Article = props => (
    <tr>
        <td>{props.article.title}</td>
        <td>{props.article.status}</td>
        <td>{props.article.topic}</td>
        <td>{props.article.author}</td>
        <td>{props.article.publisher}</td>
        <td>{props.article.publisherComment}</td>
        <td align='right'>
            <IconButton aria-label="edit" href={"/login/edit/" + props.article._id}>
                <EditIcon/>
            </IconButton>
            <IconButton aria-label="delete" onClick={() => {
                props.deleteArticle(props.article._id)
            }}>
                <DeleteIcon/>
            </IconButton>
            <IconButton aria-label="forward" onClick={() => {
                props.updateArticleStatus(props.article._id)
            }}>
                <ForwardIcon/>
            </IconButton>
        </td>
    </tr>
)


    // Constructor
    constructor(props) {
        super(props);

        this.deleteArticle = this.deleteArticle.bind(this);
        this.state = {
            article: []
        };
    }


    // Mount method
    componentDidMount() {
        ArticleDataService.findByEmail(sessionStorage.getItem("sessionEmail"))
            .then(response => {
                this.setState({article: response.data})
            })
            .catch((error) => {
                console.log(error);
            })
    }

    // delete Article method
    deleteArticle(id) {
        ArticleDataService.delete(id)
            .then(res => console.log(res.data));
        this.setState({
            article: this.state.article.filter(el => el._id !== id)
        })
    }

    // delete Article method
    updateArticleStatus = (id) => {
        console.log("implement me")

    }

    // get article list
    articleList() {
        return this.state.article.map(currentArticle => {
            return <Article article={currentArticle}
                            deleteArticle={this.deleteArticle}
                            updateArticleStatus={this.updateArticleStatus}
                            key={currentArticle._id}/>;
        })
    }

//##########Render##########
    render() {
        return (
            <div className="container">
                <h3>Meine Artikel</h3>
                <table className="articleTable table">
                    <thead className="thead-light">
                    <tr>
                        <th>Titel</th>
                        <th>Status</th>
                        <th>Bereich</th>
                        <th>Autor</th>
                        <th>Veröffentlicher</th>
                        <th>Kommentar</th>
                        <th className={"text-right"}><Link to="/login/edit">
                            <button className="btn btn-primary btn-sm">+</button>
                        </Link>
                        </th>
                    </tr>

                    </thead>
                    <tbody>
                    {this.articleList()}
                    </tbody>
                </table>
            </div>
        )
    } */
}