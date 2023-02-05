package com.zrylovestan.arsenal.core.entity;

import lombok.Data;

@Data
public class Page {

    private Integer pageNum;  //页码

    private Integer pageRow;  //每页条数， 也是limit的第二个参数

    private Integer offSet;   //limit的第一个参数
}
