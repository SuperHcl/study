package com.hcl.spring.config;

/**
 * @author: Hucl
 * @date: 2019/7/10 15:05
 * @description:
 */
public class RuntimeBeanReference {
    private String ref;

    public RuntimeBeanReference(String ref) {
        this.ref = ref;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
