package com.qingmeng.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.entity.SysWearArticleRecord;
import com.qingmeng.mapper.SysWearArticleRecordMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-25 02:36:25
 */
@Service
public class SysWearArticleRecordDao extends ServiceImpl<SysWearArticleRecordMapper, SysWearArticleRecord>{

    /**
     * 根据用户id获取已佩戴物品id
     *
     * @param userId 用户 ID
     * @return {@link List }<{@link Long }>
     * @author qingmeng
     * @createTime: 2023/11/25 14:51:26
     */
    public List<Long> getWearListById(Long userId) {
        LambdaQueryWrapper<SysWearArticleRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysWearArticleRecord::getUserId, userId);
        return list(wrapper).stream().map(SysWearArticleRecord::getArticleId).collect(Collectors.toList());
    }

    /**
     * 根据物品类型 移除已穿戴的物品
     *
     * @param userId     用户 ID
     * @param type       类型
     * @author qingmeng
     * @createTime: 2023/11/25 15:04:41
     */
    public void removeWearArticle(Long userId, int type) {
        LambdaQueryWrapper<SysWearArticleRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysWearArticleRecord::getUserId, userId);
        wrapper.eq(SysWearArticleRecord::getArticleType, type);
        List<Long> idsList = list(wrapper).stream().map(SysWearArticleRecord::getId).collect(Collectors.toList());
        removeByIds(idsList);
    }
}
