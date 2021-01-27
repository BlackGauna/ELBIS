import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import "bootstrap/dist/css/bootstrap.min.css";
import ArticleDataService from "../../services/article.service";

// TODO: creationDate, lastEdit (access via mongoDB?) and expireDate
// TODO: edit Article

const Article = props => (
    <tr>
        <td>{props.article.title}</td>
        <td>{props.article.status}</td>
        <td>{props.article.topic}</td>
        <td>{props.article.author}</td>
        <td>{props.article.publisher}</td>
        <td>{props.article.publisherComment}</td>
        <td align="right">
            <Link to={"edit/" + props.article._id}>bearbeiten</Link> | <a href='#' onClick={() => {
            props.deleteArticle(props.article._id)
        }}>löschen</a>
        </td>
    </tr>
)

export default class moderation_articleList extends Component {
    // Constructor
    constructor(props) {
        super(props);

        this.deleteArticle = this.deleteArticle.bind(this);
        this.state = {article: []};
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
    deleteArticle(id) {
        ArticleDataService.delete(id)
            .then(res => console.log(res.data));
        this.setState({
            article: this.state.article.filter(el => el._id !== id)
        })
    }

    // get article list
    articleList() {
        return this.state.article.map(currentArticle => {
            return <Article article={currentArticle} deleteArticle={this.deleteArticle} key={currentArticle._id}/>;
        })
    }

//##########Render##########
    render() {
        return (
            <div className="container">
                <h3>Artikelverwaltung</h3>
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
                        </Link></th>
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