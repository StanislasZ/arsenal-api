package com.zrylovestan.arsenal.core.entity;


import lombok.Data;

import java.util.List;

/**
 * 查询带分页的列表时，返回给controller的类
 * @param <T>
 */
@Data
public class ObjListResp<T> {

    private List<T> list;
    private Integer totalCount;

}
