package com.qingmeng.valid.custom;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description ListValue校验器
 * @createTime 2023年11月10日 22:22:37
 */
public class StingListValueConstraint implements ConstraintValidator<StingListValue,String> {

    private final Set<String> set = new HashSet<>();

    /**
     * 初始化方法
     *
     */
    @Override
    public void initialize(StingListValue constraintAnnotation) {
        String[] values = constraintAnnotation.values();
        set.addAll(Arrays.asList(values));
    }

    /**
     * 判断是否校验成功
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
         return set.contains(value);
    }

}