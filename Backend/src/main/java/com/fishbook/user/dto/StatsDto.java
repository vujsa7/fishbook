package com.fishbook.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StatsDto {
    private Integer currentPenalties;
    private Integer penaltiesForBan;
    private Integer currentPoints;
    private Integer pointsForNextLevel;
}
