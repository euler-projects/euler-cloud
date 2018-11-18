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

public class AuthenticationZuulFilter extends ZuulFilter {
    public final static String EULER_CURRENT_USER_ID_HEADER = "Euler-Cloud-Current-User-Id";

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
        return true;
    }

    @Override
    public Object run() {
        EulerOAuth2UserDetails eulerOAuth2UserDetails = SpringCloudOAuth2UserContext.getCurrentUser();
        RequestContext request = RequestContext.getCurrentContext();
        request.addZuulRequestHeader(EULER_CURRENT_USER_ID_HEADER, eulerOAuth2UserDetails.getUserId());
        return null;
    }
}
