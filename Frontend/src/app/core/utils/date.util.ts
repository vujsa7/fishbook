import { DateRange } from "src/app/shared/models/date-range.model";


export default class DateUtil {
    static getDifferenceInDays(date1: any, date2: any): number {
        const diffInMs = Math.abs(date2 - date1);
        return diffInMs / (1000 * 60 * 60 * 24) + 1;
    }

    static isOverlapping(dateRange1: DateRange, dateRange2: DateRange): boolean{
        let start1 = new Date(dateRange1.start)
        let end1 = new Date(dateRange1.end)
        let start2 = new Date(dateRange2.start)
        let end2 = new Date(dateRange2.end)
        if((start1 < start2 && end1 < start2) || (start2 < start1 && end2 < start1))
            return false
        return true
    }
}