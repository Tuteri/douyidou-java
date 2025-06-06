package cc.douyidou.service.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cc.douyidou.service.domain.DouUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 应用用户Mapper接口
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc 保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
 */
@Mapper
public interface DouUserMapper extends BaseMapper<DouUser>
{
    /**
     * 查询应用用户
     *
     * @param id 应用用户主键
     * @return 应用用户
     */
    public DouUser selectDouUserById(Long id);

    /**
     * 查询应用用户列表
     *
     * @param douUser 应用用户
     * @return 应用用户集合
     */
    public List<DouUser> selectDouUserList(DouUser douUser);

    /**
     * 新增应用用户
     *
     * @param douUser 应用用户
     * @return 结果
     */
    public int insertDouUser(DouUser douUser);

    /**
     * 修改应用用户
     *
     * @param douUser 应用用户
     * @return 结果
     */
    public int updateDouUser(DouUser douUser);

    /**
     * 删除应用用户
     *
     * @param id 应用用户主键
     * @return 结果
     */
    public int deleteDouUserById(Long id);

    /**
     * 批量删除应用用户
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDouUserByIds(Long[] ids);
}
