package cc.douyidou.service.service;

import java.util.List;
import cc.douyidou.service.domain.DouHelp;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 使用说明Service接口
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc 保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
 */
public interface IDouHelpService extends IService<DouHelp>
{
    /**
     * 获取帮助列表
     */
    public List<DouHelp> getList();
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
     * 批量删除使用说明
     * 
     * @param ids 需要删除的使用说明主键集合
     * @return 结果
     */
    public int deleteDouHelpByIds(Long[] ids);

    /**
     * 删除使用说明信息
     * 
     * @param id 使用说明主键
     * @return 结果
     */
    public int deleteDouHelpById(Long id);
}
