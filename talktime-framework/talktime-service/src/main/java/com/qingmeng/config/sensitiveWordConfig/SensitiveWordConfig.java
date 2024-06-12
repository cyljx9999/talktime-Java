package com.qingmeng.config.sensitiveWordConfig;


import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.github.houbb.sensitive.word.support.allow.WordAllows;
import com.github.houbb.sensitive.word.support.deny.WordDenys;
import com.qingmeng.dao.SysSensitiveWordConfigDao;
import com.qingmeng.entity.SysSensitiveWordConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 自定义敏感词过滤配置
 * @createTime 2023年05月12日 17:55:00
 */
@Configuration
public class SensitiveWordConfig {

    @Resource
    private CustomWordAllow customWordAllow;
    @Resource
    private CustomWordDeny customWordDeny;
    @Resource
    private SysSensitiveWordConfigDao sysSensitiveWordConfigDao;


    /**
     * 初始化引导类
     *
     * @return 初始化引导类
     * @since 1.0.0
     */
    @Bean
    public SensitiveWordBs sensitiveWordBs() {
        SysSensitiveWordConfig config = sysSensitiveWordConfigDao.list(null).get(0);
        // 可根据数据库数据判断 动态增加配置
        return SensitiveWordBs.newInstance()
                // 设置黑名单
                .wordDeny(WordDenys.chains(customWordDeny))
                // 设置白名单
                .wordAllow(WordAllows.chains(customWordAllow))
                // 忽略大小写
                .ignoreCase(config.getIgnoreCase())
                // 忽略半角圆角
                .ignoreWidth(config.getIgnoreWidth())
                // 忽略数字的写法
                .ignoreNumStyle(config.getIgnoreNumStyle())
                // 忽略中文的书写格式
                .ignoreChineseStyle(config.getIgnoreChineseStyle())
                // 忽略英文的书写格式
                .ignoreEnglishStyle(config.getIgnoreEnglishStyle())
                // 忽略重复词
                .ignoreRepeat(config.getIgnoreRepeat())
                // 是有启用邮箱检测
                .enableEmailCheck(config.getEnableEmailCheck())
                // 是否启用链接检测
                .enableUrlCheck(config.getEnableUrlCheck())
                // 是否启用数字检测
                .enableNumCheck(config.getEnableNumCheck())
                // 数字检测多少位
                .numCheckLen(config.getNumCheckLen())
                // 各种其他配置
                .init();
    }

}