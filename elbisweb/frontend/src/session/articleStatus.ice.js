export const ARTICLESTATUS = Object.freeze({
    "ENTWURF": "Entwurf",
    "EINGEREICHT": "Eingereicht",
    "ABGELEHNT": "Abgelehnt",
    "AUTORISIERT": "Autorisiert",
    "ÖFFENTLICH": "Öffentlich",
    "ARCHIVIERT": "Archiviert",

    getAll() {
        return [{name: this.ENTWURF},
            {name: this.EINGEREICHT},
            {name: this.ABGELEHNT},
            {name: this.AUTORISIERT},
            {name: this.ÖFFENTLICH},
            {name: this.ARCHIVIERT}]
    },
    getUserOptions(){
        return [{name: this.ENTWURF},
            {name: this.EINGEREICHT}]
    },
    getModeratorOptions(){
        return [{name: this.ENTWURF},
            {name: this.EINGEREICHT},
            {name: this.ABGELEHNT},
            {name: this.AUTORISIERT},]
    },
    getSubmissionOptions(){
        return [{name: this.ABGELEHNT},
            {name: this.AUTORISIERT}]
    }

})




