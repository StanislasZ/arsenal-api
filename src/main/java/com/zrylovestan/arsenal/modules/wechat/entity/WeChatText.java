package com.zrylovestan.arsenal.modules.wechat.entity;

import lombok.Data;

@Data
public class WeChatText {

    private String content;


    public WeChatText() {

    }

    public WeChatText(String content) {
        this.content = content;
    }

}
