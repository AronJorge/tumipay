package com.tumipay.infrastructure.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.tumipay.infrastructure.config.constant.AppConfigConstants;

import org.springframework.lang.NonNull;

@Configuration
public class MessageConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(AppConfigConstants.CLASSPATH_I18N_GLOBAL,
                AppConfigConstants.CLASSPATH_I18N_PAYIN);
        messageSource.setDefaultEncoding(AppConfigConstants.ENCODING_UTF8);
        return messageSource;
    }

    public LocalValidatorFactoryBean getValidator(@NonNull MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }
}
