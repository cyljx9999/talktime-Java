package com.qingmeng.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.qingmeng.config.adapt.ArticleAdapt;
import com.qingmeng.config.cache.ArticleCache;
import com.qingmeng.dao.SysUserArticleDao;
import com.qingmeng.dao.SysWearArticleRecordDao;
import com.qingmeng.dto.article.WearArticleDTO;
import com.qingmeng.entity.SysArticle;
import com.qingmeng.entity.SysUserArticle;
import com.qingmeng.enums.article.ArticleTypeEnum;
import com.qingmeng.service.SysArticleService;
import com.qingmeng.service.SysWearArticleRecordService;
import com.qingmeng.utils.AssertUtils;
import com.qingmeng.vo.article.SysArticleListVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月10日 11:17:00
 */
@Service
public class SysArticleServiceImpl implements SysArticleService {
    @Resource
    private ArticleCache articleCache;
    @Resource
    private SysUserArticleDao sysUserArticleDao;
    @Resource
    private SysWearArticleRecordService sysWearArticleRecordService;
    @Resource
    private SysWearArticleRecordDao sysWearArticleRecordDao;

    /**
     * 获取物品展示列表
     *
     * @return {@link SysArticleListVO }
     * @author qingmeng
     * @createTime: 2023/11/24 21:10:42
     */
    @Override
    public SysArticleListVO getArticleDisplayList() {
        Long userId = StpUtil.getLoginIdAsLong();
        // 查询物品列表
        List<SysArticle> sysArticles = articleCache.getList();
        // 查询拥有的列表
        List<Long> haveArticleIds = sysUserArticleDao.getArticleIdListByUserId(userId);
        // 查询已佩戴物品列表
        List<Long> wearArticleIds = sysWearArticleRecordDao.getWearListById(userId);
        return ArticleAdapt.buildSysArticleVO(sysArticles, haveArticleIds, wearArticleIds);
    }


    /**
     * 佩戴头像边框
     *
     * @param userId         用户 ID
     * @param wearArticleDTO DTO
     * @author qingmeng
     * @createTime: 2023/11/24 22:01:54
     */
    @Override
    public void wearHeadBorder(Long userId, WearArticleDTO wearArticleDTO) {
        preCheck(userId, wearArticleDTO, ArticleTypeEnum.HEAD_BORDER.getCode());
        sysWearArticleRecordService.wearArticle(userId, wearArticleDTO.getArticleIds(), ArticleTypeEnum.HEAD_BORDER.getCode());
    }

    /**
     * 佩戴徽章
     *
     * @param userId         用户 ID
     * @param wearArticleDTO DTO
     * @author qingmeng
     * @createTime: 2023/11/24 22:30:18
     */
    @Override
    public void wearBadge(Long userId, WearArticleDTO wearArticleDTO) {
        preCheck(userId, wearArticleDTO, ArticleTypeEnum.BADGE.getCode());
        sysWearArticleRecordService.wearArticle(userId, wearArticleDTO.getArticleIds(), ArticleTypeEnum.BADGE.getCode());
    }

    /**
     * 预检
     *
     * @param userId         用户 ID
     * @param wearArticleDTO DTO
     * @param type           类型
     * @author qingmeng
     * @createTime: 2023/11/24 22:12:55
     */
    private void preCheck(Long userId, WearArticleDTO wearArticleDTO, int type) {
        List<SysUserArticle> userArticles = sysUserArticleDao.getUserArticleByIds(userId, wearArticleDTO.getArticleIds());
        userArticles.forEach(item -> {
            AssertUtils.isNull(item, "尚未拥有该物品");
        });
        List<SysArticle> collection = new ArrayList<>(articleCache.getBatch(wearArticleDTO.getArticleIds()).values());
        collection.forEach(article -> {
            if (Objects.equals(type, ArticleTypeEnum.BADGE.getCode())) {
                AssertUtils.equal(article.getArticleType(), type, "请选择相关类型物品进行佩戴");
            } else if (Objects.equals(type, ArticleTypeEnum.HEAD_BORDER.getCode())) {
                AssertUtils.equal(article.getArticleType(), type, "请选择相关类型物品进行佩戴");
            }
        });
    }
}
