package com.hcl.dubbo.registry;

/**
 * @author: Hucl
 * @date: 2019/9/26 19:16
 * @description:
 */
public class RegistryTest {
    public static void main(String[] args) throws Exception {
        RegistryCenter registryCenter = new ZKRegistryCenter();

        registryCenter.register("com.hcl.dubbo.service", "localhost:9999");
        System.in.read();
    }
}
