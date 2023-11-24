package com.qingmeng.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.entity.SysUserArticle;
import com.qingmeng.mapper.SysUserArticleMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户物品关联表 服务实现类
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-08 10:43:38
 */
@Service
public class SysUserArticleDao extends ServiceImpl<SysUserArticleMapper, SysUserArticle> {

    /**
     * 按用户 ID 获取物品 ID 列表
     *
     * @param userId 用户id
     * @return {@link List }<{@link Long }>
     * @author qingmeng
     * @createTime: 2023/11/24 20:59:19
     */
    public List<Long> getArticleIdListByUserId(Long userId) {
        LambdaQueryWrapper<SysUserArticle> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserArticle::getUserId, userId);
        return list(wrapper).stream().map(SysUserArticle::getArticleId).collect(Collectors.toList());
    }

    /**
     * 根据物品id和userId查询 拥有信息
     *
     * @param userId    用户 ID
     * @param articleId 物品 ID
     * @return {@link SysUserArticle }
     * @author qingmeng
     * @createTime: 2023/11/24 22:20:57
     */
    public SysUserArticle getUserArticle(Long userId, Long articleId) {
        LambdaQueryWrapper<SysUserArticle> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserArticle::getUserId, userId);
        wrapper.eq(SysUserArticle::getArticleId, articleId);
        return getOne(wrapper);
    }
}
