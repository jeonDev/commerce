package com.commerce.core.member.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    @NotNull
    private String id;
    @NotNull
    private String password;
}
