package com.fishbook.house.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseSpecificationsDto {
    private int roomCount;
    private String bedsByRooms;
    private int totalBeds;
}
