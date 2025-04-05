package com.ruoyi.service.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.service.common.PageResult;
import com.ruoyi.service.common.request.PageParamRequest;
import com.ruoyi.service.common.response.CommonResult;
import com.ruoyi.service.common.response.parse.DouParseResponse;
import com.ruoyi.service.common.response.parse.VideoParseResponse;
import com.ruoyi.service.domain.DouParse;

/**
 * 视频解析记录Service接口
 * 
 * @author Tuteri
 * @date 2025-03-23
 */
public interface IDouParseService extends IService<DouParse>
{
    CommonResult<DouParseResponse> getInfo(DouParse douParseRequest);
    Long getTodayCount();
    /**
     * 查询视频解析记录
     *
     * @return 视频解析记录
     */
    public PageResult<DouParseResponse> getListByUser(DouParse douParse, PageParamRequest pageParamRequest);
    /**
     * 查询视频解析记录
     * @param urlHash 视频解析记录主键
     * @return 视频解析记录
     */
    public DouParse findByHash(String urlHash);
    /**
     * 查询视频解析记录
     * 
     * @param id 视频解析记录主键
     * @return 视频解析记录
     */
    public DouParse selectDouParseById(Long id);

    /**
     * 查询视频解析记录列表
     * 
     * @param douParse 视频解析记录
     * @return 视频解析记录集合
     */
    public List<DouParse> selectDouParseList(DouParse douParse);

    /**
     * 新增视频解析记录
     * 
     * @param douParse 视频解析记录
     * @return 结果
     */
    public int insertDouParse(DouParse douParse);

    /**
     * 修改视频解析记录
     * 
     * @param douParse 视频解析记录
     * @return 结果
     */
    public int updateDouParse(DouParse douParse);

    /**
     * 批量删除视频解析记录
     * 
     * @param ids 需要删除的视频解析记录主键集合
     * @return 结果
     */
    public int deleteDouParseByIds(Long[] ids);

    /**
     * 删除视频解析记录信息
     * 
     * @param id 视频解析记录主键
     * @return 结果
     */
    public int deleteDouParseById(Long id);
}
