package com.hcl.spring.config;

import java.io.InputStream;

/**
 * @author: Hucl
 * @date: 2019/7/10 14:09
 * @description:
 */
public interface Resource {

    boolean isCanRead(String location);

    InputStream getInputStream();
}
