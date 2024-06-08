package com.qingmeng.service.impl;

import com.qingmeng.config.adapt.ArticleAdapt;
import com.qingmeng.config.cache.ArticleCache;
import com.qingmeng.dao.SysUserArticleDao;
import com.qingmeng.entity.SysArticle;
import com.qingmeng.entity.SysUserArticle;
import com.qingmeng.enums.article.ArticleTypeEnum;
import com.qingmeng.service.SysUserArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月10日 11:18:00
 */
@Service
public class SysUserArticleServiceImpl implements SysUserArticleService {
    @Resource
    private ArticleCache articleCache;
    @Resource
    private SysUserArticleDao sysUserArticleDao;

    /**
     * 物品接收
     *
     * @param fromUserId 从用户 ID
     * @param itemId     商品 ID
     * @param string     字符串
     * @author qingmeng
     * @createTime: 2024/06/08 16:42:24
     */
    @Override
    public void itemReceive(Long fromUserId, Long itemId, String string) {
        SysArticle article = articleCache.get(itemId);
        if (ArticleTypeEnum.BADGE.getCode().equals(article.getArticleType())) {
            Long countByValidItemId = sysUserArticleDao.getCountByValidItemId(fromUserId, itemId);
            // 已经有徽章了不发
            if (countByValidItemId > 0) {
                return;
            }
        }
        // 发物品
        SysUserArticle insert = ArticleAdapt.getSysUserArticle(itemId, fromUserId);
        sysUserArticleDao.save(insert);
        // todo 通知用户
    }
}
