package cc.douyidou.service.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cc.douyidou.service.domain.DouParse;
import org.apache.ibatis.annotations.Mapper;

/**
 * 视频解析记录Mapper接口
 * 
 * @author Tuteri
 * @date 2025-03-23
 */
@Mapper
public interface DouParseMapper extends BaseMapper<DouParse>
{
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
     * 删除视频解析记录
     * 
     * @param id 视频解析记录主键
     * @return 结果
     */
    public int deleteDouParseById(Long id);

    /**
     * 批量删除视频解析记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDouParseByIds(Long[] ids);
}
