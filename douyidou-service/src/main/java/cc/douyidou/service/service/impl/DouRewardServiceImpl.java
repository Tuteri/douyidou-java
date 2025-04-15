package cc.douyidou.service.service.impl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cc.douyidou.common.utils.DateUtils;
import cc.douyidou.service.domain.DouUser;
import cc.douyidou.service.exception.DouException;
import cc.douyidou.service.mapper.DouUserMapper;
import cc.douyidou.service.utils.SecurityUtils;
import cc.douyidou.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.douyidou.service.mapper.DouRewardMapper;
import cc.douyidou.service.domain.DouReward;
import cc.douyidou.service.service.IDouRewardService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 激励广告奖励Service业务层处理
 *
 * @author Tuteri
 * @date 2025-04-04
 */
@Service
public class DouRewardServiceImpl extends ServiceImpl<DouRewardMapper, DouReward> implements IDouRewardService {
	@Autowired
	private DouRewardMapper douRewardMapper;
	@Autowired
	private DouUserMapper douUserMapper;
	@Autowired
	private ISysConfigService sysConfigService;
	
	/**
	 * 奖励下发
	 *
	 * @param douRewardRequest 请求体
	 * @return 激励广告奖励
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public DouReward sendReward(DouReward douRewardRequest) {
		Boolean adRewardTokensStatus = Boolean.valueOf(sysConfigService.selectConfigByKey("routine.adRewardTokensStatus"));
		Boolean adRewardParseNumStatus = Boolean.valueOf(sysConfigService.selectConfigByKey("routine.adRewardParseNumStatus"));
		Integer adRewardTokens = Integer.valueOf(sysConfigService.selectConfigByKey("routine.adRewardTokens"));
		Integer adRewardParseNum = Integer.valueOf(sysConfigService.selectConfigByKey("routine.adRewardParseNum"));
		Boolean shareNumStatus = Boolean.valueOf(sysConfigService.selectConfigByKey("routine.shareNumStatus"));
		Integer shareNum = Integer.valueOf(sysConfigService.selectConfigByKey("routine.shareNum"));
		Integer shareNumTokens = Integer.valueOf(sysConfigService.selectConfigByKey("routine.shareNumTokens"));
		DouUser user = SecurityUtils.getLoginDouUser().getUser();
		DouReward douRewardLast = findLastByUser();
		if (ObjectUtil.isNotNull(douRewardLast)) {
			Date createTime = douRewardLast.getCreateTime();
			Date date = DateUtil.date();
			// 判断是否在10秒内 防止频繁奖励下发
			if (DateUtil.between(createTime, date, DateUnit.SECOND) <= 10) {
				throw new DouException("系统繁忙，请稍后再试");
			}
		}
		String adId = douRewardRequest.getAdId();
		Integer type = douRewardRequest.getType();
		String source = douRewardRequest.getSource();
		DouReward douReward = new DouReward();
		//类型 0未知 1解析 2下载 3转码 4转码下载 10主动观看广告 11分享
		
		if (type == 10) {
			// 已开启tokens奖励
			if (adRewardTokensStatus) {
				douReward.setTokens(adRewardTokens);
				douReward.setProactive(1);
				DouUser douUser = new DouUser();
				douUser.setId(user.getId());
				douUser.setTokens(user.getTokens() + adRewardTokens);
				douUserMapper.updateById(douUser);
			} else {
				return null;
			}
		} else if (type == 11) {
			// 开启分享
			if (shareNumStatus) {
				// 分享上限
				if (dayCountByUser() > shareNum) {
					return null;
				}
				douReward.setTokens(shareNumTokens);
				douReward.setProactive(1);
				DouUser douUser = new DouUser();
				douUser.setId(user.getId());
				douUser.setTokens(user.getTokens() + shareNumTokens);
				douUserMapper.updateById(douUser);
			} else {
				return null;
			}
			
		} else if (type == 1 || type == 2 || type == 3 || type == 4) {
			douReward.setProactive(0);
			// 已开启次数奖励
			if (adRewardParseNumStatus) {
				douReward.setParseNum(adRewardParseNum);
				DouUser douUser = new DouUser();
				douUser.setId(user.getId());
				douUser.setParseNumTemp(adRewardParseNum);
				douUserMapper.updateById(douUser);
			}
		}else {
			return null;
		}
		douReward.setAdId(adId);
		douReward.setType(type);
		douReward.setSource(source);
		douReward.setUid(SecurityUtils.getLoginDouUserId());
		douReward.setCreateTime(DateUtil.date());
		save(douReward);
		return douReward;
	}
	
	/**
	 * 统计今日分享次数
	 *
	 * @return Integer
	 */
	@Override
	public Long dayCountByUser() {
		LambdaQueryWrapper<DouReward> lqw = getLqw();
		DateTime date = DateUtil.date();
		lqw.eq(DouReward::getType, 11)
				.eq(DouReward::getProactive, 1)
				.between(DouReward::getCreateTime, DateUtil.beginOfDay(date), DateUtil.endOfDay(date));
		return count(lqw);
	}
	
