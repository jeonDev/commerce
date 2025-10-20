package com.commerce.core.member.facade;

import com.commerce.core.member.domain.entity.Member;
import com.commerce.core.member.service.LoginService;
import com.commerce.core.member.service.MemberService;
import com.commerce.core.member.service.OAuthService;
import com.commerce.core.member.service.request.MemberServiceRequest;
import com.commerce.core.member.service.response.LoginServiceResponse;
import com.commerce.core.member.service.response.OAuthMemberResponse;
import com.commerce.core.member.type.oauth.OAuthType;
import com.commerce.core.member.type.oauth.OAuthUserInfoResponse;
import org.springframework.stereotype.Component;

@Component
public class OAuthFacade {

    private final OAuthService oAuthService;
    private final LoginService loginService;
    private final MemberService memberService;

    public OAuthFacade(OAuthService oAuthService,
                       LoginService loginService,
                       MemberService memberService
    ) {
        this.oAuthService = oAuthService;
        this.loginService = loginService;
        this.memberService = memberService;
    }

    public String getPage(String type) {
        return oAuthService.getPage(type);
    }

    public LoginServiceResponse getAccessToken(OAuthType oAuthType, String code) {
        var oAuthMemberResponse = oAuthService.getAccessToken(oAuthType, code);

        var optionalMember = memberService.findByIdAndOauthType(oAuthMemberResponse.id(), oAuthType);
        var member = optionalMember.orElseGet(() -> this.oauthCreateMember(oAuthMemberResponse, oAuthType));

        return loginService.login(member.getLoginId(), null);
    }

    private Member oauthCreateMember(OAuthMemberResponse oAuthMemberResponse, OAuthType oAuthType) {
        var request = MemberServiceRequest.builder()
                .id(oAuthMemberResponse.id())
                .name(oAuthMemberResponse.name())
                .oAuthType(oAuthType)
                .build();
        return memberService.createMember(request);
    }

    public OAuthUserInfoResponse getUserInfo(String type, String authorization) {
        return oAuthService.getUserInfo(type, authorization);
    }
}
