package com.mcf.relationship.controller.user;

import com.mcf.relationship.common.protocol.BaseRequest;
import com.mcf.relationship.common.protocol.McfResult;
import com.mcf.relationship.controller.user.request.MaintainInviterRequest;
import com.mcf.relationship.controller.user.request.UpdateUserRequest;
import com.mcf.relationship.controller.user.response.CurrentUserResponse;
import com.mcf.relationship.domain.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author ZhuPo
 * @date 2026/2/4 22:00
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("currentUser")
    public McfResult<CurrentUserResponse> currentUser(@RequestBody BaseRequest request) {
        CurrentUserResponse response = userService.currentUser();
        return McfResult.success(response);
    }

    @PostMapping("updateUser")
    public McfResult<Void> updateUser(@RequestBody UpdateUserRequest request) {
        userService.updateUser(request);
        return McfResult.success();
    }

    /**
     * 维护邀请人
     * @param request
     * @return
     */
    @PostMapping("maintainInviter")
    public McfResult<Void> maintainInviter(@RequestBody MaintainInviterRequest request){
        userService.maintainInviter(request);
        return McfResult.success();
    }
}
