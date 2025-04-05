package com.ruoyi.service.service.impl;

import java.util.List;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.service.common.response.user.DouUserResponse;
import com.ruoyi.service.domain.DouReward;
import com.ruoyi.service.domain.DouUser;
import com.ruoyi.service.entity.LoginDouUser;
import com.ruoyi.service.service.IDouRewardService;
import com.ruoyi.service.service.IDouUserService;
import com.ruoyi.service.utils.SecurityUtils;
import com.ruoyi.system.service.ISysConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.ruoyi.service.mapper.DouUserMapper;

/**
 * 应用用户Service业务层处理
 *
 * @author Tuteri
 * @date 2025-03-20
 */
@Service
public class DouUserServiceImpl extends ServiceImpl<DouUserMapper, DouUser> implements IDouUserService {
	@Autowired
	private DouUserMapper douUserMapper;
	@Autowired
	private IDouRewardService douRewardService;
	@Autowired
	private ISysConfigService sysConfigService;
	
	@Override
	public DouUser findByWxOpenid(String openid) {
		LambdaQueryWrapper<DouUser> lqw = new LambdaQueryWrapper<>();
		lqw.eq(DouUser::getWxOpenid, openid);
		return this.getOne(lqw);
	}
	
	/**
	 * parseNum消费 -1 常用于解析/下载/转码等
	 * @return 应用用户
	 */
	@Override
	public Boolean parseNumConsumer(Integer type) {
		DouUser user = SecurityUtils.getLoginDouUser().getUser();
		DouUser updateDouUser = new DouUser();
		System.out.println(type);
		System.out.println(user.getParseNumTemp());
		if(type == 1 && user.getParseNumTemp() > 0){
			updateDouUser.setParseNumTemp(user.getParseNumTemp() - 1);
		}else if(type == 2 && user.getParseNum() > 0){
			updateDouUser.setParseNum(user.getParseNum() - 1);
		}
		updateDouUser.setId(user.getId());
		updateDouUser.setUpdateTime(DateUtil.date());
		updateById(updateDouUser);
		return true;
	}
	
	@Override
	public DouUserResponse getInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		LoginDouUser loginDouUser = (LoginDouUser) authentication.getPrincipal();
		Boolean adRewardParseNumStatus = Boolean.valueOf(sysConfigService.selectConfigByKey("routine.adRewardParseNumStatus"));
		Boolean adParseStatus = Boolean.valueOf(sysConfigService.selectConfigByKey("routine.adParseStatus"));
		Boolean adSaveStatus = Boolean.valueOf(sysConfigService.selectConfigByKey("routine.adSaveStatus"));
		DouUser douUser = this.findByWxOpenid(loginDouUser.getWxOpenid());
		
		DouUserResponse douUserResponse = new DouUserResponse();
		BeanUtils.copyProperties(douUser, douUserResponse);
		//非会员，并且开启了解析广告
		if (douUser.getLevel() == 0 && adParseStatus) {
			DouReward douReward = douRewardService.findLastByUser();
			// 有存活奖励
			if (ObjectUtil.isNotNull(douReward)) {
				// 开启了广告次数控制
				if (adRewardParseNumStatus) {
					//广告可跳过
					if (douUser.getParseNumTemp() > 0 || douUser.getParseNum() > 0) {
						douUserResponse.setAdSkipParse(true);
					}
				}else{
					douUserResponse.setAdSkipParse(true);
				}
			} else {
				//广告可跳过
				if (douUser.getParseNum() > 0) {
					douUserResponse.setAdSkipParse(true);
				}
			}
		}else{
			douUserResponse.setAdSkipParse(true);
		}
		
