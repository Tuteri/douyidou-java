package com.ruoyi.service.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.service.domain.DouPlatform;

/**
 * 支持的平台Service接口
 * 
 * @author Tuteri
 * @date 2025-03-23
 */
public interface IDouPlatformService extends IService<DouPlatform>
{
    /**
     * 查询支持的平台
     * 
     * @param id 支持的平台主键
     * @return 支持的平台
     */
    public DouPlatform selectDouPlatformById(Long id);

    /**
     * 查询支持的平台列表
     * 
     * @param douPlatform 支持的平台
     * @return 支持的平台集合
     */
    public List<DouPlatform> selectDouPlatformList(DouPlatform douPlatform);

    /**
     * 新增支持的平台
     * 
     * @param douPlatform 支持的平台
     * @return 结果
     */
    public int insertDouPlatform(DouPlatform douPlatform);

    /**
     * 修改支持的平台
     * 
     * @param douPlatform 支持的平台
     * @return 结果
     */
    public int updateDouPlatform(DouPlatform douPlatform);

    /**
     * 批量删除支持的平台
     * 
     * @param ids 需要删除的支持的平台主键集合
     * @return 结果
     */
    public int deleteDouPlatformByIds(Long[] ids);

    /**
     * 删除支持的平台信息
     * 
     * @param id 支持的平台主键
     * @return 结果
     */
    public int deleteDouPlatformById(Long id);
}
