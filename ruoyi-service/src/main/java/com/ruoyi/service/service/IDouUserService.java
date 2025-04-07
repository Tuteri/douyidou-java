package com.ruoyi.service.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.service.common.response.CommonResult;
import com.ruoyi.service.common.response.user.DouUserResponse;
import com.ruoyi.service.domain.DouUser;

/**
 * 应用用户Service接口
 *
 * @author Tuteri
 * @date 2025-03-20
 */
public interface IDouUserService extends IService<DouUser> {
	
	/**
	 * 查询应用用户
	 *
	 * @param openid wxOpenid
	 * @return 应用用户
	 */
	public DouUser findByWxOpenid(String openid);
	/**
	 * parseNum消费 -1 常用于解析/下载/转码等
	 * @return 应用用户
	 */
	public Boolean parseNumConsumer(Integer type);
	/**
	 * 登录用户信息
	 * @return
	 */
	public DouUserResponse getInfo();
	
	boolean checkParse(DouUser updateDouUser);
	/**
	 * tokens兑换parseNum
	 */
	public CommonResult<Object> tokensToParseNum(DouUser douUserRequest);
	/**
	 * 用户看广告 奖励下发
	 */
	public void reward();
	
	/**
	 * 查询应用用户
	 *
	 * @param id 应用用户主键
	 * @return 应用用户
	 */
	public DouUser selectDouUserById(Long id);
	
	/**
	 * 查询应用用户列表
	 *
	 * @param douUser 应用用户
	 * @return 应用用户集合
	 */
	public List<DouUser> selectDouUserList(DouUser douUser);
	
	/**
	 * 新增应用用户
	 *
	 * @param douUser 应用用户
	 * @return 结果
	 */
	public int insertDouUser(DouUser douUser);
	
	/**
	 * 修改应用用户
	 *
	 * @param douUser 应用用户
	 * @return 结果
	 */
	public int updateDouUser(DouUser douUser);
	
	/**
	 * 批量删除应用用户
	 *
	 * @param ids 需要删除的应用用户主键集合
	 * @return 结果
	 */
	public int deleteDouUserByIds(Long[] ids);
	
	/**
	 * 删除应用用户信息
	 *
	 * @param id 应用用户主键
	 * @return 结果
	 */
	public int deleteDouUserById(Long id);
}
