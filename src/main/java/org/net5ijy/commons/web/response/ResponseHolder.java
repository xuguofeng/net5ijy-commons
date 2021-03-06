package org.net5ijy.commons.web.response;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

import org.apache.http.Header;

@Data
public class ResponseHolder {

	private int statusCode;

	private String contentType;

	private Header[] headers;

	private Map<String, Cookie> cookies = null;

	public ResponseHolder() {
		super();
	}

	public ResponseHolder(int statusCode, String contentType, Header[] headers) {
		super();
		this.statusCode = statusCode;
		this.contentType = contentType;
		this.headers = headers;
	}

	public String getContentType() {
		return contentType == null ? "" : contentType;
	}

	public void setHeaders(Header[] headers) {
		this.headers = headers;

		// 获取cookie
		this.cookies = this.cookies();
	}

	public String cookieValue(String name) {
		return this.cookies.get(name).value;
	}

	private Map<String, Cookie> cookies() {

		Map<String, Cookie> cookies = new HashMap<String, ResponseHolder.Cookie>();

		for (Header header : headers) {

			String name = header.getName();

			if ("Set-Cookie".equals(name)) {

				String value = header.getValue();

				String[] vs = value.split(";\\s");

				String kv = vs[0];

				String[] ss = kv.split("=");

				Cookie cookie = new Cookie();
				cookie.name = ss[0];
				cookie.value = ss[1];

				cookies.put(ss[0], cookie);
			}
		}

		return cookies;
	}

	@Data
	public static class Cookie {

		private String name;

		private String value;

		public Cookie() {
			super();
		}

		public Cookie(String name, String value) {
			super();
			this.name = name;
			this.value = value;
		}
	}
}
