package cc.douyidou.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cc.douyidou.service.domain.DouTranslate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 视频转码Mapper接口
 * 
 * @author Tuteri
 * @date 2025-03-20
 */
@Mapper
public interface DouTranslateMapper extends BaseMapper<DouTranslate>
{
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
     * 删除视频转码
     *
     * @param id 视频转码主键
     * @return 结果
     */
    public int deleteDouTranslateById(Long id);

    /**
     * 批量删除视频转码
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDouTranslateByIds(Long[] ids);
}
