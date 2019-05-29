package org.net5ijy.commons.web.pagination;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * org.net5ijy.springcloud.admin.service.util.PageObject 类
 *
 * @author xuguofeng
 * @date 2019/5/29 10:06
 */
@Data
public class PageObject<T> implements Serializable {

	public static final long serialVersionUID = -5506114617636842116L;

	private List<T> rows;
	private int total;
	private int page;
	private int size;
	private int pageCount;

	public PageObject() {
		super();
	}

	public PageObject(List<T> rows, int total, int page, int size) {
		this();
		this.rows = rows;
		this.total = total;
		this.page = page;
		this.size = size;
		this.pageCount = total % size == 0 ? total / size : total / size + 1;
	}
}
