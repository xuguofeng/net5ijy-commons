package org.net5ijy.commons.web;

import java.util.List;

/**
 * 封装分页结果集和分页信息，包括：<br />
 * <br />
 * 
 * 当前页码、每页数据量、总数据量、总页数、显示起始页、显示结束页<br />
 * <br />
 * 
 * @author xuguofeng
 * @param <T>
 *            - rows集合中保存的数据的类型
 */
public class PageResult<T> {

	private List<T> rows;

	private int pageNum = 1;

	private int pageSize = 10;

	private int total = 0;

	private int pageCount = 0;

	private int startPage = 1;

	private int endPage = 1;

	/**
	 * 创建一个分页结果<br />
	 * <br />
	 * 
	 * @param rows
	 *            - 数据集合
	 * @param pageNum
	 *            - 当前页码
	 * @param pageSize
	 *            - 每页数据量
	 * @param total
	 *            - 总数据量
	 * @param displayCount
	 *            - 总页数
	 */
	public PageResult(List<T> rows, int pageNum, int pageSize, int total,
			int displayCount) {

		// 如果传入的数据集合为null，抛出一个异常
		if (rows == null) {
			throw new IllegalArgumentException("不允许参数数据集合为null");
		}
		// 如果页码、单页数据量、总数据量或显示页码数中的任何一个为0，抛出一个异常
		if (pageNum <= 0 || pageSize <= 0 || total <= 0 || displayCount <= 0) {
			throw new IllegalArgumentException("不允许页码、单页数据量、总数据量或显示页码数为0");
		}

		this.rows = rows;
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.total = total;

		// 计算pageCount
		if (this.total % this.pageSize == 0) {
			this.pageCount = this.total / this.pageSize;
		} else {
			this.pageCount = this.total / this.pageSize + 1;
		}

		// 如果页码大于总页数，抛出一个异常
		if (this.pageNum > this.pageCount) {
			throw new IllegalArgumentException(String.format(
					"不允许页码大于总页数: [ %s > %s ]", this.pageNum, this.pageCount));
		}

		// 计算显示的开始、结束页码
		// 如果需要显示页码按钮数量 <= 总页数
		if (displayCount >= this.pageCount) {
			this.startPage = 1;
			this.endPage = this.pageCount;
		} else {

			// 用于判断需要显示的页码链接数量是奇数还是偶数
			int c = displayCount % 2;

			// 需要显示的页码链接数量是偶数
			if (c == 0) {
				this.startPage = this.pageNum - displayCount / 2 + 1;
				this.endPage = this.pageNum + displayCount / 2;
			} else {// 需要显示的页码链接数量是奇数
				this.startPage = this.pageNum - displayCount / 2;
				this.endPage = this.pageNum + displayCount / 2;
			}

			// 如果计算出来的startPage<=0
			// startPage = 1
			// endPage = 需要显示的页码链接数量
			if (this.startPage <= 0) {
				this.startPage = 1;
				this.endPage = displayCount;
			} else if (this.endPage > this.pageCount) {
				// 如果计算出来的endPage>总页数
				this.endPage = this.pageCount;
				this.startPage = this.pageCount - displayCount + 1;
			}
		}
	}

	/**
	 * 获取结果数据集合<br />
	 * <br />
	 * 
	 * @return
	 */
	public List<T> getRows() {
		return rows;
	}

	/**
	 * 获取当前页码<br />
	 * <br />
	 * 
	 * @return
	 */
	public int getPageNum() {
		return pageNum;
	}

	/**
	 * 获取每页数据量<br />
	 * <br />
	 * 
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 获取总数据量<br />
	 * <br />
	 * 
	 * @return
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * 获取总页数<br />
	 * <br />
	 * 
	 * @return
	 */
	public int getPageCount() {
		return pageCount;
	}

	/**
	 * 获取显示的起始页<br />
	 * <br />
	 * 
	 * @return
	 */
	public int getStartPage() {
		return startPage;
	}

	/**
	 * 获取显示的结束页<br />
	 * <br />
	 * 
	 * @return
	 */
	public int getEndPage() {
		return endPage;
	}
}
