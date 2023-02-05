package com.zrylovestan.arsenal.modules.wechat.mapper;


import com.zrylovestan.arsenal.modules.wechat.entity.WechatUser;
import com.zrylovestan.arsenal.modules.wechat.entity.request.WechatUserQo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WechatUserMapper {

    int deleteAll();

    int updateDeleteFlag(boolean flag);

    int insertList(List<WechatUser> list);


    //发送消息时，用
    List<WechatUser> getWechatUserListByName(String name);


    int getWechatUserCnt(WechatUserQo qo);

    //前端用
    List<WechatUser> getWechatUserList(WechatUserQo qo);


    List<WechatUser> getWechatUserExportList();
}
