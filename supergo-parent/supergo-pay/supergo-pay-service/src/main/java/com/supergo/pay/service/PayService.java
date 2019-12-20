package com.supergo.pay.service;

import java.util.Map;

public interface PayService {

    Map<String, String> closePay(String outtradeno);


    Map<String, String> queryStatus(String outtradeno);


    Map<String, String> createNative(String outtradeno, String money);

}
