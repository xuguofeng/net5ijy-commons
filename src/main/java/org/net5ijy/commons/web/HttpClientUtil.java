package org.net5ijy.commons.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.net5ijy.commons.util.StringUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * HTTP请求处理工具类
 * 
 * @author 创建人：xuguofeng
 * @version 创建于：2018年9月11日 下午1:22:41
 */
public class HttpClientUtil {

	/**
	 * 处理没有请求参数的GET请求
	 * 
	 * @author 创建人：xuguofeng
	 * @version 创建于：2018年9月11日 下午1:20:55
	 * @param url
	 *            - 请求地址，后面可以有“?arg1=val1&arg2=val2”这样的参数
	 * @return 响应字符串
	 */
	public static String doGet(String url) {
		return doGet(url, null);
	}

	/**
	 * 处理含有请求参数的GET请求
	 * 
	 * @author 创建人：xuguofeng
	 * @version 创建于：2018年9月11日 下午1:22:06
	 * @param url
	 *            - 请求地址，后面可以有“?arg1=val1&arg2=val2”这样的参数
	 * @param params
	 *            - 封装请求参数
	 * @return 响应字符串
	 */
	public static String doGet(String url, Map<String, String> params) {

		// 获取HttpClient
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 定义HttpResponse
		CloseableHttpResponse response = null;

		try {
			// 请求参数
			String paramStr = "?";
			if (url.indexOf("?") != -1) {
				paramStr = "&";
			}
			if (params != null && !params.isEmpty()) {
				List<NameValuePair> paramsList = new ArrayList<NameValuePair>();

				Set<Entry<String, String>> es = params.entrySet();

				for (Iterator<Entry<String, String>> i = es.iterator(); i
						.hasNext();) {
					Entry<String, String> e = i.next();
					paramsList.add(new BasicNameValuePair(e.getKey(), e
							.getValue()));
				}
				paramStr = paramStr
						+ EntityUtils.toString(new UrlEncodedFormEntity(
								paramsList, "UTF-8"));
			}

			// 创建httpget
			HttpGet httpget = new HttpGet(url + paramStr);

			// 发送请求并获取响应
			response = httpclient.execute(httpget);

			// 获取响应实体
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				// 响应内容
				String content = EntityUtils.toString(entity);
				return content;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpclient != null) {
				try {
					httpclient.close();
				} catch (IOException e) {
				}
			}
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}

	/**
	 * 使用POST方式发送JSON数据
	 * 
	 * @author 创建人：xuguofeng
	 * @version 创建于：2018年9月11日 下午1:23:00
	 * @param url
	 *            - 请求地址
	 * @param json
	 *            - json字符串
	 * @return 响应字符串
	 */
	public static String doPostJson(String url, String json) {
		// 获取HttpClient
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 定义HttpResponse
		CloseableHttpResponse response = null;

		try {
			// 创建httpPost
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("Content-Type", "application/json");
			httpPost.setEntity(new StringEntity(json, "UTF-8"));

			// 发送请求并获取响应
			response = httpclient.execute(httpPost);

			// 获取响应实体
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				// 响应内容
				String content = EntityUtils.toString(entity, "UTF-8");
				return content;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpclient != null) {
				try {
					httpclient.close();
				} catch (IOException e) {
				}
			}
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}

