package com.songsmily.petapi.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SelectPageParams {
    Integer currentPage;
    Integer pageSize;
    String areaFilter;
    Integer accountType;
    Integer logAdminId;
}
