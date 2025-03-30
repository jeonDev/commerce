package com.commerce.core.common.config.security;

import com.commerce.core.common.config.security.type.IdentificationGenerateRequest;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

public interface IdentifierProvider {

    /**
     * Object For Authentication Generate
     */
    Object generateIdentificationInfo(IdentificationGenerateRequest vo);

    /**
     * Authentication Info Get
     */
    Authentication getAuthenticationInfo(Object identificationInfo);

    /**
     * IdentificationInfo Valid Check
     */
    boolean validCheck(Object identificationInfo);

    /**
     * Token get Claims Body
     */
    Claims getTokenForSubject(String token);

}
