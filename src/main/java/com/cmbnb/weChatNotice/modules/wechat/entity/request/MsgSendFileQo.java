package com.cmbnb.weChatNotice.modules.wechat.entity.request;

import com.cmbnb.weChatNotice.modules.wechat.entity.WeChatFile;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class MsgSendFileQo extends MsgSendQo {

    private WeChatFile file;
}
