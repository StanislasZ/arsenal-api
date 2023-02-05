package com.zrylovestan.arsenal.modules.stu.mapper;

import com.zrylovestan.arsenal.modules.stu.entity.Stu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StuMapper {

    List<Stu> getStuList();


}
