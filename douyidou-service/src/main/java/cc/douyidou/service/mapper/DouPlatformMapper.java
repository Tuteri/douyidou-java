package cc.douyidou.service.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cc.douyidou.service.domain.DouPlatform;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支持的平台Mapper接口
 * 
 * @author Tuteri
 * @date 2025-03-23
 */
@Mapper
public interface DouPlatformMapper extends BaseMapper<DouPlatform>
{
    /**
     * 查询支持的平台
     * 
     * @param id 支持的平台主键
     * @return 支持的平台
     */
    public DouPlatform selectDouPlatformById(Long id);

    /**
     * 查询支持的平台列表
     * 
     * @param douPlatform 支持的平台
     * @return 支持的平台集合
     */
    public List<DouPlatform> selectDouPlatformList(DouPlatform douPlatform);

    /**
     * 新增支持的平台
     * 
     * @param douPlatform 支持的平台
     * @return 结果
     */
    public int insertDouPlatform(DouPlatform douPlatform);

    /**
     * 修改支持的平台
     * 
     * @param douPlatform 支持的平台
     * @return 结果
     */
    public int updateDouPlatform(DouPlatform douPlatform);

    /**
     * 删除支持的平台
     * 
     * @param id 支持的平台主键
     * @return 结果
     */
    public int deleteDouPlatformById(Long id);

    /**
     * 批量删除支持的平台
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDouPlatformByIds(Long[] ids);
}
