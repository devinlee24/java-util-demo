package com.devinlee.javautildemo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring Ioc容器
 */
@Component
public class SpringContextUtil2 implements ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(SpringContextUtil.class);
    private static ApplicationContext applicationContext;

    public SpringContextUtil2() {
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringContextUtil2.applicationContext == null) {
            SpringContextUtil2.applicationContext = applicationContext;
        }

        log.info("SpringContextUtil配置成功,applicationContext对象：" + SpringContextUtil2.applicationContext);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}

