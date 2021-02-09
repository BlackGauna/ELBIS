import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import "bootstrap/dist/css/bootstrap.min.css";
import ArticleDataService from "../../services/article.service";
import IconButton from "@material-ui/core/IconButton";
import EditIcon from "@material-ui/icons/Edit";
import DeleteIcon from "@material-ui/icons/Delete";
import BootstrapTable from 'react-bootstrap-table-next';
import 'react-bootstrap-table-next/dist/react-bootstrap-table2.min.css';
import {Container} from "react-bootstrap";
import moment from "moment";


export default class moderation_articleList extends Component {
    // Constructor
    constructor(props) {
        super(props);

        const columns = [
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
                dataField: 'publisherComment',
                text: 'Kommentar',
                sort: true,
                formatter: this.commentFormatter,
            },
            {
                dataField: '_id',
                text: 'Aktion',
                sort: false,
                formatter: this.buttonFormatter,
            },
        ];

        this.state = {
            article: [],
            columns: columns
        }
    }

    // Mount method
    componentDidMount() {
        ArticleDataService.getAll()
            .then(response => {
                this.setState({article: response.data})
            })
            .catch((error) => {
                console.log(error);
            })
    }

    // delete Article method
    deleteArticle = (id) => {
        if(window.confirm('Möchten Sie den ausgewählten Artikel löschen?')){
            ArticleDataService.delete(id)
                .then(res => console.log(res.data));
            this.setState({
                article: this.state.article.filter(el => el._id !== id)
            })
        } else {}
    }

    dateFormatter = (cell) => {
        return moment(cell).format("DD.MM.YYYY HH:mm:ss")
    }

    commentFormatter = (cell) => {
        if(cell.length >= 20){
            return <div><a href='#' onClick={()=>this.showComment(cell)}>{cell.substring(0, 20)+("(...)")}</a></div>
        } else return <div><a href='#' onClick={()=>this.showComment(cell)}>{cell}</a></div>

    }

    showComment = (cell) => {
        //TODO maybe toggle a modal here but thats good for now
        window.alert(cell)
    }

    buttonFormatter = (cell, row, rowIndex, formatExtraData) => {
        return (
            <div>
                {/*TODO mybe make this just a preview*/}
                <IconButton
                    aria-label="edit"
                    href={"/login/edit/" + row._id}>
                    <EditIcon/>
                </IconButton>

                <IconButton aria-label="delete" href='#' onClick={() => {
                    this.deleteArticle(row._id)}}>
                    <DeleteIcon/>
                </IconButton>
            </div>

        )
    }


//##########Render##########
    render() {
        return (
            <div className="container-fluid">
                <h3>Artikelverwaltung</h3>
                <BootstrapTable
                    headerClasses="thead-light"
                    bordered={false}
                    bootstrap4={true}
                    //KeyField needs to be uniqe - else error on updates!
                    keyField='createdAt'
                    data={this.state.article}
                    columns={this.state.columns}/>
            </div>
        )
    }
}