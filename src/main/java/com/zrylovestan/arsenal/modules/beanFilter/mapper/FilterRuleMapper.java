package com.zrylovestan.arsenal.modules.beanFilter.mapper;

import com.zrylovestan.arsenal.modules.beanFilter.entity.FilterRule;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilterRuleMapper {

    List<FilterRule> getRulesByBusIdAndType(@Param("busId") String busId,
                                            @Param("ruleType") String ruleType);
}
