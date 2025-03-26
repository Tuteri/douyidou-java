package com.ruoyi.service.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.service.common.response.user.DouUserResponse;
import com.ruoyi.service.domain.DouUser;
import com.ruoyi.service.entity.LoginDouUser;
import com.ruoyi.service.service.IDouUserService;
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
	
	
	@Override
	public DouUser findByWxOpenid(String openid) {
		LambdaQueryWrapper<DouUser> lqw = new LambdaQueryWrapper<>();
		lqw.eq(DouUser::getWxOpenid, openid);
		return this.getOne(lqw);
	}
	
	@Override
	public DouUserResponse getInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		LoginDouUser loginDouUser = (LoginDouUser) authentication.getPrincipal();
		DouUser douUser = this.findByWxOpenid(loginDouUser.getWxOpenid());
		DouUserResponse douUserResponse = new DouUserResponse();
		BeanUtils.copyProperties(douUser, douUserResponse);
		return douUserResponse;
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
