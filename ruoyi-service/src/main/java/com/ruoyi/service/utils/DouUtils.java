package com.ruoyi.service.utils;

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
}
