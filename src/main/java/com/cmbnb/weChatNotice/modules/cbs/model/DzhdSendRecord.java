package com.cmbnb.weChatNotice.modules.cbs.model;

import lombok.Data;

import java.util.Date;

/**
 * 电子回单发送记录
 */
@Data
public class DzhdSendRecord {

    private Integer id;

    private String bnkFlw;

    private String sendDate;


}
