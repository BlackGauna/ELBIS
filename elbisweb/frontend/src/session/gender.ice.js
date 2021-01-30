export const GENDER = Object.freeze({
    "MAN": "Männlich",
    "WOMAN": "Weiblich",
    "DIVERS": "Divers",

    getAll() {
        return [{name: this.MAN},
            {name: this.WOMAN},
            {name: this.DIVERS}]
    }

})