package com.mcf.relationship.common.util;

import com.mcf.relationship.common.enums.SystemExceptionEnum;
import com.mcf.relationship.common.exception.CommonException;
import com.mcf.relationship.common.exception.SysException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * @Author ZhuPo
 * @date 2026/2/4 21:17
 */
public final class HttpUtil {
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5)) // 连接超时
            .build();

    /**
     * 发送 GET 请求
     */
    public static String doGet(String url) {
        try {
            AssertUtil.checkStringNotBlank(url,"请求地址");
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(10)) // 请求超时
                    .GET()
                    .build();
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                throw new SysException(SystemExceptionEnum.HTTP_REQUEST_ERROR);
            }
        } catch (CommonException commonException){
            throw commonException;
        } catch (Exception e) {
            throw new SysException(SystemExceptionEnum.HTTP_REQUEST_UNKNOWN_ERROR, e.getMessage());
        }
    }

    /**
     * 发送 POST 请求（JSON 格式）
     */
    public static String doPost(String url, String jsonBody) {
        try {
            AssertUtil.checkStringNotBlank(url,"请求地址");
            AssertUtil.checkStringNotBlank(jsonBody,"请求参数");
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(10))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                throw new SysException(SystemExceptionEnum.HTTP_REQUEST_ERROR);
            }
        } catch (CommonException commonException){
            throw commonException;
        } catch (Exception e) {
            throw new SysException(SystemExceptionEnum.HTTP_REQUEST_UNKNOWN_ERROR, e.getMessage());
        }
    }
}