	/**
	 * 查询是否存在存活广告奖励
	 *
	 * @return 激励广告奖励
	 */
	@Override
	public DouReward findLastByUser() {
		LambdaQueryWrapper<DouReward> lqw = getLqw();
		Integer adInterval = Integer.valueOf(sysConfigService.selectConfigByKey("routine.adRewardInterval")); // 秒
		LocalDateTime expireBefore = LocalDateTime.now().minusSeconds(adInterval);
		lqw.gt(DouReward::getCreateTime, DateUtil.date(expireBefore))
				.eq(DouReward::getProactive, 0);
		lqw.orderByDesc(DouReward::getId);
		lqw.last("limit 1");
		return getOne(lqw);
	}
	
	private LambdaQueryWrapper<DouReward> getLqw() {
		Long id = SecurityUtils.getLoginDouUser().getUser().getId();
		LambdaQueryWrapper<DouReward> lqw = new LambdaQueryWrapper<>();
		lqw.eq(DouReward::getUid, id);
		return lqw;
	}
	
	/**
	 * 查询激励广告奖励
	 *
	 * @param id 激励广告奖励主键
	 * @return 激励广告奖励
	 */
	@Override
	public DouReward selectDouRewardById(Long id) {
		return douRewardMapper.selectDouRewardById(id);
	}
	
	/**
	 * 查询激励广告奖励列表
	 *
	 * @param douReward 激励广告奖励
	 * @return 激励广告奖励
	 */
	@Override
	public List<DouReward> selectDouRewardList(DouReward douReward) {
		return douRewardMapper.selectDouRewardList(douReward);
	}
	
	/**
	 * 新增激励广告奖励
	 *
	 * @param douReward 激励广告奖励
	 * @return 结果
	 */
	@Override
	public int insertDouReward(DouReward douReward) {
		douReward.setCreateTime(DateUtils.getNowDate());
		return douRewardMapper.insertDouReward(douReward);
	}
	
	/**
	 * 修改激励广告奖励
	 *
	 * @param douReward 激励广告奖励
	 * @return 结果
	 */
	@Override
	public int updateDouReward(DouReward douReward) {
		return douRewardMapper.updateDouReward(douReward);
	}
	
	/**
	 * 批量删除激励广告奖励
	 *
	 * @param ids 需要删除的激励广告奖励主键
	 * @return 结果
	 */
	@Override
	public int deleteDouRewardByIds(Long[] ids) {
		return douRewardMapper.deleteDouRewardByIds(ids);
	}
	
	/**
	 * 删除激励广告奖励信息
	 *
	 * @param id 激励广告奖励主键
	 * @return 结果
	 */
	@Override
	public int deleteDouRewardById(Long id) {
		return douRewardMapper.deleteDouRewardById(id);
	}
}
