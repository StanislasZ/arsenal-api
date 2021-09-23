package com.cmbnb.weChatNotice.core.controller;

import com.alibaba.fastjson.JSONObject;

import com.cmbnb.weChatNotice.core.util.CommonUtils;

import com.cmbnt.owk.authority.service.UserService;
import com.cmbnt.owk.login.UserData;
import com.cmbnt.owk.web.control.Secured;
import com.cmbnt.owk.web.userdata.UserDataThreadHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;



    @Secured
    @PostMapping("/loginUserData")
    public Object getLoginUserData() {
        UserData userData = UserDataThreadHolder.get();
//        Asserts.notNull(userData, "找不到用户信息");
        JSONObject jo = new JSONObject();

        jo.put("name", userData.getUser().getName());
        jo.put("ystId", userData.getUser().getYstId());

        List<String> allRoles = userData.getRoles();
        List<String> roles = allRoles.stream().filter(role -> role.indexOf("LSXD") >= 0).collect(Collectors.toList());
        jo.put("roles", roles);


        LOGGER.info("登录用户信息 = {}", jo);
        return CommonUtils.successJson(jo);
    }




}
