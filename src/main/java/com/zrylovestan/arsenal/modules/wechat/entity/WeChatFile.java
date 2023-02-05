package com.zrylovestan.arsenal.modules.wechat.entity;

import lombok.Data;

@Data
public class WeChatFile {

    private String media_id;


    public WeChatFile(String media_id) {
        this.media_id = media_id;
    }

    public WeChatFile() {
    }
}
