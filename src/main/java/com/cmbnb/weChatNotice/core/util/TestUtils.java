package com.cmbnb.weChatNotice.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestUtils {

    public static void main(String[] args) {



        List<String> list = new ArrayList<>();

        List<String> a= list.stream().filter(ele -> !ele.equals("a")).collect(Collectors.toList());

        System.out.println(a);

    }
}
