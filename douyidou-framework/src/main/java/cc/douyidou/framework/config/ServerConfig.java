package cc.douyidou.framework.config;

import cn.hutool.core.util.ObjectUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import cc.douyidou.common.utils.ServletUtils;

/**
 * 服务相关配置
 * 
 * @author ruoyi
 */
@Component
public class ServerConfig
{
    /**
     * 获取完整的请求路径，包括：域名，端口，上下文访问路径
     * 
     * @return 服务地址
     */
    public String getUrl()
    {
        HttpServletRequest request = ServletUtils.getRequest();
        return getDomain(request);
    }

    public static String getDomain(HttpServletRequest request)
    {
        String domain = request.getHeader("X-Host");
        if(ObjectUtil.isNull(domain)) domain = request.getHeader("Host");
        if (domain.contains("127.0.0.1") || domain.contains("localhost") || domain.contains("192.168")) {
            StringBuffer url = request.getRequestURL();
            String contextPath = request.getServletContext().getContextPath();
            return url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
        } else {
            return "https://"+domain;
        }
    }
}
