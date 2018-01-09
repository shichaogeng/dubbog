package com.gengsc.registry;

import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * @Description
 * @Author shichaogeng
 * @Create 2018-01-03 17:16
 */
public interface BaseRegistry {

    boolean register(String ref, ApplicationContext applicationContext);

    List<String> getRegistry(String id, ApplicationContext applicationContext);
}
