package com.hcl.mybatis.util;

import com.hcl.mybatis.config.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Hucl
 * @date: 2019/7/2 14:32
 * @description:
 */
public class ParameterMappingTokenHandler implements TokenHandler {

    private List<ParameterMapping> parameterMappings = new ArrayList<>();

    /**
     * @param content 参数名称
     * @return "?" select * from user where id = ?
     */
    @Override
    public String handleToken(String content) {
        parameterMappings.add(buildParameterMapping(content));
        return "?";
    }

    private ParameterMapping buildParameterMapping(String content) {
        return  new ParameterMapping(content);
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public void setParameterMappings(List<ParameterMapping> parameterMappings) {
        this.parameterMappings = parameterMappings;
    }
}
