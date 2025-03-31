package com.ruoyi.service.utils;

import jakarta.servlet.http.HttpServletRequest;

import java.net.URL;

public class DouUtils {
	public static String getUrlExt(String urlString) {
		try {
			URL url = new URL(urlString);
			String path = url.getPath(); // 只获取 URL 的路径部分
			int lastDotIndex = path.lastIndexOf(".");
			if (lastDotIndex != -1 && lastDotIndex < path.length() - 1) {
				return path.substring(lastDotIndex).toLowerCase();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public static String md5(String str) {
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
	public static String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
			// X-Forwarded-For 可能返回多个 IP，取第一个非 unknown 的
			return ip.split(",")[0].trim();
		}
		ip = request.getHeader("X-Real-IP");
		if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
			// X-Forwarded-For 可能返回多个 IP，取第一个非 unknown 的
			return ip.split(",")[0].trim();
		}
		
		ip = request.getHeader("Proxy-Client-IP");
		if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		
		ip = request.getHeader("WL-Proxy-Client-IP");
		if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		
		ip = request.getHeader("HTTP_CLIENT_IP");
		if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		
		ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		
		return request.getRemoteAddr();  // 最后才取默认的 remoteAddr
	}
}
