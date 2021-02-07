import http from "../http-common";

// interface to axios

class UserTopicDataService {

    // get all userTopics
    getAll() {
        return http.get("/userTopic/");
    }

    //get all by email
    getAllByMail(email) {
        return http.get(`/usertopic/${email}`);
    }

    // create userTopic
    create(data) {
        return http.post("/userTopic/", data);
    }

    // delete userTopic
    delete(email, topic) {
        return http.delete(`/usertopic/deleteOne/${email}/${topic}`);
    }

    deleteAllByMail(email) {
        return http.delete(`/usertopic/deleteByMail/${email}`);
    }
}

export default new UserTopicDataService();