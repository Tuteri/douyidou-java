package cc.douyidou.web.controller.system;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cc.douyidou.common.annotation.Log;
import cc.douyidou.common.core.controller.BaseController;
import cc.douyidou.common.core.domain.AjaxResult;
import cc.douyidou.common.enums.BusinessType;
import cc.douyidou.service.domain.DouHelp;
import cc.douyidou.service.service.IDouHelpService;
import cc.douyidou.common.utils.poi.ExcelUtil;
import cc.douyidou.common.core.page.TableDataInfo;

/**
 * 使用说明Controller
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc  保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
 */
@RestController
@RequestMapping("/dou/help")
public class DouHelpController extends BaseController
{
    @Autowired
    private IDouHelpService douHelpService;

    /**
     * 查询使用说明列表
     */
    @PreAuthorize("@ss.hasPermi('dou:help:list')")
    @GetMapping("/list")
    public TableDataInfo list(DouHelp douHelp)
    {
        startPage();
        List<DouHelp> list = douHelpService.selectDouHelpList(douHelp);
        return getDataTable(list);
    }

    /**
     * 导出使用说明列表
     */
    @PreAuthorize("@ss.hasPermi('dou:help:export')")
    @Log(title = "使用说明", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DouHelp douHelp)
    {
        List<DouHelp> list = douHelpService.selectDouHelpList(douHelp);
        ExcelUtil<DouHelp> util = new ExcelUtil<DouHelp>(DouHelp.class);
        util.exportExcel(response, list, "使用说明数据");
    }

    /**
     * 获取使用说明详细信息
     */
    @PreAuthorize("@ss.hasPermi('dou:help:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(douHelpService.selectDouHelpById(id));
    }

    /**
     * 新增使用说明
     */
    @PreAuthorize("@ss.hasPermi('dou:help:add')")
    @Log(title = "使用说明", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DouHelp douHelp)
    {
        return toAjax(douHelpService.insertDouHelp(douHelp));
    }

    /**
     * 修改使用说明
     */
    @PreAuthorize("@ss.hasPermi('dou:help:edit')")
    @Log(title = "使用说明", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DouHelp douHelp)
    {
        return toAjax(douHelpService.updateDouHelp(douHelp));
    }

    /**
     * 删除使用说明
     */
    @PreAuthorize("@ss.hasPermi('dou:help:remove')")
    @Log(title = "使用说明", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(douHelpService.deleteDouHelpByIds(ids));
    }
}
