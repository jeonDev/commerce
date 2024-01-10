package com.commerce.core.member.service;

import com.commerce.core.member.vo.LoginDto;
import com.commerce.core.member.vo.LoginSuccessDto;

public interface LoginService {

    /**
     * Login
     * @param dto
     * @return
     */
    LoginSuccessDto login(LoginDto dto);
}
