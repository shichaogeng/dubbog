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
 * @Create 2018-01-02 15:31
 */
public class RegistryBeanDefinitionParser implements BeanDefinitionParser {

    private final Class<?> beanClass;

    public RegistryBeanDefinitionParser(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {

        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        //spring会把这个beanClass进行实例化,eanDefinitionNames??
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);

        String protocol = element.getAttribute("protocol");
        String address = element.getAttribute("address");

        if (StringUtils.isEmpty(protocol)) {
            throw new RuntimeException("Registry protocol 不能为空！");
        }

        if (StringUtils.isEmpty(address)) {
            throw new RuntimeException("Registry address 不能为空！");
        }

        beanDefinition.getPropertyValues().addPropertyValue("protocol", protocol);
        beanDefinition.getPropertyValues().addPropertyValue("address", address);

        parserContext.getRegistry().registerBeanDefinition("Registry" + address, beanDefinition);

        return beanDefinition;
    }
}
