package com.commerce.core.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String getNowDate(String pattern) {
        return dateToStr(new Date(), pattern);
    }

    public static String dateToStr(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }
}
