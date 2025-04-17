package cc.douyidou.service.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import cc.douyidou.service.common.response.CommonResult;
import cc.douyidou.service.common.response.user.DouUserResponse;
import cc.douyidou.service.domain.DouUser;

/**
 * 应用用户Service接口
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc  保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
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
	public Boolean parseNumConsumer();
	
	Boolean parseNumConsumer(Integer parseNum);
	/**
	 * 登录用户信息
	 * @return
	 */
	public DouUserResponse getInfo();
	boolean checkParse();
	boolean checkParse(Integer parseNum);
	/**
	 * tokens兑换parseNum
	 */
	public CommonResult<Object> tokensToParseNum(DouUser douUserRequest);
	/**
	 * 用户清空 parseNumTemp Task
	 */
	public void clearPntTask();
	
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
