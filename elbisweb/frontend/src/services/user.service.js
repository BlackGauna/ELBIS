import http from "../http-common";

// interface to axios

class UserDataService {

    // get all users
    getAll() {
        return http.get("/user/");
    }

    // get user by id
    get(id) {
        return http.get(`/user/${id}`);
    }

    // create user
    create(data) {
        return http.post("/user/", data);
    }

    //authenticate a User
    authenticate(email,password){
        return http.get(`/user/authenticate/${email}/${password}`);
    }

    // update user
    update(id, data) {
        return http.put(`/user/${id}`, data);
    }

    // delete user
    delete(id) {
        return http.delete(`/user/${id}`);
    }
}

export default new UserDataService();