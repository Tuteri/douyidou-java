package cc.douyidou.web.controller.system;

import java.util.List;

import cc.douyidou.common.utils.poi.ExcelUtil;
import cc.douyidou.service.domain.DouUser;
import cc.douyidou.service.service.IDouUserService;
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
import cc.douyidou.common.core.page.TableDataInfo;

/**
 * 应用用户Controller
 * @probject douyidou
 * @author Tuteri
 * @date 2025/04/16
 * 版权所有 © 2025 douyidou.cc  保留所有权利。
 * 本程序仅供学习与测试使用，禁止商用。
 */
@RestController
@RequestMapping("/dou/user")
public class DouUserController extends BaseController
{
    @Autowired
    private IDouUserService douUserService;

    /**
     * 查询应用用户列表
     */
    @PreAuthorize("@ss.hasPermi('dou:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(DouUser douUser)
    {
        startPage();
        List<DouUser> list = douUserService.selectDouUserList(douUser);
        return getDataTable(list);
    }

    /**
     * 导出应用用户列表
     */
    @PreAuthorize("@ss.hasPermi('dou:user:export')")
    @Log(title = "应用用户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DouUser douUser)
    {
        List<DouUser> list = douUserService.selectDouUserList(douUser);
        ExcelUtil<DouUser> util = new ExcelUtil<DouUser>(DouUser.class);
        util.exportExcel(response, list, "应用用户数据");
    }

    /**
     * 获取应用用户详细信息
     */
    @PreAuthorize("@ss.hasPermi('dou:user:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(douUserService.selectDouUserById(id));
    }

    /**
     * 新增应用用户
     */
    @PreAuthorize("@ss.hasPermi('dou:user:add')")
    @Log(title = "应用用户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DouUser douUser)
    {
        return toAjax(douUserService.insertDouUser(douUser));
    }

    /**
     * 修改应用用户
     */
    @PreAuthorize("@ss.hasPermi('dou:user:edit')")
    @Log(title = "应用用户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DouUser douUser)
    {
        System.out.println(douUser);
        return toAjax(douUserService.updateDouUser(douUser));
    }

    /**
     * 删除应用用户
     */
    @PreAuthorize("@ss.hasPermi('dou:user:remove')")
    @Log(title = "应用用户", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(douUserService.deleteDouUserByIds(ids));
    }
}
