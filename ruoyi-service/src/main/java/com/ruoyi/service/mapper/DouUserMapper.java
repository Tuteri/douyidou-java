package com.ruoyi.service.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.service.domain.DouUser;

/**
 * 应用用户Mapper接口
 * 
 * @author Tuteri
 * @date 2025-03-20
 */
public interface DouUserMapper extends BaseMapper<DouUser>
{
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
     * 删除应用用户
     *
     * @param id 应用用户主键
     * @return 结果
     */
    public int deleteDouUserById(Long id);

    /**
     * 批量删除应用用户
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDouUserByIds(Long[] ids);
}
