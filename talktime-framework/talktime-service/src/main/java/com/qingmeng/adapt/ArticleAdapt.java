package com.qingmeng.adapt;

import com.qingmeng.entity.SysArticle;
import com.qingmeng.entity.SysWearArticleRecord;
import com.qingmeng.vo.article.SysArticleListVO;
import com.qingmeng.vo.article.SysArticleVO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月24日 20:45:00
 */
public class ArticleAdapt {

    /**
     * 构建物品返回类
     *
     * @param sysArticleList 物品列表
     * @param articleIds     物品 ID
     * @param wearArticleIds 穿戴物品 ID
     * @return {@link SysArticleListVO }
     * @author qingmeng
     * @createTime: 2023/11/25 14:54:02
     */
    public static SysArticleListVO buildSysArticleVO(List<SysArticle> sysArticleList,List<Long> articleIds,List<Long> wearArticleIds){
        SysArticleListVO vo = new SysArticleListVO();
        List<SysArticleVO> vos = sysArticleList.stream().map(item -> {
            SysArticleVO sysArticleVO = new SysArticleVO();
            sysArticleVO.setId(item.getId());
            sysArticleVO.setArticleType(item.getArticleType());
            sysArticleVO.setArticleUrl(item.getArticleUrl());
            sysArticleVO.setArticleDescribe(item.getArticleDescribe());
            return sysArticleVO;
        }).collect(Collectors.toList());
        vo.setArticleListList(vos);
        vo.setAlreadyHadIds(articleIds);
        vo.setWearArticleIds(wearArticleIds);
        return vo;
    }

    /**
     * 构造批量存储佩戴物品记录
     *
     * @param userId     用户 ID
     * @param articleIds 文章 ID
     * @param type       类型
     * @return {@link List }<{@link SysWearArticleRecord }>
     * @author qingmeng
     * @createTime: 2023/11/25 15:08:10
     */
    public static List<SysWearArticleRecord> buildSaveWearArticle(Long userId, List<Long> articleIds, int type) {
        List<SysWearArticleRecord> list = new ArrayList<>();
        articleIds.forEach(articleId -> {
            SysWearArticleRecord record = new SysWearArticleRecord();
            record.setArticleId(articleId);
            record.setUserId(userId);
            record.setArticleType(type);
            list.add(record);
        });
        return list;
    }
}
