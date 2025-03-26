package com.ruoyi.service.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.service.mapper.DouPlatformMapper;
import com.ruoyi.service.domain.DouPlatform;
import com.ruoyi.service.service.IDouPlatformService;

/**
 * 支持的平台Service业务层处理
 * 
 * @author Tuteri
 * @date 2025-03-23
 */
@Service
public class DouPlatformServiceImpl extends ServiceImpl<DouPlatformMapper,DouPlatform> implements IDouPlatformService
{
    @Autowired
    private DouPlatformMapper douPlatformMapper;

    /**
     * 查询支持的平台
     * 
     * @param id 支持的平台主键
     * @return 支持的平台
     */
    @Override
    public DouPlatform selectDouPlatformById(Long id)
    {
        return douPlatformMapper.selectDouPlatformById(id);
    }

    /**
     * 查询支持的平台列表
     * 
     * @param douPlatform 支持的平台
     * @return 支持的平台
     */
    @Override
    public List<DouPlatform> selectDouPlatformList(DouPlatform douPlatform)
    {
        return douPlatformMapper.selectDouPlatformList(douPlatform);
    }

    /**
     * 新增支持的平台
     * 
     * @param douPlatform 支持的平台
     * @return 结果
     */
    @Override
    public int insertDouPlatform(DouPlatform douPlatform)
    {
        douPlatform.setCreateTime(DateUtils.getNowDate());
        return douPlatformMapper.insertDouPlatform(douPlatform);
    }

    /**
     * 修改支持的平台
     * 
     * @param douPlatform 支持的平台
     * @return 结果
     */
    @Override
    public int updateDouPlatform(DouPlatform douPlatform)
    {
        douPlatform.setUpdateTime(DateUtils.getNowDate());
        return douPlatformMapper.updateDouPlatform(douPlatform);
    }

    /**
     * 批量删除支持的平台
     * 
     * @param ids 需要删除的支持的平台主键
     * @return 结果
     */
    @Override
    public int deleteDouPlatformByIds(Long[] ids)
    {
        return douPlatformMapper.deleteDouPlatformByIds(ids);
    }

    /**
     * 删除支持的平台信息
     * 
     * @param id 支持的平台主键
     * @return 结果
     */
    @Override
    public int deleteDouPlatformById(Long id)
    {
        return douPlatformMapper.deleteDouPlatformById(id);
    }
}
