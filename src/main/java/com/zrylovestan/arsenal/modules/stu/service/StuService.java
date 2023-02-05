package com.zrylovestan.arsenal.modules.stu.service;

import com.zrylovestan.arsenal.modules.stu.entity.Stu;
import com.zrylovestan.arsenal.modules.stu.mapper.StuMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class StuService {

    @Resource
    StuMapper stuMapper;

    public List<Stu> getStuList() {
        return stuMapper.getStuList();
    }
}
