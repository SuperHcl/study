package com.hcl.springmvc.test;

import com.hcl.spring.util.ClassUtils;

import java.util.List;

/**
 * @author: Hucl
 * @date: 2019/7/23 19:52
 * @description:
 */
public class Test {
    public static void main(String[] args) {
        List<String> packages = ClassUtils.getClazzName("com.hcl.springmvc.handler", false);
        for (String aPackage : packages) {
            System.out.println(aPackage);
        }
    }
}
