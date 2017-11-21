package com.crazyxxl.easemob.config;

import com.crazyxxl.easemob.service.impl.EasemobClient;
import com.crazyxxl.easemob.service.impl.HttpClientService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        HttpClient.class,
        EasemobClient.class,
        HttpClientService.class
})
public class EasemobAutoConfiguration implements BeanFactoryAware {

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}