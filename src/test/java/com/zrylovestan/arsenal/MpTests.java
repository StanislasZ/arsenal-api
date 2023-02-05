package com.zrylovestan.arsenal;

import com.zrylovestan.arsenal.modules.user.entity.User;
import com.zrylovestan.arsenal.modules.user.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MpTests {

    @Resource
    UserMapper userMapper;

    @Test
    public void testSelectUser() {
        System.out.println("-----test mp----");
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);

    }
}
