package com.cmbnb.weChatNotice;

import com.cmbnb.weChatNotice.core.util.DateUtil;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTest {

    @Test
    public void testIsToday() throws ParseException {



        String s = "2021-09-17 16:21:31.0";
//        String s = "  ";

        System.out.println(DateUtil.isToday(s));



    }
}
