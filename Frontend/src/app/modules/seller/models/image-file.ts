export class ImageFile{
    public content: File;
    public priority: number;

    public constructor(content: File, priority: number){
        this.content = content;
        this.priority = priority;
    }
}