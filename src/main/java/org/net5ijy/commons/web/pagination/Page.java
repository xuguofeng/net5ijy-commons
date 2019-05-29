package org.net5ijy.commons.web.pagination;

import lombok.Data;

/**
 * org.net5ijy.springcloud.admin.service.util.Page 类
 *
 * @author xuguofeng
 * @date 2019/5/29 10:03
 */
@Data
public class Page {

	private int page;

	private int size;

	private int firstRow;

	public Page() {
		super();
	}

	public Page(int page, int size) {
		this();
		this.page = page;
		this.size = size;
	}
}
