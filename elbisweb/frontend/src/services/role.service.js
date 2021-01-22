import http from "../http-common";

// interface to axios

class RoleDataService {

    // get all roles
    getAll() {
        return http.get("/role/");
    }

    // get role per id
    get(id) {
        return http.get(`/role/${id}`);
    }

    // create role
    create(data) {
        return http.post("/role/", data);
    }
}

export default new RoleDataService();