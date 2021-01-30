import React, {Component} from 'react';
import {Link,} from 'react-router-dom';
import "bootstrap/dist/css/bootstrap.min.css";
import EditIcon from '@material-ui/icons/Edit';
import DeleteIcon from '@material-ui/icons/Delete';
import IconButton from '@material-ui/core/IconButton';
import ForwardIcon from '@material-ui/icons/Forward';
import ArticleDataService from "../../services/article.service";


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
            <IconButton aria-label="delete" href='#' onClick={() => {
                props.deleteArticle(props.article._id)
            }}>
                <DeleteIcon/>
            </IconButton>
            <IconButton aria-label="forward" href='#' onClick={() => {
                props.updateArticleStatus(props.article._id)
            }}>
                <ForwardIcon/>
            </IconButton>
        </td>
    </tr>
)

export default class user_myArticles extends Component {
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
       //TODO update the status of an article with the given id to 'Submitted'
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
                            <button className="btn btn-primary btn-sm" onClick="reload">+</button>
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
    }
}