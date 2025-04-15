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
import cc.douyidou.service.domain.DouParse;
import cc.douyidou.service.service.IDouParseService;
import cc.douyidou.common.utils.poi.ExcelUtil;
import cc.douyidou.common.core.page.TableDataInfo;

/**
 * 视频解析记录Controller
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc  保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
 */
@RestController
@RequestMapping("/dou/parse")
public class DouParseController extends BaseController
{
    @Autowired
    private IDouParseService douParseService;

    /**
     * 查询视频解析记录列表
     */
    @PreAuthorize("@ss.hasPermi('dou:parse:list')")
    @GetMapping("/list")
    public TableDataInfo list(DouParse douParse)
    {
        startPage();
        List<DouParse> list = douParseService.selectDouParseList(douParse);
        return getDataTable(list);
    }

    /**
     * 导出视频解析记录列表
     */
    @PreAuthorize("@ss.hasPermi('dou:parse:export')")
    @Log(title = "视频解析记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DouParse douParse)
    {
        List<DouParse> list = douParseService.selectDouParseList(douParse);
        ExcelUtil<DouParse> util = new ExcelUtil<DouParse>(DouParse.class);
        util.exportExcel(response, list, "视频解析记录数据");
    }

    /**
     * 获取视频解析记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('dou:parse:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(douParseService.selectDouParseById(id));
    }

    /**
     * 新增视频解析记录
     */
    @PreAuthorize("@ss.hasPermi('dou:parse:add')")
    @Log(title = "视频解析记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DouParse douParse)
    {
        return toAjax(douParseService.insertDouParse(douParse));
    }

    /**
     * 修改视频解析记录
     */
    @PreAuthorize("@ss.hasPermi('dou:parse:edit')")
    @Log(title = "视频解析记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DouParse douParse)
    {
        return toAjax(douParseService.updateDouParse(douParse));
    }

    /**
     * 删除视频解析记录
     */
    @PreAuthorize("@ss.hasPermi('dou:parse:remove')")
    @Log(title = "视频解析记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(douParseService.deleteDouParseByIds(ids));
    }
}
