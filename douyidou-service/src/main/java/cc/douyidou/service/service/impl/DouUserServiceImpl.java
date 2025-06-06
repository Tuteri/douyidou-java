package cc.douyidou.service.service.impl;

import java.util.List;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cc.douyidou.service.common.response.CommonResult;
import cc.douyidou.service.common.response.user.DouUserResponse;
import cc.douyidou.service.domain.DouReward;
import cc.douyidou.service.domain.DouUser;
import cc.douyidou.service.entity.LoginDouUser;
import cc.douyidou.service.service.IDouRewardService;
import cc.douyidou.service.service.IDouUserService;
import cc.douyidou.service.utils.SecurityUtils;
import cc.douyidou.system.service.ISysConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import cc.douyidou.service.mapper.DouUserMapper;

/**
 * 应用用户Service业务层处理
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc  保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
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
	public Boolean parseNumConsumer() {
		return parseNumConsumer(1);
	}
	/**
	 * parseNumConsumer -parseNum 常用于解析/下载/转码等
	 * @return 应用用户
	 */
	@Override
	public Boolean parseNumConsumer(Integer parseNum) {
		if(parseNum < 0) return false;
		DouUser user = SecurityUtils.getLoginDouUser().getUser();
		DouUser updateDouUser = new DouUser();
		int temp = user.getParseNumTemp();
		int perm = user.getParseNum();
		if (parseNum <= temp) {
			// 临时点数足够
			updateDouUser.setParseNumTemp(temp - parseNum);
		} else if (parseNum <= temp + perm) {
			// 临时点数不够，但总点数足够，混合扣除
			int fromTemp = temp;
			int fromPerm = parseNum - fromTemp;
			updateDouUser.setParseNumTemp(0);
			updateDouUser.setParseNum(perm - fromPerm);
		} else {
			// 总点数不足
			return false;
		}
		updateDouUser.setId(user.getId());
		updateDouUser.setUpdateTime(DateUtil.date());
		updateById(updateDouUser);
		return true;
	}
	
	/**
	 * 获取个人信息
	 * @return
	 */
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
	public boolean checkParse() {
		return checkParse(1);
	}
	/**
	 * 用户解析、保存、转码判断
	 */
	@Override
	public boolean checkParse(Integer parseNum) {
		DouUser douUser = SecurityUtils.getLoginDouUser().getUser();
		// 读取配置
		// true:在{广告间隔时长}内，{广告奖励次数}次数为0，则需要观看广告
		// false:在{广告间隔时长}内，则无需观看广告，此时{广告奖励次数}不生效
		Boolean adRewardParseNumStatus = Boolean.valueOf(sysConfigService.selectConfigByKey("routine.adRewardParseNumStatus"));
		Boolean adParseStatus = Boolean.valueOf(sysConfigService.selectConfigByKey("routine.adParseStatus"));
		// 不是会员 并且 开启了解析广告
		if (douUser.getLevel() == 0 && adParseStatus) {
			// 有存活奖励
			if (ObjectUtil.isNotNull(douRewardService.findLastByUser())) {
				// 开启了广告次数控制
				if (adRewardParseNumStatus) {
					if (douUser.getParseNumTemp() >= parseNum || douUser.getParseNum() >= parseNum) {
						return parseNumConsumer(parseNum);
					}  else {
						return false;
					}
				}
				// 未开启 可直接解析 执行后续代码
				
			} else {
				// 没有存活奖励
				if (douUser.getParseNum() >= parseNum) {
					return parseNumConsumer(parseNum);
				} else {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * tokens兑换parseNum
	 *
	 * @param douUserRequest 请求
	 * @return CommonResult<Object>
	 */
	@Override
	public CommonResult<Object> tokensToParseNum(DouUser douUserRequest) {
		// 兑换的次数
		Integer parseNum = douUserRequest.getParseNum();
		if(parseNum<0){
			return CommonResult.failed("无效的请求");
		}
		DouUser user = SecurityUtils.getLoginDouUser().getUser();
		Integer tokens = user.getTokens();
		// 解析比例
		Integer tokensToParseNum = Integer.valueOf(sysConfigService.selectConfigByKey("routine.tokensToParseNum"));
		// 消费tokens
		var consumerTokens = parseNum * tokensToParseNum;
		if(tokens < parseNum * tokensToParseNum) {
			return CommonResult.failed("可用积分不足");
		}
		user.setTokens(user.getTokens()-consumerTokens);
		user.setParseNum(user.getParseNum()+parseNum);
		updateById(user);
		return CommonResult.success("兑换成功");
	}
	
	/**
	 * 用户清空 parseNumTemp Task
	 */
	@Override
	public void clearPntTask() {
		LambdaQueryWrapper<DouUser> lqw = new LambdaQueryWrapper<>();
		lqw.gt(DouUser::getParseNumTemp, 0)
				.eq(DouUser::getLevel, 0);
		List<DouUser> list = list(lqw);
		list.forEach(user -> {
			if(ObjectUtil.isNull(douRewardService.findLastByUserId(user.getId()))){
				DouUser updateDouUser = new DouUser();
				updateDouUser.setParseNumTemp(0);
				updateDouUser.setId(user.getId());
				updateDouUser.setUpdateTime(DateUtil.date());
				updateById(updateDouUser);
			}
		});
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
