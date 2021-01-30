import React, {Component} from 'react';
import "bootstrap/dist/css/bootstrap.min.css";
import ArticleDataService from "../../services/article.service";
import {ARTICLESTATUS} from "../../session/articleStatus.ice";
import MessageIcon from '@material-ui/icons/Message';
import IconButton from "@material-ui/core/IconButton";

//TODO im a copy yet -> make me show articles that are in 'submitted' state
//TODO create submission window to manage a submission with a comment


// TODO: creationDate, lastEdit (access via mongoDB?) and expireDate
// TODO: edit Article
//TODO managesubmission instead of 'delete' an article
const Article = props => (
    <tr>
        <td>{props.article.title}</td>
        <td>{props.article.status}</td>
        <td>{props.article.topic}</td>
        <td>{props.article.author}</td>
        <td>{props.article.publisher}</td>
        <td>{props.article.publisherComment}</td>
        <td align="right">
            <IconButton aria-label="delete" href='#' onClick={() => {
                props.handleSubmission(props.article._id)
            }}>
            <MessageIcon/>
            </IconButton>
        </td>
    </tr>
)

export default class moderation_submissionList extends Component {
    // Constructor
    constructor(props) {
        super(props);
        this.state = {article: []};
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

    // delete Article method
    handleSubmission = (id) => {
            console.log("implement me")
            //TODO update the status of an article with the given id to 'Autorisiert' oder 'Abgelehnt'
        }

    // get article list
    articleList() {
        return this.state.article.map(currentArticle => {
            return <Article
                article={currentArticle}
                handleSubmission={this.handleSubmission}
                key={currentArticle._id}/>;
        })
    }

//##########Render##########
    render() {
        return (
            <div className="container">
                <h3>Neue Veröffentlichungen</h3>
                <table className="articleTable table">
                    <thead className="thead-light">
                    <tr>
                        <th>Titel</th>
                        <th>Status</th>
                        <th>Bereich</th>
                        <th>Autor</th>
                        <th>Veröffentlicher</th>
                        <th>ursprünglicher Kommentar</th>
                        <th className={"text-right"}></th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.articleList()}
                    </tbody>
                </table>
            </div>
        )
    }
}