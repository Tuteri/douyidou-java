package com.ruoyi.service.service;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.service.domain.DouUser;
import com.ruoyi.service.entity.LoginDouUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * token验证处理
 *
 * @author ruoyi
 */
@Component
public class DouUserTokenService {
	private static final Logger log = LoggerFactory.getLogger(DouUserTokenService.class);
	
	// 令牌自定义标识
	@Value("${token.header}")
	private String header;
	
	// 令牌秘钥
	@Value("${token.secret}")
	private String secret;
	
	// 令牌有效期（默认30分钟）
	@Getter
	@Value("${token.expireTime}")
	private int expireTime;
	@Autowired
	private RedisCache redisCache;
	@Autowired
	private IDouUserService douUserService;
	
	/**
	 * 获取用户身份信息
	 *
	 * @return 用户信息
	 */
	public LoginDouUser getLoginUser(HttpServletRequest request) {
		// 获取请求携带的令牌
		String token = getToken(request);
		if (StringUtils.isNotEmpty(token)) {
			try {
				Claims claims = parseToken(token);
				// 用刷新令牌访问资源 -- 禁止
				if(ObjectUtil.isNotNull(claims.get("sub"))){
					return null;
				}
				// 解析对应的权限以及用户信息
				String wxOpenid = (String) claims.get("id");
				
				String redisToken = redisCache.getCacheObject(wxOpenid+"_at");
				if(ObjectUtil.isNull(redisToken) || !token.equals(redisToken)){
					return null;
				}
				
				DouUser user = douUserService.findByWxOpenid(wxOpenid);
				LoginDouUser loginDouUser = new LoginDouUser();
				loginDouUser.setWxOpenid(wxOpenid);
				loginDouUser.setUser(user);
				return loginDouUser;
			} catch (Exception e) {
				log.error("获取用户信息异常'{}'", e.getMessage());
			}
		}
		return null;
	}
	public LoginDouUser getRefreshLoginUser(HttpServletRequest request) {
		// 获取请求携带的令牌
		String token = getToken(request);
		if (StringUtils.isNotEmpty(token)) {
			try {
				Claims claims = parseToken(token);
				// 用刷新令牌访问资源 -- 禁止
				if(ObjectUtil.isNull(claims.get("sub"))){
					return null;
				}
				// 解析对应的权限以及用户信息
				String wxOpenid = (String) claims.get("id");
				String redisToken = redisCache.getCacheObject(wxOpenid + "_rt");
				if (ObjectUtil.isNull(redisToken) || !token.equals(redisToken)) {
					return null;
				}
				DouUser user = douUserService.findByWxOpenid(wxOpenid);
				LoginDouUser loginDouUser = new LoginDouUser();
				loginDouUser.setWxOpenid(wxOpenid);
				loginDouUser.setUser(user);
				return loginDouUser;
			} catch (Exception e) {
				log.error("获取用户信息异常'{}'", e.getMessage());
			}
		}
		return null;
	}
	
	/**
	 * 创建令牌
	 *
	 * @param loginDouUser 用户信息
	 * @return 令牌
	 */
	public String createToken(LoginDouUser loginDouUser) {
		
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", loginDouUser.getWxOpenid());
		String token = createToken(claims);
		redisCache.setCacheObject(loginDouUser.getWxOpenid() + "_at", token, getTokenExpireTime(), TimeUnit.MILLISECONDS);
		return token;
	}
	
	/**
	 * 创建刷新令牌
	 *
	 * @param loginDouUser 用户信息
	 * @return 令牌
	 */
	public String createRefreshToken(LoginDouUser loginDouUser) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", loginDouUser.getWxOpenid());
		claims.put("sub", "refresh");
		String refreshToken = createRefreshToken(claims);
		redisCache.setCacheObject(loginDouUser.getWxOpenid() + "_rt", refreshToken, getRefreshTokenExpireTime(), TimeUnit.MILLISECONDS);
		return refreshToken;
	}
	
	/**
	 * 从数据声明生成令牌
	 *
	 * @param claims 数据声明
	 * @return 令牌
	 */
	public String createToken(Map<String, Object> claims) {
		String token = Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date()) // 签发时间
				.setExpiration(new Date(System.currentTimeMillis() + getTokenExpireTime())) // 过期时间
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		return token;
	}
	
	/**
	 * 从数据声明生成令牌
	 *
	 * @param claims 数据声明
	 * @return 令牌
	 */
	public String createRefreshToken(Map<String, Object> claims) {
		String token = Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date()) // 签发时间
				.setExpiration(new Date(System.currentTimeMillis() + getRefreshTokenExpireTime())) // 过期时间
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		return token;
	}
	
	/**
	 * 从令牌中获取数据声明
	 *
	 * @param token 令牌
	 * @return 数据声明
	 */
	public Claims parseToken(String token) {
		return Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody();
	}
	
	public Integer getTokenExpireTime() {
		return expireTime * 60 * 1000;
	}
	
	public Integer getRefreshTokenExpireTime() {
		return getTokenExpireTime() * 7;
	}
	
	/**
	 * 获取请求token
	 *
	 * @param request
	 * @return token
	 */
	public String getToken(HttpServletRequest request) {
		String token = request.getHeader(header);
		if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX)) {
			token = token.replace(Constants.TOKEN_PREFIX, "");
		}
		return token;
	}
	
}
