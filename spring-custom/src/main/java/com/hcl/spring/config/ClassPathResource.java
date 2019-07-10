package com.hcl.spring.config;

import java.io.InputStream;

/**
 * @author: Hucl
 * @date: 2019/7/10 14:12
 * @description:
 */
public class ClassPathResource implements Resource {
    private String location;

    @Override
    public boolean isCanRead(String location) {
        if (location == null || "".equals(location)) {
            return false;
        }
        if (location.startsWith("classpath:")) {
            this.location = location;
            return true;
        }
        return false;
    }

    @Override
    public InputStream getInputStream() {
        if (location == null || "".equals(location)) {
            return null;
        }
        location = location.replace("classpath:", "");
        return this.getClass().getClassLoader().getResourceAsStream(location);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
