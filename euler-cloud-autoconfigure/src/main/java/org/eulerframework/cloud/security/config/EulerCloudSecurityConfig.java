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
package org.eulerframework.cloud.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

@ConfigurationProperties(prefix = "euler.security")
public class EulerCloudSecurityConfig {
    private String[] noneSecurityPath;

    public String[] getNoneSecurityPath() {
        return noneSecurityPath;
    }

    public void setNoneSecurityPath(String[] noneSecurityPath) {
        this.noneSecurityPath = noneSecurityPath;
    }

    @ConditionalOnClass(WebSecurity.class)
    @Order(SecurityProperties.IGNORED_ORDER)
    @Configuration
    public static class IgnoredPathsWebSecurityConfigurer
            implements WebSecurityConfigurer<WebSecurity> {

        @Autowired
        private EulerCloudSecurityConfig eulerCloudSecurityConfig;

        @Override
        public void init(WebSecurity builder) {
            String[] noneSecurityPath = this.eulerCloudSecurityConfig.getNoneSecurityPath();
            if(noneSecurityPath != null && noneSecurityPath.length > 0) {
                builder.ignoring().requestMatchers(
                        Arrays.stream(noneSecurityPath)
                                .map(AntPathRequestMatcher::new)
                                .toArray(AntPathRequestMatcher[]::new));
            }
        }

        @Override
        public void configure(WebSecurity builder) {
        }

    }
}
