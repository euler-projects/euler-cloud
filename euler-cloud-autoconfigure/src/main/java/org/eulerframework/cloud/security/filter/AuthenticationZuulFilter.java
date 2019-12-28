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
package org.eulerframework.cloud.security.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.eulerframework.oauth2.resource.context.EulerOAuth2UserDetails;
import org.eulerframework.oauth2.resource.context.SpringCloudOAuth2UserContext;
import org.eulerframework.web.util.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class AuthenticationZuulFilter extends ZuulFilter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public final static String EULER_CURRENT_USER_ID_HEADER = "Euler-Uid";
    public final List<AntPathRequestMatcher> ignoredPatterns;

    public AuthenticationZuulFilter(String[] ignoredPatterns) {
        if (ignoredPatterns == null || ignoredPatterns.length == 0) {
            this.ignoredPatterns = null;
        } else {
            this.ignoredPatterns = new ArrayList<>();
            for (String ignoredPattern : ignoredPatterns) {
                this.ignoredPatterns.add(new AntPathRequestMatcher(ignoredPattern));
            }
        }
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        HttpServletRequest request = ServletUtils.getRequest();

        if (CollectionUtils.isEmpty(this.ignoredPatterns)) {
            return true;
        } else {
            for (AntPathRequestMatcher ignoredPattern : this.ignoredPatterns) {
                if (ignoredPattern.matches(request)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Object run() {
        try {
            EulerOAuth2UserDetails eulerOAuth2UserDetails = SpringCloudOAuth2UserContext.getCurrentUser();
            RequestContext request = RequestContext.getCurrentContext();
            request.addZuulRequestHeader(EULER_CURRENT_USER_ID_HEADER, eulerOAuth2UserDetails.getUserId());
        } catch (Exception e) {
            this.logger.warn(e.getMessage(), e);
        }
        return null;
    }
}
