package com.supergo.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.supergo.user.service.UserService;
import com.supergo.user.config.StaticProperty;
import com.supergo.service.base.impl.BaseServiceImpl;
import com.supergo.user.mq.MessageSender;
import com.supergo.common.pojo.User;
import com.supergo.user.utils.RedisUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author wesker
 * @Date 6/6/2019 3:54 PM
 * @Version 1.0
 **/
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    // @Autowired
    private RedisUtils redisUtils;

    // @Autowired
    private MessageSender messageSender;

    @Override
    public Boolean verifyCode(String phone, String code) {
        //根据手机号获取库存(Redis)中的验证码
        String key = "UserCode_Mobile_" + phone;
        Object redisCode = redisUtils.get(key);
        //判断验证码是否存在
        if (redisCode == null) {
            return false;
        }
        //存在，则对比验证码是否一致
        if (!code.equalsIgnoreCase(redisCode.toString())) {
            return false;
        }
        //清空Redis中已经存在的验证码缓存
        redisUtils.del(key);
        return true;
    }

    @Override
    public void sendCode(String phone) {
        //生成验证码
        long code = RandomUtils.nextLong(1000, 9999);
        //将验证码存储到Redis(1分钟有效)
        String key = "UserCode_Mobile_" + phone;
        redisUtils.set(key, String.valueOf(code));
        redisUtils.expire(key, 60);
        //封装要发送的数据
        Map<String, String> paramMap = this.sendMessage(phone, code);
        //调用消息发送
        messageSender.sendMessage(paramMap);
    }

    public Map<String, String> sendMessage(String phone, long code) {
        Map<String, String> messageMap = new HashMap<String, String>();
        messageMap.put("mobile", phone);       //手机号
        messageMap.put("signName", StaticProperty.SIGN_NAME);     //签名
        messageMap.put("templateCode", StaticProperty.TEMPLATE_CODE); //模板编号
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("code", String.valueOf(code));
        messageMap.put("templateParam", JSON.toJSONString(paramMap));//模板参数
        return messageMap;
    }
}
