package com.cmbnb.weChatNotice.modules.wechat.entity.request;


import com.cmbnb.weChatNotice.modules.wechat.entity.WeChatFile;
import lombok.Data;


/**
 * 发送文件到群聊
 */
@Data
public class MsgSendFileToGroupQo {

    private String chatid;

    private String msgtype;

    private WeChatFile file;

    private Integer safe;
}
