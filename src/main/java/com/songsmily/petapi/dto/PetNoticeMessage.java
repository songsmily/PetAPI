package com.songsmily.petapi.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PetNoticeMessage {
    private String phone;
    private String message;
    private String petName;
}
