package com.scsa.workshop7;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class Util {
    public static final int INSERT = 1;
    public static final int EDIT = 2;

    public static String getFormattedDate(long time) {
        //format 설정
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss E", Locale.KOREA);
        //TimeZone  설정 (GMT +9)
        format.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));

        //현재시간에 적용
        return format.format(time);
    }
}
