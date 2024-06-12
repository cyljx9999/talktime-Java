package com.qingmeng.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.entity.SysSensitiveWord;
import com.qingmeng.enums.common.OpenStatusEnum;
import com.qingmeng.mapper.SysSensitiveWordMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年05月12日 20:57:00
 */
@Service
public class SysSensitiveWordDao extends ServiceImpl<SysSensitiveWordMapper, SysSensitiveWord> {
    /**
     * 获取带有类型敏感词列表
     *
     * @param type 类型
     * @return {@link List }<{@link String }>
     * @author qingmeng
     * @createTime: 2024/06/12 17:42:42
     */
    public List<String> getSensitiveWordsListWithType(int type) {
        LambdaQueryWrapper<SysSensitiveWord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysSensitiveWord::getIsOpen, OpenStatusEnum.OPEN.getCode());
        wrapper.eq(SysSensitiveWord::getSensitiveWordType, type);
        List<SysSensitiveWord> sysSensitiveWords = this.list(wrapper);
        return sysSensitiveWords.stream().map(SysSensitiveWord::getSensitiveWord).collect(Collectors.toList());
    }
}
