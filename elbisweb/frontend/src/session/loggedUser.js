import { extendObservable } from 'mobx';

class loggedUser {
    constructor(){
        extendObservable(this, {
            loading: true,
            isLoggedIn: false
        })
    }
}

export default new loggedUser();