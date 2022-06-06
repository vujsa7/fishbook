package com.fishbook.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteResponseDto {

    private Long requestId;
    private String response;
    private Boolean approved;
}
