package com.gengsc.registry;

import com.gengsc.configbean.RegistryConfig;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * @Description
 * @Author shichaogeng
 * @Create 2018-01-03 16:40
 */
public class BaseRegistryDelegate {

    public static void register(String ref, ApplicationContext applicationContext) {

        BaseRegistry baseRegistry = getBaseRegistry(applicationContext);
        baseRegistry.register(ref, applicationContext);
    }

    public static List<String> getRegistries(String id, ApplicationContext applicationContext) {
        BaseRegistry baseRegistry = getBaseRegistry(applicationContext);
        return baseRegistry.getRegistry(id, applicationContext);

    }

    private static BaseRegistry getBaseRegistry(ApplicationContext applicationContext) {
        RegistryConfig registryConfig = applicationContext.getBean(RegistryConfig.class);
        String protocol = registryConfig.getProtocol();
        BaseRegistry baseRegistry = registryConfig.registries.get(protocol);
        return baseRegistry;
    }
}
