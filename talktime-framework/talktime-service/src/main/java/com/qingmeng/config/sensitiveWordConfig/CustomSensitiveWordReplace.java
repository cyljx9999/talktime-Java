package com.qingmeng.config.sensitiveWordConfig;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.houbb.heaven.util.lang.CharUtil;
import com.github.houbb.sensitive.word.api.ISensitiveWordReplace;
import com.github.houbb.sensitive.word.api.ISensitiveWordReplaceContext;
import com.qingmeng.dao.SysSensitiveReplaceDao;
import com.qingmeng.entity.SysSensitiveReplace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 自定义敏感词过滤规则
 * @createTime 2023年05月12日 18:04:00
 */
@Configuration
@Slf4j
public class CustomSensitiveWordReplace implements ISensitiveWordReplace {
    @Resource
    private SysSensitiveReplaceDao sysSensitiveReplaceDao;


    @Override
    public String replace(ISensitiveWordReplaceContext context) {
        LambdaQueryWrapper<SysSensitiveReplace> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysSensitiveReplace::getIsOpen, 1);
        // todo 缓存
        List<SysSensitiveReplace> replaceList = sysSensitiveReplaceDao.list(wrapper);
        String sensitiveWord = context.sensitiveWord();
        // 判断敏感词是否为自定义替换内容
        List<SysSensitiveReplace> list = Optional.ofNullable(replaceList).orElse(new ArrayList<>()).stream()
                .filter(item -> item.getTargetName().equals(sensitiveWord))
                .collect(Collectors.toList());
        // 如果list不为空代表找到自定义替换内容，然后返回替换的名字
        if(CollUtil.isNotEmpty(list)){
            return list.get(0).getReplaceName();
        }
        // 其他默认使用 * 代替
        int wordLength = context.wordLength();
        return CharUtil.repeat('*', wordLength);
    }

}