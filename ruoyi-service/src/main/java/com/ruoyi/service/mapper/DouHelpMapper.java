package com.ruoyi.service.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.service.domain.DouHelp;
import org.apache.ibatis.annotations.Mapper;

/**
 * 使用说明Mapper接口
 * 
 * @author Tuteri
 * @date 2025-04-05
 */
@Mapper
public interface DouHelpMapper extends BaseMapper<DouHelp>
{
    /**
     * 查询使用说明
     * 
     * @param id 使用说明主键
     * @return 使用说明
     */
    public DouHelp selectDouHelpById(Long id);

    /**
     * 查询使用说明列表
     * 
     * @param douHelp 使用说明
     * @return 使用说明集合
     */
    public List<DouHelp> selectDouHelpList(DouHelp douHelp);

    /**
     * 新增使用说明
     * 
     * @param douHelp 使用说明
     * @return 结果
     */
    public int insertDouHelp(DouHelp douHelp);

    /**
     * 修改使用说明
     * 
     * @param douHelp 使用说明
     * @return 结果
     */
    public int updateDouHelp(DouHelp douHelp);

    /**
     * 删除使用说明
     * 
     * @param id 使用说明主键
     * @return 结果
     */
    public int deleteDouHelpById(Long id);

    /**
     * 批量删除使用说明
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDouHelpByIds(Long[] ids);
}
