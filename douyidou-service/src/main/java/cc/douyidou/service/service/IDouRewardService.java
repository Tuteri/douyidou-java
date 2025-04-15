package cc.douyidou.service.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import cc.douyidou.service.domain.DouReward;

/**
 * 激励广告奖励Service接口
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc 保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
 */
public interface IDouRewardService extends IService<DouReward> {
	
	
	/**
	 * 统计今日分享次数
	 *
	 * @return Integer
	 */
	Long dayCountByUser();
	
	DouReward sendReward(DouReward douRewardRequest);
	
	DouReward findLastByUser();
	
	/**
	 * 查询激励广告奖励
	 *
	 * @param id 激励广告奖励主键
	 * @return 激励广告奖励
	 */
	public DouReward selectDouRewardById(Long id);
	
	/**
	 * 查询激励广告奖励列表
	 *
	 * @param douReward 激励广告奖励
	 * @return 激励广告奖励集合
	 */
	public List<DouReward> selectDouRewardList(DouReward douReward);
	
	/**
	 * 新增激励广告奖励
	 *
	 * @param douReward 激励广告奖励
	 * @return 结果
	 */
	public int insertDouReward(DouReward douReward);
	
	/**
	 * 修改激励广告奖励
	 *
	 * @param douReward 激励广告奖励
	 * @return 结果
	 */
	public int updateDouReward(DouReward douReward);
	
	/**
	 * 批量删除激励广告奖励
	 *
	 * @param ids 需要删除的激励广告奖励主键集合
	 * @return 结果
	 */
	public int deleteDouRewardByIds(Long[] ids);
	
	/**
	 * 删除激励广告奖励信息
	 *
	 * @param id 激励广告奖励主键
	 * @return 结果
	 */
	public int deleteDouRewardById(Long id);
}
