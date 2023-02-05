package com.zrylovestan.arsenal.modules.wechat.entity.response;


import lombok.Data;

@Data
public class MediaUploadResp {

    private Integer errcode;

    private String errmsg;

    private String type;   //文件类型

    private String media_id;   //媒体文件上传后获取的唯一标识

    private String created_at;   //上传时间戳


}
