package cc.douyidou.web.controller.api;

import jakarta.servlet.http.HttpServletResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

/**
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc  保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
 */
@RestController
@RequestMapping("/api")
public class FetchApi {
	
	
	@GetMapping(value = {"f","f/{filename}"})
	public void fetch(
			@PathVariable(required = false) String filename,
			@RequestParam(name = "p", required = false) String p,
			@RequestParam(name = "n", required = false) Integer platform,
			WebRequest request,
			HttpServletResponse response) {
		if (platform == null) {
			sendJsonError(response, HttpStatus.BAD_REQUEST, "Invalid URL");
			return;
		}
		
		String decodedUrl = decodeBase64Url(p);
		if (decodedUrl == null || !isValidUrl(decodedUrl)) {
			sendJsonError(response, HttpStatus.BAD_REQUEST, "Invalid URL");
			return;
		}
		
		try {
			URI uri = new URI(decodedUrl);
			String host = uri.getHost();
			if (host == null || host.isEmpty()) {
				sendJsonError(response, HttpStatus.BAD_REQUEST, "Invalid Host");
				return;
			}
			OkHttpClient httpClient = new OkHttpClient.Builder()
					.followRedirects(true)
					.followSslRedirects(true)
					.build();
			Request.Builder requestBuilder = new Request.Builder().url(decodedUrl);
					//.addHeader(HttpHeaders.HOST, host);
			
			if (platform == 4) { // 微博
				String ua = request.getHeader(HttpHeaders.USER_AGENT);
				if (ua == null) {
					sendJsonError(response, HttpStatus.BAD_REQUEST, "Invalid Post");
					return;
				}
				requestBuilder.addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (iPhone; CPU iPhone OS 16_6 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.6 Mobile/15E148 Safari/604.1")
						.addHeader(HttpHeaders.REFERER, "https://m.weibo.com/");
			} else if (platform == 17) { // 新片场
				String ua = request.getHeader(HttpHeaders.USER_AGENT);
				if (ua == null) {
					sendJsonError(response, HttpStatus.BAD_REQUEST, "Invalid Post");
					return;
				}
				requestBuilder.addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (iPhone; CPU iPhone OS 16_6 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.6 Mobile/15E148 Safari/604.1")
						.addHeader(HttpHeaders.REFERER, "https://h5.xinpianchang.com/")
						.addHeader(HttpHeaders.RANGE, "bytes=0-");
			}
			
			try (Response okhttpResponse = httpClient.newCall(requestBuilder.build()).execute()) {
				if (!okhttpResponse.isSuccessful() || okhttpResponse.body() == null) {
					System.out.println(decodedUrl);
					System.out.println(okhttpResponse);
					sendJsonError(response, HttpStatus.INTERNAL_SERVER_ERROR, "Request failed");
					return;
				}
				
				// 设置 Content-Type 和 Content-Length
				response.setContentType(okhttpResponse.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE));
				response.setHeader(HttpHeaders.CONTENT_LENGTH, okhttpResponse.header(HttpHeaders.CONTENT_LENGTH, "0"));
				response.setHeader(HttpHeaders.ACCEPT_RANGES, "bytes");
				
				// 处理文件扩展名
				String ext = getFileExtension(decodedUrl);
				if (ext != null) {
					String fileName = UUID.randomUUID() + "." + ext;
					response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
				}else{
					response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline");
				}
				
				// 获取输入流并写入响应
				try (InputStream inputStream = okhttpResponse.body().byteStream()) {
					byte[] buffer = new byte[1024];
					int bytesRead;
					while ((bytesRead = inputStream.read(buffer)) != -1) {
						response.getOutputStream().write(buffer, 0, bytesRead);
						response.flushBuffer();
					}
				}
			}
		} catch (Exception e) {
			sendJsonError(response, HttpStatus.INTERNAL_SERVER_ERROR, "Request failed: " + e.getMessage());
		}
	}
	
	private String decodeBase64Url(String encoded) {
		if (encoded == null || encoded.isEmpty()) {
			return null;
		}
		try {
			return new String(Base64.getUrlDecoder().decode(encoded), StandardCharsets.UTF_8);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
	
	private boolean isValidUrl(String url) {
		try {
			new URL(url).toURI();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	private String getFileExtension(String url) {
		try {
			String path = new URL(url).getPath(); // 只获取路径部分
			int lastDot = path.lastIndexOf(".");
			if (lastDot != -1 && lastDot < path.length() - 1) {
				return path.substring(lastDot + 1);
			}
		} catch (Exception ignored) {}
		return null;
	}
	
	private void sendJsonError(HttpServletResponse response, HttpStatus status, String message) {
		try {
			response.setStatus(status.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.getWriter().write("{\"error\": \"" + message + "\"}");
			response.getWriter().flush();
		} catch (Exception ignored) {
		}
	}
}
