package cc.douyidou.service.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import cc.douyidou.common.annotation.Excel;

/**
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc 保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
 */
@Data
public class DouUser extends DouBase
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 微信昵称 */
    @Excel(name = "微信昵称")
    private String wxName;

    /** 微信openid */
    @Excel(name = "微信openid")
    private String wxOpenid;

    /** 用户名 */
    @Excel(name = "用户名")
    private String username;

    /** 密码 */
    @Excel(name = "密码")
    private String password;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /** 头像 */
    @Excel(name = "头像")
    private String avatar;
    /** 注册ip */
    @Excel(name = "注册ip")
    private String registerIp;
    /** 登录ip */
    private String loginIp;
    /** 上次登录ip */
    private String lastLoginIp;
    /** 用户等级 */
    @Excel(name = "用户等级")
    private Integer level;
    /** 解析/下载/转码次数 */
    @Excel(name = "解析/下载/转码次数")
    private Integer parseNum;
    /** tokens */
    @Excel(name = "tokens")
    private Integer tokens;
    /** 临时可用 解析/下载/转码次数 */
    @Excel(name = "临时可用 解析/下载/转码次数")
    private Integer parseNumTemp;
    
}
