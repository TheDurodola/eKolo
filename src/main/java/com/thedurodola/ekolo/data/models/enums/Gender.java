package com.thedurodola.ekolo.data.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
    MALE("M"),
    FEMALE("F"),
    NON_BINARY("NB"),
    OTHER("O"),
    PREFER_NOT_TO_SAY("P");

    private final String code;

}
