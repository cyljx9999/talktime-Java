package com.qingmeng.serialize;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.DesensitizedUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.qingmeng.annotation.Desensitization;
import com.qingmeng.enums.DesensitizationEnum;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Objects;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月08日 15:13:00
 */
@AllArgsConstructor
@NoArgsConstructor
public class DesensitizationSerialize extends JsonSerializer implements ContextualSerializer {
    /**
     * 脱敏类型
     */
    private DesensitizationEnum type;

    /**
     * 定义反序列化开始位置
     */
    private Integer start;

    /**
     * 定义反序列化结束位置
     */
    private Integer end;
    @Override
    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String value = (String) o;
        switch (type) {
            //userId
            case USER_ID:
                jsonGenerator.writeString(String.valueOf(DesensitizedUtil.desensitized(value, DesensitizedUtil.DesensitizedType.USER_ID)));
                break;
            //中文名
            case CHINESE_NAME:
                jsonGenerator.writeString(DesensitizedUtil.desensitized(value, DesensitizedUtil.DesensitizedType.CHINESE_NAME));
                break;
            //身份证号
            case ID_CARD:
                jsonGenerator.writeString(DesensitizedUtil.desensitized(value, DesensitizedUtil.DesensitizedType.ID_CARD));
                break;
            //座机
            case FIXED_PHONE:
                jsonGenerator.writeString(DesensitizedUtil.desensitized(value, DesensitizedUtil.DesensitizedType.FIXED_PHONE));
                break;
            //手机号
            case MOBILE_PHONE:
                jsonGenerator.writeString(DesensitizedUtil.desensitized(value, DesensitizedUtil.DesensitizedType.MOBILE_PHONE));
                break;
            //地址
            case ADDRESS:
                jsonGenerator.writeString(DesensitizedUtil.desensitized(value, DesensitizedUtil.DesensitizedType.ADDRESS));
                break;
            //邮箱
            case EMAIL:
                jsonGenerator.writeString(DesensitizedUtil.desensitized(value, DesensitizedUtil.DesensitizedType.EMAIL));
                break;
            case BANK_CARD:
                jsonGenerator.writeString(DesensitizedUtil.desensitized(value, DesensitizedUtil.DesensitizedType.BANK_CARD));
                break;
            //密码
            case PASSWORD:
                jsonGenerator.writeString(DesensitizedUtil.desensitized(value, DesensitizedUtil.DesensitizedType.PASSWORD));
                break;
            //中国大陆车牌号
            case CAR_LICENSE:
                jsonGenerator.writeString(DesensitizedUtil.desensitized(value, DesensitizedUtil.DesensitizedType.CAR_LICENSE));
                break;
            //自定义
            case CUSTOM:
                jsonGenerator.writeString(CharSequenceUtil.hide(value, start, end));
                break;
            default:
                break;
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        if (Objects.nonNull(beanProperty)) {
            //判断是否为string类型
            if (Objects.equals(beanProperty.getType().getRawClass(), String.class)) {
                Desensitization anno = beanProperty.getAnnotation(Desensitization.class);
                if (Objects.isNull(anno)) {
                    anno = beanProperty.getContextAnnotation(Desensitization.class);
                }
                if (Objects.nonNull(anno)) {
                    //返回反序列化类型
                    return new DesensitizationSerialize(anno.type(), anno.start(), anno.end());
                }
            }
            //返回默认反序列化类型
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
        //返回默认反序列化类型
        return serializerProvider.findNullValueSerializer(null);
    }
}