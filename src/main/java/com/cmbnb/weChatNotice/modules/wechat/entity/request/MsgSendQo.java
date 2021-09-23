package com.cmbnb.weChatNotice.modules.wechat.entity.request;

import lombok.Data;

/**
 * 发送应用消息 请求类   基本类
 */
@Data
public class MsgSendQo {

    private String touser;

    private String toparty = "@all";

    private String totag = "@all";

    private String msgtype;

    private Integer agentid;

    private Boolean safe;  //是否保密   0：可对外分享    1：不能分享且内容显示水印

    private Boolean enable_id_trans;  //是否开启id转译  0：否   1：是

    private Boolean enable_duplicate_check;  //是否开启重复消息检查 0：否  1：是

    private Integer duplicate_check_inteval;   //重复消息检查的时间间隔 默认1800s，最大不超过4小时


}
