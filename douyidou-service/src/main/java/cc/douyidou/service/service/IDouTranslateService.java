package cc.douyidou.service.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import cc.douyidou.service.common.PageResult;
import cc.douyidou.service.common.request.PageParamRequest;
import cc.douyidou.service.common.response.CommonResult;
import cc.douyidou.service.common.response.parse.TranscodeResponse;
import cc.douyidou.service.domain.DouTranslate;

/**
 * 视频转码Service接口
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc 保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
 */
public interface IDouTranslateService extends IService<DouTranslate>
{
    CommonResult<TranscodeResponse> getInfo(Long id);
    DouTranslate findByTask(String task);
    PageResult<TranscodeResponse> getListByUser(DouTranslate douTranslateRequest,PageParamRequest pageParamRequest);
    
    public DouTranslate insertOne(DouTranslate douTranslate, TranscodeResponse transcodeResponse);
    
    
    
    /**
     * 查询视频转码
     *
     * @param id 视频转码主键
     * @return 视频转码
     */
    public DouTranslate selectDouTranslateById(Long id);

    /**
     * 查询视频转码列表
     *
     * @param douTranslate 视频转码
     * @return 视频转码集合
     */
    public List<DouTranslate> selectDouTranslateList(DouTranslate douTranslate);

    /**
     * 新增视频转码
     *
     * @param douTranslate 视频转码
     * @return 结果
     */
    public int insertDouTranslate(DouTranslate douTranslate);

    /**
     * 修改视频转码
     *
     * @param douTranslate 视频转码
     * @return 结果
     */
    public int updateDouTranslate(DouTranslate douTranslate);

    /**
     * 批量删除视频转码
     *
     * @param ids 需要删除的视频转码主键集合
     * @return 结果
     */
    public int deleteDouTranslateByIds(Long[] ids);

    /**
     * 删除视频转码信息
     *
     * @param id 视频转码主键
     * @return 结果
     */
    public int deleteDouTranslateById(Long id);
}
