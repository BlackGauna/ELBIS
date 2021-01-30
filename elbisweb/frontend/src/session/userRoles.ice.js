export const ROLE = Object.freeze({
    "ADMINISTRATOR": "Administrator",
    "MODERATOR": "Moderator",
    "USER": "Nutzer",

    getAll() {
        return [{name: this.ADMINISTRATOR},
            {name: this.MODERATOR},
            {name: this.USER}]
    },
    getModeratorOptions() {
        return [{name: this.USER}]
    }

})

