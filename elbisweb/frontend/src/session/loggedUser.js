import { extendObservable } from 'mobx';

class loggedUser {
    constructor(){
        extendObservable(this, {
            loading: true,
            isLoggedIn: false,
            email: 'DEFAULT',
            password: 'DEFAULT',
            role: 'DEFAULT'
        })
    }
}

export default new loggedUser();