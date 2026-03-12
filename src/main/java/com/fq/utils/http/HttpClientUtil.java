package com.fq.utils.http;

import cn.hutool.core.util.StrUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @Author 超chao
 * @Description http工具
 * @Date 2024/10/16/周三 15:12
 * @Version 1.0
 */
@Log4j2
public class HttpClientUtil {

    /**
     * 常规URL的连接符号
     */
    private static final String PARAM_STARTER = "?";
    private static final String PARAM_CONCAT = "&";
    private static final String ENCODING = "UTF-8";
    /**
     * GET方法返回JSON数据
     *
     * @param url
     * @return
     */
    public static String getJson(String url, Map<String, String> headers, Map<String, String> params) {
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            String fullUrl = buildParamsUrl(url, params);
            HttpGet httpGet = new HttpGet(fullUrl);

            // 循环加入文件头
            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> entry : headers.entrySet()){
                    httpGet.addHeader(entry.getKey(), entry.getValue());
                }
            }

            CloseableHttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity, ENCODING);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * POST方法返回JSON数据
     * @param url
     * @param headers
     * @param params
     * @return
     */
    public static String postJson(String url, Map<String, String> headers, String params) {
        CloseableHttpClient client = HttpClients.createDefault();
        try {

            HttpPost httpPost = new HttpPost(url);
            StringEntity entity = new StringEntity(params,ENCODING);
            httpPost.setEntity(entity);
            // 循环加入文件头
            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> entry : headers.entrySet()){
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            CloseableHttpResponse response = client.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            return EntityUtils.toString(responseEntity, ENCODING);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 构造一个带参数的GET形式URL
     *
     * @param url
     * @param params
     * @return
     */
    public static String buildParamsUrl(String url, Map<String, String> params) {

        // 拼接参数
        if (params != null && !params.isEmpty()) {

            StringBuffer sb = new StringBuffer(url);

            //判断URL是否已经有问题号了
            if (!url.contains(PARAM_STARTER)) {
                sb.append(PARAM_STARTER);
            }

            for (Map.Entry<String, String> entry : params.entrySet()) {

                if (!sb.toString().endsWith(PARAM_STARTER)) {
                    sb.append(PARAM_CONCAT);
                }
                String key = entry.getKey();

                String value = params.get(key);
                if (StrUtil.isBlank(value)) {
                    value = "";
                } else {
                    // 值做一下URL转码
                    try {
                        value = URLEncoder.encode(value, ENCODING);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        continue;
                    }
                }
                sb.append(key).append("=").append(value);
            }

            return sb.toString();
        }

        return url;
    }
}
