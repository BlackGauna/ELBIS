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


//TODO create submission window to manage a submission with a comment

export default class moderation_submissionList extends Component {
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
                dataField: '_id',
                text: 'Aktion',
                sort: false,
                formatter: this.buttonFormatter,
            },
        ];

        this.state = {
            article: [],
            columns: columns
        };
    }

    // Mount method
    componentDidMount() {
        ArticleDataService.findByState(ARTICLESTATUS.EINGEREICHT)
            .then(response => {
                this.setState({article: response.data})
            })
            .catch((error) => {
                console.log(error);
            })
    }

    dateFormatter = (cell) => {
        return moment(cell).format("DD.MM.YYYY HH:mm:ss")
    }

    // TODO: implement view Article (read only??) and create comment to author
    buttonFormatter = (cell, row, rowIndex, formatExtraData) => {
        return (
            <div>
                <IconButton
                    aria-label="view"
                    href={"/login/edit/" + row._id}>
                    <VisibilityIcon/>
                </IconButton>

                <IconButton aria-label="message" href='#' onClick={() => {
                    this.deleteArticle(row._id)}}>
                    <MessageIcon/>
                </IconButton>
            </div>
        )
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


//##########Render##########
    render() {
        return (
            <div className="container">
                <h3>Artikelverwaltung</h3>
                <Container style={{display: "flex"}}>
                    Level: {sessionStorage.getItem("sessionRole")}
                </Container>

                <BootstrapTable
                    headerClasses="thead-light"
                    bordered={false}
                    bootstrap4={true}
                    keyField='title'
                    data={this.state.article}
                    columns={this.state.columns}/>
            </div>
        )
    }
}