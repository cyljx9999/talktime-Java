package com.qingmeng.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 敏感词检测配置
 * @createTime 2023年05月12日 19:02:00
 */
@Data
@TableName("sys_sensitive_word_config")
public class SysSensitiveWordConfig implements Serializable {
    private static final long serialVersionUID = -7139867137009608098L;

    @TableId(type = IdType.AUTO)
    private Long sensitiveWordConfigId;

    /**
     * 忽略大小写
     */
    private Boolean ignoreCase;

    /**
     * 忽略半角圆角
     */
    private Boolean ignoreWidth;

    /**
     * 忽略数字的写法
     */
    private Boolean ignoreNumStyle;

    /**
     * 忽略中文的书写格式
     */
    private Boolean ignoreChineseStyle;

    /**
     * 忽略英文的书写格式
     */
    private Boolean ignoreEnglishStyle;

    /**
     * 忽略重复词
     */
    private Boolean ignoreRepeat;

    /**
     * 是有启用邮箱检测
     */
    private Boolean enableEmailCheck;

    /**
     * 是否启用链接检测
     */
    private Boolean enableUrlCheck;

    /**
     * 是否启用数字检测
     */
    private Boolean enableNumCheck;


    /**
     * 检测多少位数字
     */
   private Integer numCheckLen;


}
