package com.todo.simple.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public enum ProfileEnum {

    ADMIN(1, "ROLE_ADMIN"),
    USER(2, "ROLE_USER");

    private Integer code;
    private String description;

    public static ProfileEnum toEnum(Integer code) {

        if(Objects.isNull(code)) return null;

        for (ProfileEnum profileEnum : ProfileEnum.values()) {
            if (profileEnum.getCode().equals(code)) {
                return profileEnum;
            }
        }

        throw new IllegalArgumentException("Invalid code: " + code);
    }
}
