package com.mcf.relationship.intergration.tos;

import com.mcf.relationship.config.tos.TosProperties;
import com.volcengine.tos.TOSV2;
import com.volcengine.tos.comm.HttpMethod;
import com.volcengine.tos.model.object.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author ZhuPo
 * @date 2026/1/31 19:58
 */
@Slf4j
@Component
public class TosManager {
    @Resource
    private TosProperties tosProperties;

    @Resource
    private TOSV2 tosClient;


    /**
     * 生成预签名URL（用于前端直传）
     */
    public PreSignedURLOutput generatePreSignedUrl(String objectKey) {
        try {
            PreSignedURLInput input = PreSignedURLInput.builder()
                    .bucket(tosProperties.getBucket())
                    .key(objectKey)
                    .httpMethod(HttpMethod.PUT)
                    .expires(tosProperties.getExpireSeconds())
                    .build();

            return tosClient.preSignedURL(input);
        } catch (Exception e) {
            log.error("生成预签名URL失败", e);
            throw new RuntimeException("生成预签名URL失败", e);
        }
    }

    /**
     * 生成临时上传凭证
     */
    public PreSignedPostSignatureOutput generatePreSignedPostSignature(String objectKey) {
        try {
            PreSignedPostSignatureInput input = PreSignedPostSignatureInput.builder()
                    .bucket(tosProperties.getBucket())
                    .key(objectKey)
                    .expires(tosProperties.getExpireSeconds())
                    .conditions(new ArrayList<>())
                    .build();

            return tosClient.preSignedPostSignature(input);
        } catch (Exception e) {
            log.error("生成临时上传凭证失败", e);
            throw new RuntimeException("生成临时上传凭证失败", e);
        }
    }

    /**
     * 获取文件列表
     */
    public List<String> listFiles(String directory) {
        try {
            ListObjectsType2Input input = ListObjectsType2Input.builder()
                    .bucket(tosProperties.getBucket())
                    .prefix(directory)
                    .build();

            ListObjectsType2Output output = tosClient.listObjectsType2(input);

            return output.getContents().stream()
                    .map(ListedObjectV2::getKey)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("获取文件列表失败", e);
            throw new RuntimeException("获取文件列表失败", e);
        }
    }

    /**
     * 删除文件
     */
    public void deleteFile(String objectKey) {
        try {
            DeleteObjectInput input = DeleteObjectInput.builder()
                    .bucket(tosProperties.getBucket())
                    .key(objectKey)
                    .build();

            tosClient.deleteObject(input);
            log.info("文件删除成功: {}", objectKey);
        } catch (Exception e) {
            log.error("文件删除失败", e);
            throw new RuntimeException("文件删除失败", e);
        }
    }

    /**
     * 生成文件访问URL（临时）
     */
    public String generateFileUrl(String objectKey, Long expireSeconds) {
        try {
            PreSignedURLInput input = PreSignedURLInput.builder()
                    .bucket(tosProperties.getBucket())
                    .key(objectKey)
                    .httpMethod(HttpMethod.GET)
                    .expires(expireSeconds != null ? expireSeconds : 3600L)
                    .build();

            PreSignedURLOutput output = tosClient.preSignedURL(input);
            return output.getSignedUrl();
        } catch (Exception e) {
            log.error("生成文件访问URL失败", e);
            throw new RuntimeException("生成文件访问URL失败", e);
        }
    }
}
