package com.qingmeng.service.impl;

import com.qingmeng.config.adapt.ArticleAdapt;
import com.qingmeng.dao.SysWearArticleRecordDao;
import com.qingmeng.entity.SysWearArticleRecord;
import com.qingmeng.service.SysWearArticleRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月25日 14:37:00
 */
@Service
public class SysWearArticleRecordServiceImpl implements SysWearArticleRecordService {
    @Resource
    private SysWearArticleRecordDao sysWearArticleRecordDao;

    /**
     * 佩戴物品
     *
     * @param userId     用户 ID
     * @param articleIds 物品 ID
     * @param type       类型
     * @author qingmeng
     * @createTime: 2023/11/25 14:56:44
     */
    @Override
    public void wearArticle(Long userId, List<Long> articleIds, int type) {
        sysWearArticleRecordDao.removeWearArticle(userId, type);
        List<SysWearArticleRecord> saveList = ArticleAdapt.buildSaveWearArticle(userId, articleIds, type);
        sysWearArticleRecordDao.saveBatch(saveList);
    }
}
