package com.zrylovestan.arsenal.modules.stu.entity.qo;

import com.zrylovestan.arsenal.core.entity.Page;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "学生请求类")
@Data
public class StuQo extends Page {

    private Long id;
}
