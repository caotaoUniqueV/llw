package com.linwang.uitls;

import com.google.common.collect.Lists;

public class PageUtil {
	private final static int DEF_PAGE_SIZE = 10;// 默认当前页的容量

	/**
	 * 功能:传入查询前初始化的page实例,创建一个新的Page实例
	 * 
	 * @param page
	 * @param totalRow
	 * @return
	 */
	public static <T> Page<T> createPage(Page<T> page, int totalRow) {
	    Lists.newArrayList();
		return createPage(page.getCondition(), page.getUrl(), page.getParam(),
				page.getPageSize(), page.getCurPage(), totalRow);
	}

	public static <T> Page<T> createPage(Condition condition, String url,
			String param, int pageSize, int curPage, int totalRow) {
		pageSize = getpageSize(pageSize);
		curPage = getcurPage(curPage);
		int beginIndex = getBeginIndex(pageSize, curPage);
		int totalPage = getTotalPage(pageSize, totalRow);
		boolean hasNextPage = hasNextPage(curPage, totalPage);
		boolean hasPrePage = hasPrePage(curPage);
		Page<T> page = new Page<T>(hasPrePage, hasNextPage, pageSize, totalPage, totalRow,
				curPage, beginIndex, condition,url);
		page.setParam(param);
		return page;
	}

	private static int getpageSize(int pageSize) {
		return pageSize == 0 ? DEF_PAGE_SIZE : pageSize;
	}

	private static int getcurPage(int curPage) {
		return curPage == 0 ? 1 : curPage;
	}

	private static int getBeginIndex(int pageSize, int curPage) {
		return (curPage - 1) * pageSize;
	}

	private static int getTotalPage(int pageSize, int totalRow) {
		int totalPage = 0;
		if (totalRow % pageSize == 0)
			totalPage = totalRow / pageSize;
		else
			totalPage = totalRow / pageSize + 1;
		return totalPage;
	}

	private static boolean hasPrePage(int curPage) {
		return curPage == 1 ? false : true;
	}

	private static boolean hasNextPage(int curPage, int totalPage) {
		return curPage == totalPage || totalPage == 0 ? false : true;
	}
}
