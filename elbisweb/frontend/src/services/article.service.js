import http from "../http-common";

// interface to axios

class ArticleDataService {

    // get all articles
    getAll() {
        return http.get("/article/");
    }

    // get all articles by a specific email
    findByEmail(email) {
        return http.get(`/article/fetchByMail/${email}`);
    }
    // get all articles in a specific status
    findByState(status) {
        return http.get(`/article/fetchByStatus/${status}`);
    }

        // get article by id
    get(id) {
        return http.get(`/article/${id}`);
    }

    // create article
    create(data) {
        return http.post("/article/", data);
    }

    // delete article
    delete(id) {
        return http.delete(`/article/${id}`);
    }
}

export default new ArticleDataService();