package com.zrylovestan.arsenal.modules.user.service.impl;

import com.zrylovestan.arsenal.modules.user.entity.User;
import com.zrylovestan.arsenal.modules.user.mapper.UserMapper;
import com.zrylovestan.arsenal.modules.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2023-02-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
