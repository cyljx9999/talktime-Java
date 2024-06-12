package com.qingmeng.config.sensitiveWordConfig;


import com.github.houbb.sensitive.word.api.IWordAllow;
import com.qingmeng.dao.SysSensitiveWordDao;
import com.qingmeng.enums.sensitive.SensitiveTypeEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 自定义敏感词白名单
 * @createTime 2023年05月12日 17:55:00
 */
@Service
public class CustomWordAllow implements IWordAllow {
   @Resource
   private SysSensitiveWordDao sysSensitiveWordDao;

    /**
     * 敏感词白名单
     */
    @Override
    public List<String> allow() {
        return sysSensitiveWordDao.getSensitiveWordsListWithType(SensitiveTypeEnum.WHITE.getCode());
    }

}
