import { extendObservable } from 'mobx';

class loggedUser {
    constructor(){
        extendObservable(this, {
            loading: true,
            isLoggedIn: false,
            eMail: '',
            role: ''
        })
    }
}

export default new loggedUser();