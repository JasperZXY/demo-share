package org.ruanwei.demo.springframework.web.core;

import javax.validation.constraints.Min;


public class Page {
	@Min(value = 1, message = "{paging.pageNo.error}")
	private int curPage = 1;
	@Min(value = 1, message = "{paging.pageSize.error}")
	private int pageSize = 10;
	private long totalPage;
	private long totalRecord;

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

	public long getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
		this.totalPage = totalRecord % pageSize == 0 ? totalRecord / pageSize
				: totalRecord / pageSize + 1;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Page [curPage=").append(curPage).append(", pageSize=")
				.append(pageSize).append(", totalPage=").append(totalPage)
				.append(", totalRecord=").append(totalRecord).append("]");
		return builder.toString();
	}
}
