package com.hcl.springmvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: Hucl
 * @date: 2019/7/23 18:46
 * @description:
 */
// 表示该注解加在哪个位置
@Target({ElementType.TYPE})
// 表示该注解作用于哪个阶段（编译、保存到class文件、运行阶段）
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
    // @Controller(value="")
    String value() default "";

}
