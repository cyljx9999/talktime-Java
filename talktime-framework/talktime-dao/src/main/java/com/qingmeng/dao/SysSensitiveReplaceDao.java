package com.qingmeng.dao;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.entity.SysSensitiveReplace;
import com.qingmeng.enums.common.OpenStatusEnum;
import com.qingmeng.mapper.SysSensitiveReplaceMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年05月14日 13:45:00
 */
@Service
public class SysSensitiveReplaceDao extends ServiceImpl<SysSensitiveReplaceMapper, SysSensitiveReplace> {

    /**
     * 获取已启动列表
     *
     * @return {@link List }<{@link SysSensitiveReplace }>
     * @author qingmeng
     * @createTime: 2024/06/15 18:10:49
     */
    public List<SysSensitiveReplace> getListWithOpen() {
        return lambdaQuery().eq(SysSensitiveReplace::getIsOpen, OpenStatusEnum.OPEN.getCode()).list();
    }
}
