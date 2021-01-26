import http from "../http-common";

// interface to axios

class SessionDataService {

    // create session
    create(data) {
        return http.post("/session/", data);
    }

    // delete session
    delete(email) {
        return http.delete(`/session/${email}`);
    }

    //check session
    check(token, email){
        return http.get(`/session/check/${token}/${email}`);
    }

    // get all sessions
    getAll() {
        return http.get("/session/");
    }

}

export default new SessionDataService();