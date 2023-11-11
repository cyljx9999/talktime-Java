package com.qingmeng.valid.custom;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 自定义校验注解--Integer类型数值状态校验
 * @createTime 2023年11月10日 22:20:07
 */
@Documented
@Constraint(validatedBy = { IntListValueConstraint.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface StingListValue {
    String message() default "该字段只能传指定类型数据";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] values() default {};
}