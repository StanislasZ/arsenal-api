package com.zrylovestan.arsenal.modules.stu.controller;

import com.zrylovestan.arsenal.core.entity.Result;
import com.zrylovestan.arsenal.core.util.CommonUtils;
import com.zrylovestan.arsenal.modules.stu.entity.Stu;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@Slf4j
@Api(tags = "学生接口类")
public class StuController {


    @ApiOperation(value = "获取学生", notes = "测试用")
    @RequestMapping("/getTestStu")
    public Result getTestStu() {
        Stu stu = new Stu();
        stu.setName("stan");
        stu.setAge(20);
        stu.setBirth(LocalDate.of(2000,11,20));
        return Result.success(stu);
    }

    @ApiOperation(value = "根据id获取学生", notes = "测试用")
    @RequestMapping(value = "/getStu/{id}")
    public Result getStu(@PathVariable(value = "id") Long id) {
        System.out.println("getStu... id = " + id);
        Stu stu = Stu.getTestStu();
        stu.setId(id);
        return Result.success(stu);
    }

}
