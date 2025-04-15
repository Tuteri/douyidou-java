package cc.douyidou.service.common;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageResult<T> {
	private Integer page = 1;
	private Integer nextPage = 2;
	private Integer limit = 20;
	private Integer totalPage = 0;
	private Long total = 0L ;
	private List<T> list = new ArrayList<>();
	
	
	/**
	 * 将PageHelper分页后的 PageInfo 转为分页信息
	 * @return
	 */
	public static <T> PageResult<T> restPage(PageInfo<?> pageInfo, List<T> list) {
		PageResult<T> result = new PageResult<>();
		result.setTotalPage(pageInfo.getPages());
		result.setPage(pageInfo.getPageNum());
		result.setNextPage(pageInfo.getNextPage());
		result.setLimit(pageInfo.getPageSize());
		result.setTotal(pageInfo.getTotal());
		result.setList(list);
		return result;
	}

	/**
	 * 对象A复制对象B的分页信息 // 多次数据查询导致分页数据异常解决办法
	 */
	public static <T> PageInfo<T> copyPageInfo(PageInfo<?> originPageInfo, List<T> list) {
		PageInfo<T> pageInfo = new PageInfo<>(list);
		pageInfo.setPages(originPageInfo.getPages());
		pageInfo.setNextPage(originPageInfo.getNextPage());
		pageInfo.setPageNum(originPageInfo.getPageNum());
		pageInfo.setPageSize(originPageInfo.getPageSize());
		pageInfo.setTotal(originPageInfo.getTotal());
		pageInfo.setList(list);
		return pageInfo;
	}
}
