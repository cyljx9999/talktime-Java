package com.qingmeng.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.entity.SysUserApply;
import com.qingmeng.enums.user.ApplyStatusEnum;
import com.qingmeng.enums.chat.ReadStatusEnum;
import com.qingmeng.mapper.SysUserApplyMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 好友申请表 服务实现类
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-08 10:43:38
 */
@Service
public class SysUserApplyDao extends ServiceImpl<SysUserApplyMapper, SysUserApply> {

    /**
     * 同意申请
     *
     * @param applyId 申请 ID
     * @author qingmeng
     * @createTime: 2023/11/28 23:23:50
     */
    public void agreeApply(Long applyId) {
        lambdaUpdate().set(SysUserApply::getApplyStatus, ApplyStatusEnum.ACCEPT.getCode())
                .eq(SysUserApply::getId, applyId)
                .update(new SysUserApply());
    }

    /**
     * 根据Id获取好友申请列表
     *
     * @param userId 用户 ID
     * @param page   页
     * @return {@link IPage }<{@link SysUserApply }>
     * @author qingmeng
     * @createTime: 2023/11/29 08:17:45
     */
    public IPage<SysUserApply> getFriendApplyPageListByUserId(Long userId, IPage<SysUserApply> page) {
        return lambdaQuery()
                .eq(SysUserApply::getTargetId, userId)
                .ne(SysUserApply::getApplyStatus, ApplyStatusEnum.BLOCK.getCode())
                .orderByDesc(SysUserApply::getCreateTime)
                .page(page);
    }


    /**
     * 阅读申请列表
     *
     * @param userId   用户 ID
     * @param applyIds 申请 ID
     * @author qingmeng
     * @createTime: 2023/11/29 08:34:22
     */
    public void readApplyList(Long userId, List<Long> applyIds) {
        lambdaUpdate()
                .set(SysUserApply::getReadStatus, ReadStatusEnum.READ.getCode())
                .eq(SysUserApply::getReadStatus, ReadStatusEnum.UNREAD.getCode())
                .in(SysUserApply::getId, applyIds)
                .eq(SysUserApply::getTargetId, userId)
                .update(new SysUserApply());
    }

    /**
     * 更新记录为未读状态
     *
     * @param applyId 应用 ID
     * @author qingmeng
     * @createTime: 2023/11/29 08:54:59
     */
    public void unReadApplyRecord(Long applyId) {
        lambdaUpdate()
                .set(SysUserApply::getReadStatus, ReadStatusEnum.UNREAD.getCode())
                .eq(SysUserApply::getId, applyId)
                .update(new SysUserApply());
    }

    /**
     * 更新重新申请状态
     *
     * @param applyId 应用 ID
     * @author qingmeng
     * @createTime: 2023/11/29 08:57:53
     */
    public void updateReapplyStatus(Long applyId) {
        lambdaUpdate()
                .set(SysUserApply::getReadStatus, ReadStatusEnum.UNREAD.getCode())
                .set(SysUserApply::getApplyStatus, ApplyStatusEnum.APPLYING.getCode())
                .eq(SysUserApply::getId, applyId)
                .update(new SysUserApply());
    }

    /**
     * 通过两个 ID 获取申请记录
     *
     * @param userId   用户 ID
     * @param targetId 目标 ID
     * @return {@link SysUserApply }
     * @author qingmeng
     * @createTime: 2023/11/29 09:00:43
     */
    public SysUserApply getApplyRecordByBothId(Long userId, Long targetId) {
        return lambdaQuery()
                .eq(SysUserApply::getUserId, userId)
                .eq(SysUserApply::getTargetId, targetId)
                .one();
    }

    /**
     * 按用户 ID 获取未读申请记录计数
     *
     * @param userId 用户 ID
     * @return {@link Long }
     * @author qingmeng
     * @createTime: 2023/11/29 09:13:11
     */
    public Long getUnReadApplyRecordCountByUserId(Long userId) {
        return lambdaQuery().eq(SysUserApply::getTargetId, userId)
                .eq(SysUserApply::getReadStatus, ReadStatusEnum.UNREAD.getCode())
                .count();
    }

    /**
     * 拉黑申请记录
     *
     * @param applyId 应用 ID
     * @author qingmeng
     * @createTime: 2023/11/29 10:36:25
     */
    public void blockApplyRecord( Long applyId) {
        lambdaUpdate()
                .set(SysUserApply::getApplyStatus, ApplyStatusEnum.BLOCK.getCode())
                .eq(SysUserApply::getId, applyId)
                .update(new SysUserApply());
    }

    /**
     * 取消拉黑申请记录
     *
     * @param applyId 应用 ID
     * @author qingmeng
     * @createTime: 2023/11/29 11:24:41
     */
    public void cancelBlockApplyRecord(Long applyId) {
        lambdaUpdate()
                .set(SysUserApply::getApplyStatus, ApplyStatusEnum.APPLYING.getCode())
                .eq(SysUserApply::getId, applyId)
                .update(new SysUserApply());
    }

    /**
     * 按用户 ID 获取拉黑申请记录列表
     *
     * @param userId 用户 ID
     * @return {@link List }<{@link SysUserApply }>
     * @author qingmeng
     * @createTime: 2023/11/29 10:57:27
     */
    public List<SysUserApply> getBlockApplyListByUserId(Long userId) {
        return lambdaQuery()
                .eq(SysUserApply::getTargetId, userId)
                .eq(SysUserApply::getApplyStatus, ApplyStatusEnum.BLOCK.getCode())
                .list();
    }


}
