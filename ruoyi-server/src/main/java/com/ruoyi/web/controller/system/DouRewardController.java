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
import com.ruoyi.service.domain.DouReward;
import com.ruoyi.service.service.IDouRewardService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 激励广告奖励Controller
 * 
 * @author Tuteri
 * @date 2025-04-04
 */
@RestController
@RequestMapping("/dou/reward")
public class DouRewardController extends BaseController
{
    @Autowired
    private IDouRewardService douRewardService;

    /**
     * 查询激励广告奖励列表
     */
    @PreAuthorize("@ss.hasPermi('dou:reward:list')")
    @GetMapping("/list")
    public TableDataInfo list(DouReward douReward)
    {
        startPage();
        List<DouReward> list = douRewardService.selectDouRewardList(douReward);
        return getDataTable(list);
    }

    /**
     * 导出激励广告奖励列表
     */
    @PreAuthorize("@ss.hasPermi('dou:reward:export')")
    @Log(title = "激励广告奖励", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DouReward douReward)
    {
        List<DouReward> list = douRewardService.selectDouRewardList(douReward);
        ExcelUtil<DouReward> util = new ExcelUtil<DouReward>(DouReward.class);
        util.exportExcel(response, list, "激励广告奖励数据");
    }

    /**
     * 获取激励广告奖励详细信息
     */
    @PreAuthorize("@ss.hasPermi('dou:reward:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(douRewardService.selectDouRewardById(id));
    }

    /**
     * 新增激励广告奖励
     */
    @PreAuthorize("@ss.hasPermi('dou:reward:add')")
    @Log(title = "激励广告奖励", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DouReward douReward)
    {
        return toAjax(douRewardService.insertDouReward(douReward));
    }

    /**
     * 修改激励广告奖励
     */
    @PreAuthorize("@ss.hasPermi('dou:reward:edit')")
    @Log(title = "激励广告奖励", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DouReward douReward)
    {
        return toAjax(douRewardService.updateDouReward(douReward));
    }

    /**
     * 删除激励广告奖励
     */
    @PreAuthorize("@ss.hasPermi('dou:reward:remove')")
    @Log(title = "激励广告奖励", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(douRewardService.deleteDouRewardByIds(ids));
    }
}
