package com.supergo.feign;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by on 2019/3/14.
 */
@FeignClient("supergo-manager")
public interface ApiLoginFeign {



}
