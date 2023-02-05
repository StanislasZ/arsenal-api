package com.zrylovestan.arsenal.modules.wechat.entity.request;

import com.zrylovestan.arsenal.modules.wechat.entity.WeChatText;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class MsgSendTextQo extends MsgSendQo {

    private WeChatText text;
}
