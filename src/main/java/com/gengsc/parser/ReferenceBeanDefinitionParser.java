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
public class ReferenceBeanDefinitionParser implements BeanDefinitionParser {

    private final Class<?> beanClass;

    public ReferenceBeanDefinitionParser(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {


        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        //spring会把这个beanClass进行实例化,eanDefinitionNames??
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);

        String id = element.getAttribute("id");
        String intf = element.getAttribute("interface");
        String loadbalance = element.getAttribute("loadbalance");
        String protocol = element.getAttribute("protocol");

        if (StringUtils.isEmpty(id)) {
            throw new RuntimeException("Reference id 不能为空！");
        }

        if (StringUtils.isEmpty(intf)) {
            throw new RuntimeException("Reference intf 不能为空！");
        }

        if (StringUtils.isEmpty(loadbalance)) {
            throw new RuntimeException("Reference loadbalance 不能为空！");
        }

        if (StringUtils.isEmpty(protocol)) {
            throw new RuntimeException("Reference protocol 不能为空！");
        }

        beanDefinition.getPropertyValues().addPropertyValue("id", id);
        beanDefinition.getPropertyValues().addPropertyValue("intf", intf);
        beanDefinition.getPropertyValues().addPropertyValue("loadbalance", loadbalance);
        beanDefinition.getPropertyValues().addPropertyValue("protocol", protocol);

        parserContext.getRegistry().registerBeanDefinition("Reference" + id, beanDefinition);

        return beanDefinition;
    }
}
