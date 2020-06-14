package com.byteframework.base.user.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

/**
 * beanName生成策略（全限定类名）
 * <p>
 * spring提供两种beanName生成策略，基于注解的sprong-boot默认使用的是AnnotationBeanNameGenerator，它生成beanName的策略就是，取当前类名（不是全限定类名）作为beanName。
 * 由此，如果出现不同包结构下同样的类名称，肯定会出现冲突
 * </p>
 */
public class UniqueNameGenerator extends AnnotationBeanNameGenerator {

    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        //全限定类名
        String beanName = definition.getBeanClassName();
        return beanName;
    }
}
