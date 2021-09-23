package com.cmbnb.weChatNotice.core.util;


import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {


    public static boolean isToday(String dateStr)  {
        if (StringUtils.isEmpty(dateStr)) return false;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date d = sdf.parse(dateStr);
            Date now = new Date();
            return sdf.format(d).equals(sdf.format(now));

        } catch (ParseException ex) {
            ex.printStackTrace();
            return false;
        }


    }



    public static void main(String[] args) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }
}
