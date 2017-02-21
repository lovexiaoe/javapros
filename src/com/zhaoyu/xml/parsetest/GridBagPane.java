package com.zhaoyu.xml.parsetest;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Field;

import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * ����һ����XML�ļ���������������������񲼾ֵ�λ�á�
 *
 * @author xiaoE
 *
 */
public class GridBagPane extends JPanel {
	private GridBagConstraints constraints;

	public GridBagPane(String filename) {
		setLayout(new GridBagLayout());
		constraints = new GridBagConstraints();

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(true);

			// xsd��֤����
			if (filename.contains("-schema")) {
				// xml�Ľ��������ṩ��xml�����ռ��֧�֡�
				factory.setNamespaceAware(true);
				final String JAXP_SCHEMA_LANGUAGE = "http:java.sun.com/xml/jaxp/properties/schemaLanguage";
				final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
				factory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
			}
			// ���ý�����ȥ��Ԫ�����ݵĿհ׷�
			factory.setIgnoringElementContentWhitespace(true);

			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new File(filename));

			// xsd��֤����
			// if (filename.contains("-schema")) {
			int count = removeElementContentWhitespace(doc.getDocumentElement());
			System.out.println(count + "whitespace nodes removed.");
			// }

			parseGridbag(doc.getDocumentElement());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �Ƴ��ĵ��еĿհ׷��ڵ㡣��Ԫ��֮��Ŀհ׷���
	 *
	 * @param e
	 * @return
	 */
	private int removeElementContentWhitespace(Element e) {
		NodeList children = e.getChildNodes();
		int count = 0;
		// �Ƿ����е����ı��ڵ�Ϊ�հ׷�
		boolean allTextChildrenAreWhitespace = true;
		// children���ı���Ԫ�صļ�����
		int elements = 0;

		for (int i = 0; i < children.getLength() && allTextChildrenAreWhitespace; i++) {
			Node child = children.item(i);
			if (child instanceof Text && ((Text) child).getData().trim().length() > 0) {
				allTextChildrenAreWhitespace = false;
			} else if (child instanceof Element) {
				elements++;
				count += removeElementContentWhitespace((Element) child);
			}
		}
		// �����Ԫ��Ϊ�ı������е��ı�Ϊ�հ׷������Ƴ�����հ׷����Ƴ���������
		if (elements > 0 && allTextChildrenAreWhitespace) {
			for (int i = children.getLength() - 1; i >= 0; i--) {
				Node child = children.item(i);
				if (child instanceof Text) {
					e.removeChild(child);
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * ��ȡָ�����Ƶ����
	 *
	 * @param name
	 * @return
	 */
	public Component get(String name) {
		Component[] components = getComponents();
		for (int i = 0; i < components.length; i++) {
			if (components[i].getName().equals(name)) {
				return components[i];
			}
		}
		return null;
	}

	/**
	 * ����������з���
	 *
	 * @param e
	 */
	private void parseGridbag(Element e) {
		NodeList rows = e.getChildNodes();
		for (int i = 0; i < rows.getLength(); i++) {
			Element row = (Element) rows.item(i);
			NodeList cells = row.getChildNodes();
			for (int j = 0; j < cells.getLength(); j++) {
				Element cell = (Element) cells.item(j);
				parseCell(cell, i, j);
			}
		}

	}

	/**
	 * ��cellԪ�ؽ��н���
	 *
	 * @param e
	 * @param r
	 * @param c
	 */
	private void parseCell(Element e, int r, int c) {
		// ��ȡgridx����
		String value = e.getAttribute("gridx");
		if (value.length() == 0) {
			if (c == 0) {
				constraints.gridx = 0;
			} else {
				constraints.gridx += constraints.gridwidth;
			}
		} else {
			constraints.gridx = Integer.parseInt(value);
		}

		// ��ȡ����gridy
		value = e.getAttribute("gridy");
		if (value.length() == 0) {
			constraints.gridy = r;
		} else {
			constraints.gridy = Integer.parseInt(value);
		}

		constraints.gridwidth = Integer.parseInt(e.getAttribute("gridwidth"));
		constraints.gridheight = Integer.parseInt(e.getAttribute("gridheight"));
		constraints.weightx = Integer.parseInt(e.getAttribute("weightx"));
		constraints.weighty = Integer.parseInt(e.getAttribute("weighty"));
		constraints.ipadx = Integer.parseInt(e.getAttribute("ipadx"));
		constraints.ipady = Integer.parseInt(e.getAttribute("ipady"));

		// ʹ�÷����ȡ��̬�ֶε�ֵ ��
		Class<GridBagConstraints> c1 = GridBagConstraints.class;

		try {
			String name = e.getAttribute("fill");
			Field f = c1.getField(name);
			constraints.fill = f.getInt(c1);

			name = e.getAttribute("anchor");
			f = c1.getField(name);
			constraints.anchor = f.getInt(c1);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		Component comp = (Component) parseBean((Element) e.getFirstChild());
		add(comp, constraints);
	}

	// ����һ��beanԪ�ء��������bean��ʵ��
	private Object parseBean(Element e) {
		try {
			NodeList children = e.getChildNodes();
			Element classElement = (Element) children.item(0);
			String className = ((Text) classElement.getFirstChild()).getData();

			Class<?> c1 = Class.forName(className);

			Object obj = c1.newInstance();

			if (obj instanceof Component) {
				((Component) obj).setName(e.getAttribute("id"));
			}

			for (int i = 0; i < children.getLength(); i++) {
				Node propertyElement = children.item(i);
				Element nameElement = (Element) propertyElement.getFirstChild();
				String propertyName = ((Text) nameElement.getFirstChild()).getData();

				Element valueElement = (Element) propertyElement.getLastChild();
				Object value = parseValue(valueElement);
				BeanInfo beanInfo = Introspector.getBeanInfo(c1);
				PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
				boolean done = false;
				for (int j = 0; !done && j < descriptors.length; j++) {
					if (descriptors[i].getName().equals(propertyName)) {
						descriptors[i].getWriteMethod().invoke(obj, value);
						done = true;
					}
				}
			}
			return obj;
		} catch (Exception e2) {
			e2.printStackTrace();
			return null;
		}
	}

	/**
	 * ����һ��ֵԪ�ء�
	 *
	 * @param e
	 * @return
	 */
	private Object parseValue(Element e) {
		Element child = (Element) e.getFirstChild();
		if (child.getTagName().equals("bean")) {
			return parseBean(child);
		}

		String text = ((Text) child.getFirstChild()).getData();

		if (child.getTagName().equals("int")) {
			return new Integer(text);
		} else if (child.getTagName().equals("boolean")) {
			return new Boolean(text);
		} else if (child.getTagName().equals("string")) {
			return text;
		} else {
			return null;
		}
	}
}