	/**
	 * 使用POST方式发送JSON数据
	 * 
	 * @author 创建人：xuguofeng
	 * @version 创建于：2018年9月11日 下午1:23:00
	 * @param url
	 *            - 请求地址
	 * @param jsonAble
	 *            - 可以转为JSON字符串的Object对象
	 * @return 响应字符串
	 */
	public static String doPostJson(String url, Object jsonAble) {
		try {
			// 把参数转为JSON字符串
			ObjectMapper mapper = new ObjectMapper();
			String body = mapper.writeValueAsString(jsonAble);
			return doPostJson(url, body);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 使用POST方式发送XML数据
	 * 
	 * @author 创建人：xuguofeng
	 * @version 创建于：2018年9月11日 下午1:23:00
	 * @param url
	 *            - 请求地址
	 * @param xml
	 *            - 封装xml数据
	 * @return 响应字符串
	 */
	public static String doPostXml(String url, Map<String, String> xml) {
		return doPostXml(url, XmlUtil.map2Xml(xml, "xml"));
	}

	/**
	 * 使用POST方式发送XML数据
	 * 
	 * @author 创建人：xuguofeng
	 * @version 创建于：2018年9月11日 下午1:23:00
	 * @param url
	 *            - 请求地址
	 * @param xml
	 *            - xml字符串
	 * @return 响应字符串
	 */
	public static String doPostXml(String url, String xml) {

		// 获取HttpClient
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 定义HttpResponse
		CloseableHttpResponse response = null;

		try {
			// 创建httpPost
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("Content-Type", "text/xml");
			httpPost.setEntity(new StringEntity(xml, "UTF-8"));

			// 发送请求并获取响应
			response = httpclient.execute(httpPost);

			// 获取响应实体
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				// 响应内容
				String content = EntityUtils.toString(entity);
				return content;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpclient != null) {
				try {
					httpclient.close();
				} catch (IOException e) {
				}
			}
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}

	/**
	 * 发送一个POST请求到指定url
	 * 
	 * @author 创建人：xuguofeng
	 * @version 创建于：2018年9月11日 下午1:27:30
	 * @param url
	 *            - 请求地址，后面可以有“?arg1=val1&arg2=val2”这样的参数
	 * @return 响应字符串
	 */
	public static String doPost(String url) {
		return doPostForm(url, null);
	}

	/**
	 * 发送一个POST请求到指定url
	 * 
	 * @author 创建人：xuguofeng
	 * @version 创建于：2018年9月11日 下午1:28:30
	 * @param url
	 *            - 请求地址
	 * @param params
	 *            - 封装请求参数
	 * @return 响应字符串
	 */
	public static String doPostForm(String url, Map<String, String> params) {

		// 获取HttpClient
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 定义HttpResponse
		CloseableHttpResponse response = null;

		try {
			// 创建httpPost
			HttpPost httpPost = new HttpPost(url);

			// 请求参数
			if (params != null && !params.isEmpty()) {
				List<NameValuePair> paramsList = new ArrayList<NameValuePair>();

				Set<Entry<String, String>> es = params.entrySet();

				for (Iterator<Entry<String, String>> i = es.iterator(); i
						.hasNext();) {
					Entry<String, String> e = i.next();
					paramsList.add(new BasicNameValuePair(e.getKey(), e
							.getValue()));
				}
				HttpEntity entity = new UrlEncodedFormEntity(paramsList,
						"UTF-8");
				// 设置参数
				httpPost.setEntity(entity);
			}

			// 发送请求并获取响应
			response = httpclient.execute(httpPost);

			// 获取响应实体
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				// 响应内容
				String content = EntityUtils.toString(entity);
				return content;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpclient != null) {
				try {
					httpclient.close();
				} catch (IOException e) {
				}
			}
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}

	/**
	 * 发送一个POST请求到指定url
	 * 
	 * @author 创建人：xuguofeng
	 * @version 创建于：2018年9月12日 下午1:31:06
	 * @param url
	 *            - 请求地址
	 * @param params
	 *            - 普通类型参数
	 * @param file
	 *            - 文件
	 * @param name
	 *            - 文件域的name值
	 * @return
	 */
	public static String doPostFormWithFile(String url,
			Map<String, String> params, File file, String name) {

		// 获取HttpClient
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 定义HttpResponse
		CloseableHttpResponse response = null;

		// 超时设置
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(2000000).setSocketTimeout(200000000).build();

		try {

			// 创建httpPost
			HttpPost httpPost = new HttpPost(url);

			// 超时设置
			httpPost.setConfig(requestConfig);

			ContentType contentType = ContentType.create("text/plain",
					Consts.UTF_8);

			// multipart请求体构建器
			MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder
					.create();

			// 添加上传文件
			multipartEntityBuilder.addBinaryBody(name, file);

			// 表单数据
			Set<Entry<String, String>> es = params.entrySet();
			for (Iterator<Entry<String, String>> i = es.iterator(); i.hasNext();) {
				Entry<String, String> e = i.next();
				// String val = URLEncoder.encode(e.getValue(), "UTF-8");
				String val = e.getValue();
				// multipartEntityBuilder.addTextBody(e.getKey(), val);
				multipartEntityBuilder.addPart(e.getKey(), new StringBody(val,
						contentType));
			}

			// 创建HttpEntity
			HttpEntity httpEntity = multipartEntityBuilder.build();
			httpPost.setEntity(httpEntity);

			// 发送请求并获取响应
			response = httpclient.execute(httpPost);

			// 获取响应实体
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				// 响应内容
				String content = EntityUtils.toString(entity);
				return content;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpclient != null) {
				try {
					httpclient.close();
				} catch (IOException e) {
				}
			}
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}

	/**
	 * 上传文件到指定url
	 * 
	 * @author 创建人：xuguofeng
	 * @version 创建于：2018年9月11日 下午1:29:09
	 * @param url
	 *            - 请求地址
	 * @param path
	 *            - 文件路径
	 * @param name
	 *            - 文件在form中的name值
	 * @return
	 */
	public static String upload(String url, String path, String name) {
		return upload(url, new File(path), name);
	}

	/**
	 * 上传文件到指定url
	 * 
	 * @author 创建人：xuguofeng
	 * @version 创建于：2018年9月11日 下午1:29:09
	 * @param url
	 *            - 请求地址
	 * @param file
	 *            - 文件对象
	 * @param name
	 *            - 文件在form中的name值
	 * @return
	 */
	public static String upload(String url, File file, String name) {

		// 获取HttpClient
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 定义HttpResponse
		CloseableHttpResponse response = null;

		// 超时设置
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(200000).setSocketTimeout(200000000).build();

		try {

			// 创建httpPost
			HttpPost httpPost = new HttpPost(url);
			// 超时设置
			httpPost.setConfig(requestConfig);

			// multipart请求体构建器
			MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder
					.create();
			// 添加上传文件
			multipartEntityBuilder.addBinaryBody(name, file);

			// 创建HttpEntity
			HttpEntity httpEntity = multipartEntityBuilder.build();
			httpPost.setEntity(httpEntity);

			// 发送请求并获取响应
			response = httpclient.execute(httpPost);

			// 获取响应实体
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				// 响应内容
				String content = EntityUtils.toString(entity);
				return content;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpclient != null) {
				try {
					httpclient.close();
				} catch (IOException e) {
				}
			}
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}

	public static String appendUrlArgument(String url, String arg, String val) {

		if (StringUtil.isNullOrEmpty(url)) {
			return url;
		}

		StringBuilder sb = new StringBuilder(url);

		if (url.indexOf("?") > -1) {
			sb.append("&");
			sb.append(arg);
			sb.append("=");
			sb.append(val);
			return sb.toString();
		}

		sb.append("?");
		sb.append(arg);
		sb.append("=");
		sb.append(val);
		return sb.toString();
	}

	public static <T> T parseJson(String json, Class<T> type) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(json, type);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
