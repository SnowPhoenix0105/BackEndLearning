package top.snowphoenix.exp2.aop;

import top.snowphoenix.exp2.auth.Role;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RequireAuthWithRole {
    Role[] value() default { };
}
