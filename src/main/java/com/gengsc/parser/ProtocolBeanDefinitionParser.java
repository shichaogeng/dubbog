package com.gengsc.parser;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @Description
 * @Author shichaogeng
 * @Create 2018-01-02 15:38
 */
public class ProtocolBeanDefinitionParser implements BeanDefinitionParser {

    private final Class<?> beanClass;

    public ProtocolBeanDefinitionParser(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {

        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        //spring会把这个beanClass进行实例化,eanDefinitionNames??
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);

        String name = element.getAttribute("name");
        String host = element.getAttribute("host");
        String port = element.getAttribute("port");
        String contextPath = element.getAttribute("contextPath");

        if (StringUtils.isEmpty(name)) {
            throw new RuntimeException("Protocol name 不能为空！");
        }

        if (StringUtils.isEmpty(host)) {
            throw new RuntimeException("Protocol host 不能为空！");
        }

        if (StringUtils.isEmpty(port)) {
            throw new RuntimeException("Protocol port 不能为空！");
        }

        beanDefinition.getPropertyValues().addPropertyValue("name", name);
        beanDefinition.getPropertyValues().addPropertyValue("host", host);
        beanDefinition.getPropertyValues().addPropertyValue("port", port);
        beanDefinition.getPropertyValues().addPropertyValue("contextPath", contextPath);

        parserContext.getRegistry().registerBeanDefinition("Protocol" + "-" + host + ":" + port, beanDefinition);

        return beanDefinition;
    }
}
