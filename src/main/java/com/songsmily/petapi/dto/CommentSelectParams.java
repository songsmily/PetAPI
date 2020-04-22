package com.songsmily.petapi.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentSelectParams {
    private Integer currentPage;
    private Integer pageSize;
    private Integer commentLevel;
}
