package org.eulerframework.cloud.security;

public class EulerAccessDeniedException extends RuntimeException {

    public EulerAccessDeniedException(String msg) {
        super(msg);
    }
}
