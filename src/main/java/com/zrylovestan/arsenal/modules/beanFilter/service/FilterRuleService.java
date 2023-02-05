package com.zrylovestan.arsenal.modules.beanFilter.service;

import com.alibaba.fastjson.JSON;
import com.zrylovestan.arsenal.modules.beanFilter.entity.FilterRule;
import com.zrylovestan.arsenal.modules.beanFilter.mapper.FilterRuleMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

@Service
@Slf4j
public class FilterRuleService {

    @Resource
    FilterRuleMapper filterRuleMapper;

    public List<FilterRule> getRulesByBusIdAndType(String busId, String type) {
        return filterRuleMapper.getRulesByBusIdAndType(busId, type);
    }



    /**
     * 根据规则列表， 获取指定类的过滤器
     * @param ruleList
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Predicate<T> getFilter(List<FilterRule> ruleList, Class<T> clazz) {
        Predicate<T> initPredicate = (e -> true);
        for (FilterRule rule : ruleList) {
            System.out.println("进入 rule: " + JSON.toJSONString(rule));
            Predicate<T> tmpPredicate = t -> {

                try {
                    String fieldValue = BeanUtils.getProperty(t, rule.getTargetField());
                    Object fieldObj = PropertyUtils.getProperty(t, rule.getTargetField());
                    if (fieldValue == null) {
                        log.warn("值为空，返回false");
                        return false;
                    }
                    String className = PropertyUtils.getProperty(t, rule.getTargetField()).getClass().getName();
                    if (className.equals("java.lang.Integer")) {

                        if (rule.getOperator().equals(">")) {
                            return JSON.parseObject(fieldValue, Integer.class) > JSON.parseObject(rule.getOpValue(), Integer.class);
                        } else if (rule.getOperator().equals(">=")) {
                            return JSON.parseObject(fieldValue, Integer.class) >= JSON.parseObject(rule.getOpValue(), Integer.class);
                        } else if (rule.getOperator().equals("<")) {
                            return JSON.parseObject(fieldValue, Integer.class) < JSON.parseObject(rule.getOpValue(), Integer.class);
                        } else if (rule.getOperator().equals("<=")) {
                            return JSON.parseObject(fieldValue, Integer.class) <= JSON.parseObject(rule.getOpValue(), Integer.class);
                        } else if (rule.getOperator().equals("==")) {
                            return JSON.parseObject(fieldValue, Integer.class) == JSON.parseObject(rule.getOpValue(), Integer.class);
                        } else {
                            log.warn("Integer 无此操作符: {} 的过滤, 放弃此规则，return true", rule.getOperator());
                            return true;
                        }


                    } else if (className.equals("java.lang.String")) {


                        if (rule.getOperator().equals(">")) {
                            return fieldValue.compareTo(rule.getOpValue()) > 0;
                        } else if (rule.getOperator().equals(">=")) {
                            return fieldValue.compareTo(rule.getOpValue()) >= 0;
                        } else if (rule.getOperator().equals("<")) {
                            return fieldValue.compareTo(rule.getOpValue()) < 0;
                        } else if (rule.getOperator().equals("<=")) {
                            return fieldValue.compareTo(rule.getOpValue()) <= 0;
                        } else if (rule.getOperator().equals("==")) {
                            return fieldValue.compareTo(rule.getOpValue()) == 0;
                        } else if (rule.getOperator().equals("rlike")) {
                            return fieldValue.indexOf(rule.getOpValue()) >= 0;
                        } else {
                            log.warn("String 无此操作符: {} 的过滤, 放弃此规则，return true", rule.getOperator());
                            return true;
                        }
                    } else if (className.equals("java.util.Date")) {
                        Date d1 = (Date) fieldObj;
                        Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse(rule.getOpValue());
                        if (rule.getOperator().equals(">")) {
                            return d1.after(d2);
                        } else if (rule.getOperator().equals(">=")) {
                            return !d1.before(d2);
                        }else if (rule.getOperator().equals("<")) {
                            return d1.before(d2);
                        } else if (rule.getOperator().equals("<=")) {
                            return !d1.after(d2);
                        } else if (rule.getOperator().equals("==")) {
                            return !d1.before(d2) && !d1.after(d2);
                        } else {
                            log.warn(" Date 无此操作符: {} 的过滤, 放弃此规则，return true", rule.getOperator());
                            return true;
                        }



                    } else if (className.equals("java.time.LocalDate")) {
                        LocalDate d1 = (LocalDate) fieldObj;
                        LocalDate ld = LocalDate.parse(rule.getOpValue(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                        if (rule.getOperator().equals(">")) {
                            return d1.isAfter(ld);
                        } else if (rule.getOperator().equals(">=")) {
                            return !d1.isBefore(ld);
                        }else if (rule.getOperator().equals("<")) {
                            return d1.isBefore(ld);
                        } else if (rule.getOperator().equals("<=")) {
                            return !d1.isAfter(ld);
                        } else if (rule.getOperator().equals("==")) {
                            return !d1.isEqual(ld);
                        } else {
                            log.warn(" LocalDate 无此操作符: {} 的过滤, 放弃此规则，return true", rule.getOperator());
                            return true;
                        }


                    } else {
                        log.warn("对此类型： {} 暂无过滤逻辑，置为return true", fieldValue.getClass().getName());
                        return true;
                    }

                } catch (Exception e) {
                    log.error("出错");
                    log.error(e.getMessage(), e);
                    return true;
                }

            };
            initPredicate = initPredicate.and(tmpPredicate);
        }
        return initPredicate;
    }

}
