export class StatsInfo {

    currentPoints: number;
    pointsForNextLevel: number;
    currentPenalties: number;
    penaltiesForBan: number;

    constructor(
        currentPoints: number,
        pointsForNextLevel: number,
        currentPenalties: number,
        penaltiesForBan: number
    ) {
        this.currentPoints = currentPoints
        this.pointsForNextLevel = pointsForNextLevel
        this.currentPenalties = currentPenalties
        this.penaltiesForBan = penaltiesForBan
    }

}