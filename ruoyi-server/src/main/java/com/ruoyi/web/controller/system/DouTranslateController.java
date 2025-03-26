package com.ruoyi.web.controller.system;

import java.util.List;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.service.domain.DouTranslate;
import com.ruoyi.service.service.IDouTranslateService;
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
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 视频转码Controller
 * 
 * @author Tuteri
 * @date 2025-03-20
 */
@RestController
@RequestMapping("/dou/translate")
public class DouTranslateController extends BaseController
{
    @Autowired
    private IDouTranslateService douTranslateService;

    /**
     * 查询视频转码列表
     */
    @PreAuthorize("@ss.hasPermi('dou:translate:list')")
    @GetMapping("/list")
    public TableDataInfo list(DouTranslate douTranslate)
    {
        startPage();
        List<DouTranslate> list = douTranslateService.selectDouTranslateList(douTranslate);
        return getDataTable(list);
    }

    /**
     * 导出视频转码列表
     */
    @PreAuthorize("@ss.hasPermi('dou:translate:export')")
    @Log(title = "视频转码", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DouTranslate douTranslate)
    {
        List<DouTranslate> list = douTranslateService.selectDouTranslateList(douTranslate);
        ExcelUtil<DouTranslate> util = new ExcelUtil<DouTranslate>(DouTranslate.class);
        util.exportExcel(response, list, "视频转码数据");
    }

    /**
     * 获取视频转码详细信息
     */
    @PreAuthorize("@ss.hasPermi('dou:translate:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(douTranslateService.selectDouTranslateById(id));
    }

    /**
     * 新增视频转码
     */
    @PreAuthorize("@ss.hasPermi('dou:translate:add')")
    @Log(title = "视频转码", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DouTranslate douTranslate)
    {
        return toAjax(douTranslateService.insertDouTranslate(douTranslate));
    }

    /**
     * 修改视频转码
     */
    @PreAuthorize("@ss.hasPermi('dou:translate:edit')")
    @Log(title = "视频转码", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DouTranslate douTranslate)
    {
        return toAjax(douTranslateService.updateDouTranslate(douTranslate));
    }

    /**
     * 删除视频转码
     */
    @PreAuthorize("@ss.hasPermi('dou:translate:remove')")
    @Log(title = "视频转码", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(douTranslateService.deleteDouTranslateByIds(ids));
    }
}
