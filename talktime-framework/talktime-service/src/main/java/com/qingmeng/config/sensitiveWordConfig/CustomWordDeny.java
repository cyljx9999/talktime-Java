package com.qingmeng.config.sensitiveWordConfig;

import com.github.houbb.sensitive.word.api.IWordDeny;
import com.qingmeng.dao.SysSensitiveWordDao;
import com.qingmeng.enums.sensitive.SensitiveTypeEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 自定义敏感词黑名单
 * @createTime 2023年05月12日 17:56:00
 */
@Service
public class CustomWordDeny implements IWordDeny {
    @Resource
    private SysSensitiveWordDao sysSensitiveWordDao;

    /**
     * 敏感词黑名单
     */
    @Override
    public List<String> deny() {
        return sysSensitiveWordDao.getSensitiveWordsListWithType(SensitiveTypeEnum.BLACK.getCode());
    }

}