		//非会员，并且开启了保存广告
		if (douUser.getLevel() == 0 && adSaveStatus) {
			DouReward douReward = douRewardService.findLastByUser();
			// 有存活奖励
			if (ObjectUtil.isNotNull(douReward)) {
				// 开启了广告次数控制
				if (adRewardParseNumStatus) {
					//广告可跳过
					if (douUser.getParseNumTemp() > 0 || douUser.getParseNum() > 0) {
						douUserResponse.setAdSkipSave(true);
					}
				}else{
					douUserResponse.setAdSkipSave(true);
				}
			} else {
				//广告可跳过
				if (douUser.getParseNum() > 0) {
					douUserResponse.setAdSkipSave(true);
				}
			}
		}else{
			douUserResponse.setAdSkipSave(true);
		}
		
		return douUserResponse;
	}
	/**
	 * 用户解析、保存、转码判断
	 */
	@Override
	public boolean checkParse(DouUser updateDouUser) {
		DouUser douUser = SecurityUtils.getLoginDouUser().getUser();
		// 读取配置
		// true:在{广告间隔时长}内，{广告奖励次数}次数为0，则需要观看广告
		// false:在{广告间隔时长}内，则无需观看广告，此时{广告奖励次数}不生效
		Boolean adRewardParseNumStatus = Boolean.valueOf(sysConfigService.selectConfigByKey("routine.adRewardParseNumStatus"));
		Boolean adParseStatus = Boolean.valueOf(sysConfigService.selectConfigByKey("routine.adParseStatus"));
		// 不是会员 并且 开启了解析广告
		if (douUser.getLevel() == 0 && adParseStatus) {
			DouReward douReward = douRewardService.findLastByUser();
			// 有存活奖励
			if (ObjectUtil.isNotNull(douReward)) {
				//Date createTime = douReward.getCreateTime();
				//小程序广告间隔时长，距上次广告adInterval
				//if (DateUtil.between(createTime, DateUtil.date(), DateUnit.SECOND) > adInterval) {
				
				// 开启了广告次数控制
				if (adRewardParseNumStatus) {
					if (douUser.getParseNumTemp() > 0) {
						parseNumConsumer(1);
					} else if (douUser.getParseNum() > 0) {
						parseNumConsumer(2);
					} else {
						return false;
					}
				}
				// 未开启 可直接解析 执行后续代码
				
			} else {
				// 没有存活奖励
				if (douUser.getParseNum() > 0) {
					parseNumConsumer(2);
				} else {
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * 用户看广告 奖励下发
	 */
	@Override
	public void reward() {
	
	}
	
	/**
	 * 查询应用用户
	 *
	 * @param id 应用用户主键
	 * @return 应用用户
	 */
	@Override
	public DouUser selectDouUserById(Long id) {
		return douUserMapper.selectDouUserById(id);
	}
	
	/**
	 * 查询应用用户列表
	 *
	 * @param douUser 应用用户
	 * @return 应用用户
	 */
	@Override
	public List<DouUser> selectDouUserList(DouUser douUser) {
		return douUserMapper.selectDouUserList(douUser);
	}
	
	/**
	 * 新增应用用户
	 *
	 * @param douUser 应用用户
	 * @return 结果
	 */
	@Override
	public int insertDouUser(DouUser douUser) {
		return douUserMapper.insertDouUser(douUser);
	}
	
	/**
	 * 修改应用用户
	 *
	 * @param douUser 应用用户
	 * @return 结果
	 */
	@Override
	public int updateDouUser(DouUser douUser) {
		return douUserMapper.updateDouUser(douUser);
	}
	
	/**
	 * 批量删除应用用户
	 *
	 * @param ids 需要删除的应用用户主键
	 * @return 结果
	 */
	@Override
	public int deleteDouUserByIds(Long[] ids) {
		return douUserMapper.deleteDouUserByIds(ids);
	}
	
	/**
	 * 删除应用用户信息
	 *
	 * @param id 应用用户主键
	 * @return 结果
	 */
	@Override
	public int deleteDouUserById(Long id) {
		return douUserMapper.deleteDouUserById(id);
	}
}
