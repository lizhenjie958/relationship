package com.mcf.relationship.controller.tos;

import com.mcf.relationship.common.protocol.McfResult;
import com.mcf.relationship.config.tos.TosProperties;
import com.mcf.relationship.controller.tos.request.TosRreSignRequest;
import com.mcf.relationship.intergration.tos.TosManager;
import com.volcengine.tos.model.object.PreSignedPostSignatureOutput;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * @Author ZhuPo
 * @date 2026/1/31 17:40
 */
@RestController
@RequestMapping("tos")
public class TosController {

    @Resource
    private TosProperties tosProperties;

    @Resource
    private TosManager tosManager;

    /**
     * 获取单个文件上传凭证
     */
    @PostMapping("/credential")
    public McfResult<PreSignedPostSignatureOutput> getFileUploadCredential(@RequestBody TosRreSignRequest request) {
        LocalDate now = LocalDate.now();

        PreSignedPostSignatureOutput output = tosManager.generatePreSignedPostSignature(tosProperties.getBasePath() + now + "/" +request.getFileName());
        return McfResult.success(output);
    }
}
