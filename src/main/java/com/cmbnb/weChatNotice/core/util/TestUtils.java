package com.cmbnb.weChatNotice.core.util;

public class TestUtils {

    public static void main(String[] args) {


//        String s = "A050008";
//        String s = "2011-55555/201266666、2013-5444";
        String s = "2011-55555,123，897、444/666";

        String[] arr = s.split("/|、|，|,");
        System.out.println(arr.length);
        for (String s1 : arr) {
            System.out.println(s1);
        }

    }
}
