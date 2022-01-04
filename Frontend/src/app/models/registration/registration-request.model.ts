export class RegistrationRequest {
    public id: number;
    public firstName: string;
    public lastName: string;
    public email: string;
    public password: string;
    public phoneNumber: string;
    public address: string;
    public city: string;
    public country: string;
    public registrationType: string;
    public registrationMessage: string;

    constructor() {
        this.id = -1;
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.password = "";
        this.phoneNumber = "";
        this.address = "";
        this.city = "";
        this.country = "";
        this.registrationType = "";
        this.registrationMessage = "";
    }

}