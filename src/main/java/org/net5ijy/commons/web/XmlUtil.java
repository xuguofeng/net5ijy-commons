package org.net5ijy.commons.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * XML工具类
 * 
 * @author 创建人：xuguofeng
 * @version 创建于：2018年9月12日 下午1:36:33
 */
public class XmlUtil {

	/**
	 * 把一个Map转为XML字符串
	 * 
	 * @author 创建人：xuguofeng
	 * @version 创建于：2018年9月12日 下午1:36:42
	 * @param xml
	 *            - 封装数据
	 * @param rootElement
	 *            - 根节点的标签名
	 * @return xml字符串
	 */
	public static String map2Xml(Map<String, String> xml, String rootElement) {
		StringBuilder sb = new StringBuilder();
		sb.append("<");
		sb.append(rootElement);
		sb.append(">");
		Set<Entry<String, String>> es = xml.entrySet();
		for (Iterator<Entry<String, String>> i = es.iterator(); i.hasNext();) {
			Entry<String, String> entry = i.next();
			String name = entry.getKey();
			String val = entry.getValue();
			// 开始标记
			sb.append("<");
			sb.append(name);
			sb.append(">");
			// 文本内容
			sb.append("<![CDATA[");
			sb.append(val);
			sb.append("]]>");
			// 结束标记
			sb.append("</");
			sb.append(name);
			sb.append(">");
		}
		sb.append("</");
		sb.append(rootElement);
		sb.append(">");
		return sb.toString();
	}

	/**
	 * XML字符串转为Map集合
	 * 
	 * @author 创建人：xuguofeng
	 * @version 创建于：2018年9月12日 下午1:37:43
	 * @param xml
	 *            - XML字符串
	 * @return 封装后的Map集合
	 */
	public static Map<String, String> xml2Map(String xml) {
		Map<String, String> xmlMap = new HashMap<String, String>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse(new ByteArrayInputStream(xml
					.getBytes()));
			Element root = dom.getDocumentElement();
			NodeList nodes = root.getChildNodes();
			Node node = null;
			for (int i = 0; i < nodes.getLength(); i++) {
				node = nodes.item(i);
				String nodeName = node.getNodeName();
				String nodeVal = node.getNodeValue();
				xmlMap.put(nodeName, nodeVal);
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return xmlMap;
	}
}
