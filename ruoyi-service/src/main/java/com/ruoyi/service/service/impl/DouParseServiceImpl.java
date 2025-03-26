package com.ruoyi.service.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.service.domain.DouUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.service.mapper.DouParseMapper;
import com.ruoyi.service.domain.DouParse;
import com.ruoyi.service.service.IDouParseService;

/**
 * 视频解析记录Service业务层处理
 * 
 * @author Tuteri
 * @date 2025-03-23
 */
@Service
public class DouParseServiceImpl extends ServiceImpl<DouParseMapper, DouParse> implements IDouParseService
{
    @Autowired
    private DouParseMapper douParseMapper;

    /**
     * 查询视频解析记录
     * 
     * @param id 视频解析记录主键
     * @return 视频解析记录
     */
    @Override
    public DouParse selectDouParseById(Long id)
    {
        return douParseMapper.selectDouParseById(id);
    }

    /**
     * 查询视频解析记录列表
     * 
     * @param douParse 视频解析记录
     * @return 视频解析记录
     */
    @Override
    public List<DouParse> selectDouParseList(DouParse douParse)
    {
        return douParseMapper.selectDouParseList(douParse);
    }

    /**
     * 新增视频解析记录
     * 
     * @param douParse 视频解析记录
     * @return 结果
     */
    @Override
    public int insertDouParse(DouParse douParse)
    {
        douParse.setCreateTime(DateUtils.getNowDate());
        return douParseMapper.insertDouParse(douParse);
    }

    /**
     * 修改视频解析记录
     * 
     * @param douParse 视频解析记录
     * @return 结果
     */
    @Override
    public int updateDouParse(DouParse douParse)
    {
        douParse.setUpdateTime(DateUtils.getNowDate());
        return douParseMapper.updateDouParse(douParse);
    }

    /**
     * 批量删除视频解析记录
     * 
     * @param ids 需要删除的视频解析记录主键
     * @return 结果
     */
    @Override
    public int deleteDouParseByIds(Long[] ids)
    {
        return douParseMapper.deleteDouParseByIds(ids);
    }

    /**
     * 删除视频解析记录信息
     * 
     * @param id 视频解析记录主键
     * @return 结果
     */
    @Override
    public int deleteDouParseById(Long id)
    {
        return douParseMapper.deleteDouParseById(id);
    }
}
