/*
 * Copyright 2002-2018 the original author or authors.
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
 *  imitations under the License.
 */
package org.eulerframework.cloud.config;

import org.eulerframework.common.util.Assert;
import org.eulerframework.common.util.CommonUtils;
import org.eulerframework.common.util.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "euler")
public class EulerCloudConfig implements InitializingBean {
    private String runtimePath;
    private String tmpPath;

    public String getRuntimePath() {
        return runtimePath;
    }

    public void setRuntimePath(String runtimePath) {
        this.runtimePath = CommonUtils.convertDirToUnixFormat(runtimePath, false);
    }

    public String getTmpPath() {
        return tmpPath;
    }

    public void setTmpPath(String tmpPath) {
        this.tmpPath = CommonUtils.convertDirToUnixFormat(tmpPath, false);
    }

    @Override
    public void afterPropertiesSet() {
        Assert.hasText(this.runtimePath, "euler cloud app runtime path must be setted.");

        if(this.tmpPath == null) {
            this.tmpPath = this.runtimePath + "/tmp";
        } else {
            Assert.hasText(this.tmpPath, "euler cloud app temp path can not be empty.");
        }
    }
}
