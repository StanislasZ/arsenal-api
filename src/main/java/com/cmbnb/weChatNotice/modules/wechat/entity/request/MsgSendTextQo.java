package com.cmbnb.weChatNotice.modules.wechat.entity.request;

import com.cmbnb.weChatNotice.modules.wechat.entity.WeChatText;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class MsgSendTextQo extends MsgSendQo {

    private WeChatText text;
}
