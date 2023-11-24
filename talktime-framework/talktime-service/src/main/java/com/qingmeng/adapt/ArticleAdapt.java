package com.qingmeng.adapt;

import com.qingmeng.entity.SysArticle;
import com.qingmeng.vo.article.SysArticleListVO;
import com.qingmeng.vo.article.SysArticleVO;

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
     * @return {@link SysArticleListVO }
     * @author qingmeng
     * @createTime: 2023/11/24 21:09:20
     */
    public static SysArticleListVO buildSysArticleVO(List<SysArticle> sysArticleList,List<Long> articleIds){
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
        return vo;
    }

}
