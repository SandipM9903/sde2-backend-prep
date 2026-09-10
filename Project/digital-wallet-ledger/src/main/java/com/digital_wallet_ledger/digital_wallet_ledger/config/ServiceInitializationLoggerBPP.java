package com.digital_wallet_ledger.digital_wallet_ledger.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * Custom BeanPostProcessor to observe the initialization phase of our services.
 */
@Component
public class ServiceInitializationLoggerBPP implements BeanPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(ServiceInitializationLoggerBPP.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // Intercepts bean right BEFORE @PostConstruct or afterPropertiesSet runs
        if (beanName.contains("FeeCalculator") || beanName.contains("Wallet")) {
            log.info("[BPP - BEFORE INIT] Initializing bean: '{}' of type [{}]", beanName, bean.getClass().getName());
        }
        return bean; // Must return the bean instance
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // Intercepts bean AFTER initialization. This is where AOP/Transactional proxies are wrapped!
        if (beanName.contains("FeeCalculator") || beanName.contains("Wallet")) {
            log.info("[BPP - AFTER INIT] Completed initialization for bean: '{}'. Ready for proxying or traffic.", beanName);
        }
        return bean; // Must return the bean (or a wrapped proxy)
    }
}
