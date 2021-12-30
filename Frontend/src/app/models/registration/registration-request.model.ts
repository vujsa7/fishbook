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

    constructor(id: number, firstName: string, lastName: string, email: string, password: string, phoneNumber: string, address: string, city: string, country: string, registrationType: string, registrationMessage: string) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.country = country;
        this.registrationType = registrationType;
        this.registrationMessage = registrationMessage;
    }
}