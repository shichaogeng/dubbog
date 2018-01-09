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
 * @Create 2018-01-02 15:39
 */
public class ServiceBeanDefinitionServer implements BeanDefinitionParser {

    private final Class<?> beanClass;

    public ServiceBeanDefinitionServer(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {

        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        //spring会把这个beanClass进行实例化,eanDefinitionNames??
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);

        String intf = element.getAttribute("interface");
        String ref = element.getAttribute("ref");
        String protocol = element.getAttribute("protocol");

        if (StringUtils.isEmpty(intf)) {
            throw new RuntimeException("Service intf 不能为空！");
        }

        if (StringUtils.isEmpty(ref)) {
            throw new RuntimeException("Service ref 不能为空！");
        }

        if (StringUtils.isEmpty(protocol)) {
            throw new RuntimeException("Service protocol 不能为空！");
        }

        beanDefinition.getPropertyValues().addPropertyValue("intf", intf);
        beanDefinition.getPropertyValues().addPropertyValue("ref", ref);
        beanDefinition.getPropertyValues().addPropertyValue("protocol", protocol);

        parserContext.getRegistry().registerBeanDefinition("Service" + intf + ref, beanDefinition);

        return beanDefinition;
    }
}
