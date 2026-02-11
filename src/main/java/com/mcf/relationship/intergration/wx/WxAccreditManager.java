package com.mcf.relationship.intergration.wx;

import com.alibaba.fastjson2.JSONObject;
import com.mcf.relationship.common.enums.BizExceptionEnum;
import com.mcf.relationship.common.exception.BizException;
import com.mcf.relationship.common.util.AssertUtil;
import com.mcf.relationship.common.util.HttpUtil;
import com.mcf.relationship.config.wx.WxProperties;
import com.mcf.relationship.intergration.entity.WxAccreditBO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author ZhuPo
 * @date 2026/2/4 20:38
 */
@Component
@EnableConfigurationProperties(WxProperties.class)
@Slf4j
public class WxAccreditManager {

    private static final String SESSION_USER_INFO = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";


    @Resource
    private WxProperties wxProperties;

    @Value("${spring.active.profile:dev}")
    private String activeProfile;


    /**
     * 0d32bf2w3jU5Y13uVH2w3Lbxa442bf2I
     * {
     *      "session_key": "6yiQMNn9CdN\/qlBaT646rw==",
     *      "openid": "ouyVY5JJN9cOm9c8lVIxyIwdHqLU"
     * }
     *
     * @param openIdAuthCode
     * @return
     */
    public String getOpenId(String openIdAuthCode) {
        AssertUtil.checkStringNotBlank(openIdAuthCode, "微信OpenId授权Code");
        if("dev".equals(activeProfile) && "the code is a mock one".equals(openIdAuthCode)){
            return "o7i5g3e9snDwrlGDxLAs7bIbOVqw";
        }
        String url = String.format(SESSION_USER_INFO, wxProperties.getAppId(), wxProperties.getAppSecret(), openIdAuthCode);
        String result = HttpUtil.doGet(url);
        WxAccreditBO wxAccreditBO = JSONObject.parseObject(result, WxAccreditBO.class);
        if (wxAccreditBO != null && StringUtils.isNoneBlank(wxAccreditBO.getOpenId())) {
            return wxAccreditBO.getOpenId();
        }
        log.error("getOpenId#failed#{}", result);
        throw new BizException(BizExceptionEnum.WX_AUTH_ERROR);
    }

}
