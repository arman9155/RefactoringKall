package com.refactoring.rekall;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Auth {

    public enum Role {ADMIN, USER, KAKAO, NAVER}

    public Role role() default Role.USER;
}
