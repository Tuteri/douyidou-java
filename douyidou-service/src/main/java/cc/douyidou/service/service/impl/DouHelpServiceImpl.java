package cc.douyidou.service.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import cc.douyidou.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.douyidou.service.mapper.DouHelpMapper;
import cc.douyidou.service.domain.DouHelp;
import cc.douyidou.service.service.IDouHelpService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 使用说明Service业务层处理
 * 
 * @author Tuteri
 * @date 2025-04-05
 */
@Service
public class DouHelpServiceImpl extends ServiceImpl<DouHelpMapper, DouHelp> implements IDouHelpService
{
    @Autowired
    private DouHelpMapper douHelpMapper;
    
    /**
     * 获取帮助列表
     */
    @Override
    public List<DouHelp> getList() {
        LambdaQueryWrapper<DouHelp> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DouHelp::getStatus, 0)
                .orderByDesc(DouHelp::getWeight);
        return list(lqw);
    }
    
    /**
     * 查询使用说明
     * 
     * @param id 使用说明主键
     * @return 使用说明
     */
    @Override
    public DouHelp selectDouHelpById(Long id)
    {
        return douHelpMapper.selectDouHelpById(id);
    }

    /**
     * 查询使用说明列表
     * 
     * @param douHelp 使用说明
     * @return 使用说明
     */
    @Override
    public List<DouHelp> selectDouHelpList(DouHelp douHelp)
    {
        return douHelpMapper.selectDouHelpList(douHelp);
    }

    /**
     * 新增使用说明
     * 
     * @param douHelp 使用说明
     * @return 结果
     */
    @Override
    public int insertDouHelp(DouHelp douHelp)
    {
        douHelp.setCreateTime(DateUtils.getNowDate());
        return douHelpMapper.insertDouHelp(douHelp);
    }

    /**
     * 修改使用说明
     * 
     * @param douHelp 使用说明
     * @return 结果
     */
    @Override
    public int updateDouHelp(DouHelp douHelp)
    {
        douHelp.setUpdateTime(DateUtils.getNowDate());
        return douHelpMapper.updateDouHelp(douHelp);
    }

    /**
     * 批量删除使用说明
     * 
     * @param ids 需要删除的使用说明主键
     * @return 结果
     */
    @Override
    public int deleteDouHelpByIds(Long[] ids)
    {
        return douHelpMapper.deleteDouHelpByIds(ids);
    }

    /**
     * 删除使用说明信息
     * 
     * @param id 使用说明主键
     * @return 结果
     */
    @Override
    public int deleteDouHelpById(Long id)
    {
        return douHelpMapper.deleteDouHelpById(id);
    }
}
