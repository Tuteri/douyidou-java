package com.ruoyi.service.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

import java.util.Base64;
import java.util.UUID;

@Service
public class ProxyService {
	
	private String proxyUrl;
	
	private String getPrefix() {
		return proxyUrl + "/api/f/" + UUID.randomUUID() + "?p=";
	}
	private void init(HttpServletRequest request) {
		String domain = request.getHeader("X-Host");
		if (domain.contains("127.0.0.1") || domain.contains("192.168.1.34")) {
			this.proxyUrl = "http://192.168.1.34:7999";
		} else {
			this.proxyUrl = "https://"+domain;
		}
	}
	
	public Map<String, Object> make(Map<String, Object> arr,HttpServletRequest request) {
		this.init(request);
		Map<String, Object> originArr = new HashMap<>(arr);
		Map<String, Object> proxy = addPrefixToUrlsAll(arr);
		arr.put("proxy", proxy);
		arr.put("origin", originArr);
		return arr;
	}
	
	private Map<String, Object> addPrefixToUrlsAll(Map<String, Object> data) {
		Map<String, Object> result = new HashMap<>();
		for (Map.Entry<String, Object> entry : data.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			
			if (value instanceof List) {
				// 处理数组
				List<Object> newSubArray = new ArrayList<>();
				for (Object subValue : (List<?>) value) {
					if (subValue instanceof String && isValidUrl((String) subValue)) {
						newSubArray.add(getPrefix() + base64UrlEncode((String) subValue) + "&n=" + data.get("platform"));
					} else {
						newSubArray.add(subValue);
					}
				}
				result.put(key, newSubArray);
			} else if (value instanceof String && isValidUrl((String) value)) {
				result.put(key, getPrefix() + base64UrlEncode((String) value) + "&n=" + data.get("platform"));
			} 
		}
		return result;
	}
	
	
	private boolean isValidUrl(String url) {
		try {
			new URL(url);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	private String base64UrlEncode(String input) {
		return Base64.getUrlEncoder().withoutPadding().encodeToString(input.getBytes(StandardCharsets.UTF_8));
	}
}
