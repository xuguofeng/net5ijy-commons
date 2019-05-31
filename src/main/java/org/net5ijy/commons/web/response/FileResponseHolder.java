package org.net5ijy.commons.web.response;

import java.io.File;
import java.util.Arrays;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.http.Header;

@Data
@EqualsAndHashCode(callSuper = false)
public class FileResponseHolder extends ResponseHolder {

	private File file;

	public FileResponseHolder() {
		super();
	}

	public FileResponseHolder(int statusCode, String contentType, Header[] headers, File file) {
		super(statusCode, contentType, headers);
		this.file = file;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder("HtmlResponseHolder: ");

		// 拼接响应状态码
		sb.append("StatusCode: " + getStatusCode());
		sb.append("\n");

		// 拼接content-type
		sb.append("Content-Type: " + getContentType());
		sb.append("\n");

		// 拼接响应头
		sb.append("Headers: ");
		sb.append(Arrays.toString(getHeaders()));
		sb.append("\n");

		// 拼接Cookie
		sb.append("Cookies: ");
		sb.append(getCookies());
		sb.append("\n");

		// 拼接html内容
		sb.append("File: ");
		sb.append(this.file.getAbsolutePath());

		return sb.toString();
	}
}
