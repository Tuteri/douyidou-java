package cc.douyidou.service.common.response.parse;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc 保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
 */
@Data
public class TranscodeResponse implements Serializable {
	// 任务名
	private Long id;
	// 任务名
	private String name;
	// 任务id
	private String task;
	// 任务状态
	private Integer stats;
	// 已转换文件大小 kb
	private BigInteger size;
	// 原url
	private String url;
	// 下载url
	private String downloadUrl;
	// 目标时长
	private Integer time;
	// 类型
	private String type;
	// 转码类型
	private String source;
	private String target;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date doneTime;
}
