package com.commerce.core.common.utils;

import com.commerce.core.common.config.security.type.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SessionUtils {

    public static UserDetails getMyUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return (CustomUserDetails) principal;
        }
        return null;
    }

    public static Long getMemberSeq() {
        UserDetails myUserInfo = SessionUtils.getMyUserInfo();
        if (myUserInfo == null) return null;
        return Long.parseLong(myUserInfo.getUsername());
    }
}
