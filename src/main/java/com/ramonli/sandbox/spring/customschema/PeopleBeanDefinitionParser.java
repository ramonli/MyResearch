package com.ramonli.sandbox.spring.customschema;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class PeopleBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

	protected Class getBeanClass(Element element) {
		return People.class;
	}

	protected void doParse(Element element, BeanDefinitionBuilder bean) {
		String name = element.getAttribute("name");
		String age = element.getAttribute("age");
//		String id = element.getAttribute("id");
//		if (StringUtils.hasText(id)) {
//			bean.addPropertyValue("id", id);
//		}
		if (StringUtils.hasText(name)) {
			bean.addPropertyValue("name", name);
		}
		if (StringUtils.hasText(age)) {
			bean.addPropertyValue("age", Integer.valueOf(age));
		}

		System.out.println(element.getNamespaceURI());
		NodeList eles = element.getElementsByTagNameNS(element.getNamespaceURI(), "address");
		List addresses = new LinkedList<Address>();
		for (int i = 0; i < eles.getLength(); i++) {
			Element ele = (Element) eles.item(i);
			Address address = new Address();
			address.setCity(ele.getAttribute("city"));
			address.setCountry(ele.getAttribute("country"));
			address.setStreet(ele.getAttribute("street"));
			address.setZipcode(ele.getAttribute("zipcode"));
			addresses.add(address);
		}
		bean.addPropertyValue("addresses", addresses);
	}
}
