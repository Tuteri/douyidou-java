package com.ruoyi.service.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.service.common.PageResult;
import com.ruoyi.service.common.request.PageParamRequest;
import com.ruoyi.service.common.response.CommonResult;
import com.ruoyi.service.common.response.parse.TranscodeResponse;
import com.ruoyi.service.domain.DouUser;
import com.ruoyi.service.entity.LoginDouUser;
import com.ruoyi.service.utils.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.service.mapper.DouTranslateMapper;
import com.ruoyi.service.domain.DouTranslate;
import com.ruoyi.service.service.IDouTranslateService;

/**
 * 视频转码Service业务层处理
 *
 * @author Tuteri
 * @date 2025-03-20
 */
@Service
public class DouTranslateServiceImpl extends ServiceImpl<DouTranslateMapper, DouTranslate> implements IDouTranslateService {
	@Autowired
	private DouTranslateMapper douTranslateMapper;
	
	@Override
	public CommonResult<TranscodeResponse> getInfo(Long id) {
		LambdaQueryWrapper<DouTranslate> lqw = getLqw();
		lqw.eq(DouTranslate::getId,id);
		DouTranslate douTranslate = getOne(lqw);
		TranscodeResponse transcodeResponse = new TranscodeResponse();
		BeanUtils.copyProperties(douTranslate,transcodeResponse);
		transcodeResponse.setStats(douTranslate.getStatus());
		transcodeResponse.setTime(douTranslate.getTargetTime());
		return CommonResult.success(transcodeResponse);
	}
	
	/**
	 * 查询一条
	 * @param task 任务名
	 * @return DouTranslate
	 */
	@Override
	public DouTranslate findByTask(String task) {
		LambdaQueryWrapper<DouTranslate> lqw = getLqw();
		return getOne(lqw);
	}
	
	/**
	 * 获取集合
	 */
	@Override
	public PageResult<TranscodeResponse> getListByUser(DouTranslate douTranslateRequest,PageParamRequest pageParamRequest) {
		PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
		Integer type = douTranslateRequest.getType();
		if(Objects.isNull(type)) type = 1;
		LambdaQueryWrapper<DouTranslate> lqw = getLqw();
		lqw.eq(DouTranslate::getType,type);
		lqw.orderByDesc(DouTranslate::getId);
		List<DouTranslate> list = list(lqw);
		PageInfo<DouTranslate> pageInfo = new PageInfo<>(list);
		ArrayList<TranscodeResponse> transcodeResponseList = new ArrayList<>();
		for (DouTranslate douTranslate : list) {
			TranscodeResponse transcodeResponse = new TranscodeResponse();
			BeanUtils.copyProperties(douTranslate, transcodeResponse);
			transcodeResponse.setStats(douTranslate.getStatus());
			transcodeResponse.setTime(douTranslate.getTargetTime());
			transcodeResponseList.add(transcodeResponse);
		}
		PageResult<TranscodeResponse> transcodeResponsePageInfo = PageResult.restPage(pageInfo, transcodeResponseList);
		return transcodeResponsePageInfo;
	}
	
	/**
	 * 插入一条
	 *
	 * @param transcodeResponse
	 * @return
	 */
	@Override
	public DouTranslate insertOne(DouTranslate douTranslate, TranscodeResponse transcodeResponse) {
		LoginDouUser loginDouUser = SecurityUtils.getLoginDouUser();
		DouUser douUser = loginDouUser.getUser();
		douTranslate.setUid(douUser.getId());
		douTranslate.setCreateTime(new Date());
		douTranslate.setUpdateTime(new Date());
		douTranslate.setStatus(transcodeResponse.getStats());
		douTranslate.setTask(transcodeResponse.getTask());
		douTranslate.setTargetTime(transcodeResponse.getTime());
		save(douTranslate);
		return douTranslate;
	}
	private LambdaQueryWrapper<DouTranslate> getLqw(){
		Long id = SecurityUtils.getLoginDouUser().getUser().getId();
		LambdaQueryWrapper<DouTranslate> lqw = new LambdaQueryWrapper<>();
		lqw.eq(DouTranslate::getUid, id);
		return lqw;
	}
	/**
	 * 查询视频转码
	 *
	 * @param id 视频转码主键
	 * @return 视频转码
	 */
	@Override
	public DouTranslate selectDouTranslateById(Long id) {
		return douTranslateMapper.selectDouTranslateById(id);
	}
	
	/**
	 * 查询视频转码列表
	 *
	 * @param douTranslate 视频转码
	 * @return 视频转码
	 */
	@Override
	public List<DouTranslate> selectDouTranslateList(DouTranslate douTranslate) {
		return douTranslateMapper.selectDouTranslateList(douTranslate);
	}
	
	/**
	 * 新增视频转码
	 *
	 * @param douTranslate 视频转码
	 * @return 结果
	 */
	@Override
	public int insertDouTranslate(DouTranslate douTranslate) {
		return douTranslateMapper.insertDouTranslate(douTranslate);
	}
	
	/**
	 * 修改视频转码
	 *
	 * @param douTranslate 视频转码
	 * @return 结果
	 */
	@Override
	public int updateDouTranslate(DouTranslate douTranslate) {
		return douTranslateMapper.updateDouTranslate(douTranslate);
	}
	
	/**
	 * 批量删除视频转码
	 *
	 * @param ids 需要删除的视频转码主键
	 * @return 结果
	 */
	@Override
	public int deleteDouTranslateByIds(Long[] ids) {
		return douTranslateMapper.deleteDouTranslateByIds(ids);
	}
	
	/**
	 * 删除视频转码信息
	 *
	 * @param id 视频转码主键
	 * @return 结果
	 */
	@Override
	public int deleteDouTranslateById(Long id) {
		return douTranslateMapper.deleteDouTranslateById(id);
	}
}
