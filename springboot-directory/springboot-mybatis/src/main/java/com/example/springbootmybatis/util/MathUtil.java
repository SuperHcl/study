package com.example.springbootmybatis.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hu.ChangLiang
 * @date 2021/12/16 3:14 下午
 */
public class MathUtil {
    public static void main(String[] args) {
        BigDecimal b = new BigDecimal("3.231");
        BigDecimal bigDecimal = b.setScale(2, BigDecimal.ROUND_HALF_UP);
        System.out.println(b);
        System.out.println(bigDecimal);

        System.out.println(getMonth());

        int count = (int) Math.ceil(1000 / 2000d);
        System.out.println(count);

        Map<String, String> map = new HashMap<>();
        map.put("1", "A");
        map.put("2", "B");
        map.put("3", "C");

        System.out.println(map.get("2"));
    }
    public static String getMonth() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.MONTH, -1);
        Date month = rightNow.getTime();
        return format.format(month);
    }

}
