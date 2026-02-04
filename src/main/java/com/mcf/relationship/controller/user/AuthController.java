package com.mcf.relationship.controller.user;

import com.mcf.relationship.common.protocol.McfResult;
import com.mcf.relationship.controller.user.request.LoginByWxRequest;
import com.mcf.relationship.controller.user.response.LoginResponse;
import com.mcf.relationship.domain.service.UserAuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author ZhuPo
 * @date 2026/2/4 20:11
 */
@RestController
@RequestMapping("auth")
public class AuthController {

    @Resource
    private UserAuthService userAuthService;

    @PostMapping("loginByWx")
    public McfResult<LoginResponse> loginByWx(@RequestBody LoginByWxRequest request){
        LoginResponse loginResponse = userAuthService.loginByWx(request);
        return McfResult.success(loginResponse);
    }

}
