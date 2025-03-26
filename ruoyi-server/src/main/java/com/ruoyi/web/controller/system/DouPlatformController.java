package com.ruoyi.web.controller.system;

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
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.service.domain.DouPlatform;
import com.ruoyi.service.service.IDouPlatformService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 支持的平台Controller
 * 
 * @author Tuteri
 * @date 2025-03-23
 */
@RestController
@RequestMapping("/dou/platform")
public class DouPlatformController extends BaseController
{
    @Autowired
    private IDouPlatformService douPlatformService;

    /**
     * 查询支持的平台列表
     */
    @PreAuthorize("@ss.hasPermi('dou:platform:list')")
    @GetMapping("/list")
    public TableDataInfo list(DouPlatform douPlatform)
    {
        startPage();
        List<DouPlatform> list = douPlatformService.selectDouPlatformList(douPlatform);
        return getDataTable(list);
    }

    /**
     * 导出支持的平台列表
     */
    @PreAuthorize("@ss.hasPermi('dou:platform:export')")
    @Log(title = "支持的平台", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DouPlatform douPlatform)
    {
        List<DouPlatform> list = douPlatformService.selectDouPlatformList(douPlatform);
        ExcelUtil<DouPlatform> util = new ExcelUtil<DouPlatform>(DouPlatform.class);
        util.exportExcel(response, list, "支持的平台数据");
    }

    /**
     * 获取支持的平台详细信息
     */
    @PreAuthorize("@ss.hasPermi('dou:platform:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(douPlatformService.selectDouPlatformById(id));
    }

    /**
     * 新增支持的平台
     */
    @PreAuthorize("@ss.hasPermi('dou:platform:add')")
    @Log(title = "支持的平台", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DouPlatform douPlatform)
    {
        return toAjax(douPlatformService.insertDouPlatform(douPlatform));
    }

    /**
     * 修改支持的平台
     */
    @PreAuthorize("@ss.hasPermi('dou:platform:edit')")
    @Log(title = "支持的平台", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DouPlatform douPlatform)
    {
        return toAjax(douPlatformService.updateDouPlatform(douPlatform));
    }

    /**
     * 删除支持的平台
     */
    @PreAuthorize("@ss.hasPermi('dou:platform:remove')")
    @Log(title = "支持的平台", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(douPlatformService.deleteDouPlatformByIds(ids));
    }
}
