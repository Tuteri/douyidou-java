package cc.douyidou.service.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cc.douyidou.service.domain.DouTranslate;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cc.douyidou.common.utils.DateUtils;
import cc.douyidou.service.common.PageResult;
import cc.douyidou.service.common.request.PageParamRequest;
import cc.douyidou.service.common.response.CommonResult;
import cc.douyidou.service.common.response.parse.DouParseResponse;
import cc.douyidou.service.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.douyidou.service.mapper.DouParseMapper;
import cc.douyidou.service.domain.DouParse;
import cc.douyidou.service.service.IDouParseService;

/**
 * 视频解析记录Service业务层处理
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc  保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
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
        Date now = DateUtil.date();
        Date ago = DateUtil.offsetDay(now, -30);
        lqw.between(DouParse::getCreateTime, ago, now);
        
        List<DouParse> list = list(lqw);
        PageInfo<DouParse> pageInfo = new PageInfo<>(list);
        ArrayList<DouParseResponse> douParseResponseList = new ArrayList<>();
        for (DouParse douParse : list) {
            DouParseResponse douParseResponse = new DouParseResponse(douParse);
            douParseResponse.setVideo(null);
            douParseResponse.setProxy(null);
            douParseResponse.setOrigin(null);
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
