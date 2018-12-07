/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.eulerframework.cloud.bean;

import org.eulerframework.web.core.i18n.ClassPathReloadableResourceBundleMessageSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

@Configuration
@ConditionalOnClass(ClassPathReloadableResourceBundleMessageSource.class)
public class EulerCloudBeans {

    @Bean
    public MessageSource messageSource() {
        ClassPathReloadableResourceBundleMessageSource messageSource = new ClassPathReloadableResourceBundleMessageSource();
        messageSource.setCacheSeconds(Integer.MAX_VALUE);
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setBasename("classpath*:language/**/*");
        return messageSource;
    }
}
