import http from "../http-common";

// interface to axios

class GenderDataService {

    // get all genders
    getAll() {
        return http.get("/gender/");
    }

    // get gender by id
    get(id) {
        return http.get(`/gender/${id}`);
    }

    // create gender
    create(data) {
        return http.post("/gender/", data);
    }
}

export default new GenderDataService();