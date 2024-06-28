package com.commerce.core.common.security;

import com.commerce.core.common.security.vo.IdentificationGenerateVO;
import org.springframework.security.core.Authentication;

public interface IdentifierProvider {

    /**
     * Object For Authentication Generate
     */
    Object generateIdentificationInfo(IdentificationGenerateVO vo);

    /**
     * Authentication Info Get
     */
    Authentication getAuthenticationInfo(Object identificationInfo);

    /**
     * IdentificationInfo Valid Check
     */
    boolean validCheck(Object identificationInfo);
}
