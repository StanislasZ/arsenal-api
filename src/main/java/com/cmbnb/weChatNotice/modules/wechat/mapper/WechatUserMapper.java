package com.cmbnb.weChatNotice.modules.wechat.mapper;


import com.cmbnb.weChatNotice.modules.wechat.entity.WechatUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WechatUserMapper {

    int deleteAll();

    int updateDeleteFlag(boolean flag);

    int insertList(List<WechatUser> list);

    List<WechatUser> getWechatUserList(String name);

}
