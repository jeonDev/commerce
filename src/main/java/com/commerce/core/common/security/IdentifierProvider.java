package com.commerce.core.common.security;

import com.commerce.core.common.security.vo.AuthenticationInfo;
import com.commerce.core.common.security.vo.IdentificationVO;

public interface IdentifierProvider {

    /**
     * Object For Authentication Generate
     */
    Object generateIdentificationInfo(IdentificationVO vo);

    /**
     * Authentication Info Get
     */
    AuthenticationInfo getAuthenticationInfo(Object identificationInfo);

    /**
     * IdentificationInfo Valid Check
     */
    boolean validCheck(Object identificationInfo);
}
