package com.qingmeng.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.entity.SysUserApply;
import com.qingmeng.enums.user.ApplyStatusEnum;
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
                .update();
    }

    /**
     * 根据Id获取好友申请列表
     *
     * @param userId 用户 ID
     * @return {@link List }<{@link SysUserApply }>
     * @author qingmeng
     * @createTime: 2023/11/28 23:23:57
     */
    public List<SysUserApply> getFriendApplyListByUserId(Long userId) {
        return lambdaQuery()
                .eq(SysUserApply::getTargetId, userId)
                .eq(SysUserApply::getApplyStatus, ApplyStatusEnum.BLOCK.getCode())
                .list();
    }
}
