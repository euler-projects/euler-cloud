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
package org.eulerframework.cloud.security;

import org.eulerframework.cloud.security.filter.AuthenticationZuulFilter;
import org.eulerframework.common.util.ArrayUtils;
import org.eulerframework.common.util.StringUtils;
import org.eulerframework.web.util.ServletUtils;
import org.springframework.security.access.AccessDeniedException;

import java.util.Optional;

public class EulerCloudUserContext {
    public static String getCurrentUserId() {
        return ServletUtils.getRequest().getHeader(AuthenticationZuulFilter.EULER_CURRENT_USER_ID_HEADER);
    }


    public static void requiredAuthorities(String... authority) {
        if(authority == null  || authority.length == 0) {
            return;
        }

        String grantedAuthoritiesStr = ServletUtils.getRequest().getHeader(AuthenticationZuulFilter.EULER_CURRENT_USER_AUTHORITY_HEADER);
        if (StringUtils.hasText(grantedAuthoritiesStr)) {
            String[] grantedAuthorities = grantedAuthoritiesStr.split(",");
            for(String requiredAuthority : authority) {
                if(!ArrayUtils.contains(grantedAuthorities, authority)) {
                    throw new AccessDeniedException("Access Denied");
                }
            }
        } else {
            throw new AccessDeniedException("Access Denied");
        }
    }
}
