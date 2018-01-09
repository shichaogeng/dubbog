package com.gengsc.parser;

import com.gengsc.configbean.ProtocolConfig;
import com.gengsc.configbean.ReferenceBean;
import com.gengsc.configbean.RegistryConfig;
import com.gengsc.configbean.ServiceBean;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @Description
 * @Author shichaogeng
 * @Create 2018-01-02 14:50
 */
public class SoaNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("registry", new RegistryBeanDefinitionParser(RegistryConfig.class));
        registerBeanDefinitionParser("protocol", new ProtocolBeanDefinitionParser(ProtocolConfig.class));
        registerBeanDefinitionParser("service", new ServiceBeanDefinitionServer(ServiceBean.class));
        registerBeanDefinitionParser("reference", new ReferenceBeanDefinitionParser(ReferenceBean.class));
    }
}
