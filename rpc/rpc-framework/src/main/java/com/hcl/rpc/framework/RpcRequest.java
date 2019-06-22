package com.hcl.rpc.framework;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Hucl
 * @date: 2019/6/22 14:37
 * @description: 消息体：方法名称、接口类名、方法参数、参数类型
 */
@Data
public class RpcRequest implements Serializable {

    private String className;

    private String methodName;

    private Object[] parameters;

    private Class[] type;
}
