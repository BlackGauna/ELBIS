import React, {Component} from 'react';
import {BrowserRouter as Router, Link, Route} from 'react-router-dom';
import "bootstrap/dist/css/bootstrap.min.css";
import ArticleDataService from "../../services/article.service";

//TODO im still a copy of mod_article list -> just show articles of the logged user here
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

export default class user_myArticles extends Component {
    // Constructor
    constructor(props) {
        super(props);

        this.deleteArticle = this.deleteArticle.bind(this);
        this.state = {article: []};
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
                <div className='ElbisTable'>
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
                            </Link></th>
                        </tr>
                        </thead>
                        <tbody>
                        {this.articleList()}
                        </tbody>
                    </table>
                </div>
            </div>
        )
    }
}