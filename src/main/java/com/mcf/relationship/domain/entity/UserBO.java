package com.mcf.relationship.domain.entity;

import com.mcf.relationship.common.enums.UserTypeEnum;
import com.mcf.relationship.common.util.RandomUtil;
import com.mcf.relationship.common.util.UserTokenUtil;
import com.mcf.relationship.infra.model.UserDO;
import lombok.Data;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * @Author ZhuPo
 * @date 2026/2/4 21:28
 */
@Data
public class UserBO extends UserDO {
    @Serial
    private static final long serialVersionUID = 8151819389923806362L;

    public UserBO() {
    }

    /**
     * 注册用户
     *
     * @param openId
     * @param inviterId
     * @return
     */
    public UserBO (String openId, Long inviterId){
        this.setRegisterTime(LocalDateTime.now());
        this.setInviterId(inviterId);
        this.setWxOpenId(openId);
        this.setUserType(UserTypeEnum.USER.getType());
        this.setUsername(RandomUtil.generateStr(6));
    }

    /**
     * 生成token
     *
     * @return
     */
    public String generateToken(){
        UserTokenBO userTokenBO = new UserTokenBO(System.currentTimeMillis(), this.getId(), this.getWxOpenId());
        return UserTokenUtil.encrypt(userTokenBO);
    }
}
