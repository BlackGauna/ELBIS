import http from "../http-common";

// interface to axios

class TopicDataService {

    // get all topics
    getAll() {
        return http.get("/topic/");
    }

    // get all parentTopics


    // get topic by id
    get(id) {
        return http.get(`/topic/${id}`);
    }

    // create topic
    create(data) {
        return http.post("/topic/", data);
    }

    // update topic
    update(id, data) {
        return http.put(`/topic/${id}`, data);
    }

    // delete topic
    delete(id) {
        return http.delete(`/topic/${id}`);
    }
}

export default new TopicDataService();