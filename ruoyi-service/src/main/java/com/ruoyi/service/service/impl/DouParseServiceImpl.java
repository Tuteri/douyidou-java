package com.ruoyi.service.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.service.common.PageResult;
import com.ruoyi.service.common.request.PageParamRequest;
import com.ruoyi.service.common.response.CommonResult;
import com.ruoyi.service.common.response.parse.DouParseResponse;
import com.ruoyi.service.utils.SecurityUtils;
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
    @Override
    public CommonResult<DouParseResponse> getInfo(DouParse douParseRequest) {
        LambdaQueryWrapper<DouParse> lqw = getLqw();
        lqw.eq(DouParse::getId,douParseRequest.getId());
        DouParse douParse = getOne(lqw);
        DouParseResponse douParseResponse = new DouParseResponse(douParse);
        return CommonResult.success(douParseResponse);
    }
    @Override
    public PageResult<DouParseResponse> getListByUser(DouParse douParseRequest, PageParamRequest pageParamRequest) {
        PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
        LambdaQueryWrapper<DouParse> lqw = getLqw();
        lqw.ne(DouParse::getPlatform,0);
        lqw.orderByDesc(DouParse::getId);
        List<DouParse> list = list(lqw);
        PageInfo<DouParse> pageInfo = new PageInfo<>(list);
        ArrayList<DouParseResponse> douParseResponseList = new ArrayList<>();
        for (DouParse douParse : list) {
            DouParseResponse douParseResponse = new DouParseResponse(douParse);
            douParseResponseList.add(douParseResponse);
        }
        PageResult<DouParseResponse> douParseResponsePageInfo = PageResult.restPage(pageInfo, douParseResponseList);
        return douParseResponsePageInfo;
    }
    
    /**
     * 获取今日解析数量
     * @return
     */
    @Override
    public Long getTodayCount() {
        LambdaQueryWrapper<DouParse> lqw = getLqw();
        lqw.between(DouParse::getCreateTime, DateUtil.beginOfDay(new Date()), DateUtil.endOfDay(new Date()));
        return count(lqw);
    }
    
    @Override
    public DouParse findByHash(String urlHash) {
        LocalDateTime fiveMinutesAgo = LocalDateTime.now().minusMinutes(5);
        LambdaQueryWrapper<DouParse> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DouParse::getUrlHash,urlHash)
                .ge(DouParse::getCreateTime,fiveMinutesAgo).last("LIMIT 1");
        return getOne(lqw);
    }
    private LambdaQueryWrapper<DouParse> getLqw(){
        Long id = SecurityUtils.getLoginDouUser().getUser().getId();
        LambdaQueryWrapper<DouParse> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DouParse::getUid, id);
        return lqw;
    }
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
