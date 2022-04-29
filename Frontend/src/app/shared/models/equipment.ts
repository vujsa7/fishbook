export class Equipment {
    public id: number;
    public type: string;
    public name: string;

    public constructor(id: number, type: string, name: string){
        this.id = id;
        this.type = type;
        this.name = name;
    }
}