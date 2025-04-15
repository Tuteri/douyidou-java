package cc.douyidou.service.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cc.douyidou.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.douyidou.service.mapper.DouPlatformMapper;
import cc.douyidou.service.domain.DouPlatform;
import cc.douyidou.service.service.IDouPlatformService;

/**
 * 支持的平台Service业务层处理
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc  保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
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
