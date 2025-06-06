package cc.douyidou.service.api;

import okhttp3.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc 保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
 */
public class ApiClient {
    private final String appId;
    private final String appSecret;
    private final String baseUrl;
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    public ApiClient(String appId, String appSecret, String baseUrl) {
        this.appId = appId;
        this.appSecret = appSecret;
        this.baseUrl = baseUrl;
        this.client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)  // 连接超时
                .readTimeout(20, TimeUnit.SECONDS)     // 读取超时
                .writeTimeout(20, TimeUnit.SECONDS)    // 写入超时
                .build();
        this.objectMapper = new ObjectMapper();
    }
    
    /**
     * 视频无水印解析
     * @param url
     * @return
     * @throws Exception
     */
    public Map<String, Object> make(String url) throws Exception {
        String endpoint = "/parse";
        return sendGetRequest(endpoint, Collections.singletonMap("url", url));
    }

    public Map<String, Object> transcode(String url) throws Exception {
        String endpoint = "/translate/make";
		return sendGetRequest(endpoint, Collections.singletonMap("url", url));
    }
    public Map<String, Object> getTranscodeStats(String task) throws Exception {
        String endpoint = "/translate/stats";
        return sendGetRequest(endpoint, Collections.singletonMap("task", task));
    }

    public Map<String, Object> stopTranscode(String task) throws Exception {
        String endpoint = "/translate/stop";
        return sendGetRequest(endpoint, Collections.singletonMap("task", task));
    }
    public Map<String, Object> videoMd5(String url) throws Exception {
        String endpoint = "/videoMd5/make";
		return sendGetRequest(endpoint, Collections.singletonMap("url", url));
    }
    public Map<String, Object> getVideoMd5Stats(String task) throws Exception {
        String endpoint = "/videoMd5/stats";
        return sendGetRequest(endpoint, Collections.singletonMap("task", task));
    }
    
    public Map<String, Object> stopVideoMd5(String task) throws Exception {
        String endpoint = "/videoMd5/stop";
        return sendGetRequest(endpoint, Collections.singletonMap("task", task));
    }
    
    public Map<String, Object> videoToMp3(String url) throws Exception {
        String endpoint = "/mp4ToMp3/make";
		return sendGetRequest(endpoint, Collections.singletonMap("url", url));
    }
    public Map<String, Object> getVideoToMp3Stats(String task) throws Exception {
        String endpoint = "/mp4ToMp3/stats";
        return sendGetRequest(endpoint, Collections.singletonMap("task", task));
    }
    
    public Map<String, Object> stopVideoToMp3(String task) throws Exception {
        String endpoint = "/mp4ToMp3/stop";
        return sendGetRequest(endpoint, Collections.singletonMap("task", task));
    }
    private Map<String, Object> sendGetRequest(String endpoint, Map<String, String> params) throws Exception {
        Map<String, String> data = new HashMap<>(params);
        data.put("app_id", appId);
        
        String sign = getSign(data);
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl + endpoint).newBuilder();
        data.forEach(urlBuilder::addQueryParameter);
        //System.out.println(urlBuilder.build());
        //System.out.println(sign);
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .addHeader("Sign", sign)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("API 请求失败" + response.body().string());
            }
            return objectMapper.readValue(response.body().string(), Map.class);
        }
    }

    private String getSign(Map<String, String> data) {
        // 1. 对 key 进行排序
        List<String> keys = new ArrayList<>(data.keySet());
        Collections.sort(keys);
        
        // 2. 拼接 `key=value&` 形式的字符串
        StringBuilder preStr = new StringBuilder();
        for (String key : keys) {
            String encodedValue = URLEncoder.encode(data.get(key), StandardCharsets.UTF_8); // 仅编码 value
            preStr.append(key).append("=").append(encodedValue).append("&");
        }
        
        // 3. 移除最后的 `&`
        if (!preStr.isEmpty()) {
            preStr.deleteCharAt(preStr.length() - 1);
        }
        
        // 4. 追加 `appSecret`
        preStr.append(appSecret);
        
        // 5. 计算 MD5 哈希值
        return md5(preStr.toString());
    }

    private String md5(String str) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(str.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : array) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("MD5 计算失败", e);
        }
    }
}
