package com.zrylovestan.arsenal.modules.stu.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@ApiModel(value = "学生实体类")
public class Stu {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "生日")
    private LocalDate birth;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


    public static Stu getTestStu() {
        Stu stu = new Stu();
        stu.setName("stan");
        stu.setAge(20);
        stu.setBirth(LocalDate.of(2000,11,20));
        return stu;
    }

}
