package cc.douyidou.service.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cc.douyidou.service.domain.DouReward;
import org.apache.ibatis.annotations.Mapper;

/**
 * 激励广告奖励Mapper接口
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc 保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
 */
@Mapper
public interface DouRewardMapper extends BaseMapper<DouReward>
{
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
     * 删除激励广告奖励
     * 
     * @param id 激励广告奖励主键
     * @return 结果
     */
    public int deleteDouRewardById(Long id);

    /**
     * 批量删除激励广告奖励
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDouRewardByIds(Long[] ids);
}
