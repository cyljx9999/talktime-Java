package com.qingmeng.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.entity.SysUserFriend;
import com.qingmeng.enums.system.LogicDeleteEnum;
import com.qingmeng.mapper.SysUserFriendMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户好友表 服务实现类
 * </p>
 *
 * @author qingmeng
 * @since 2023-11-08 10:43:38
 */
@Service
public class SysUserFriendDao extends ServiceImpl<SysUserFriendMapper, SysUserFriend> {

    /**
     * 按好友id删除
     *
     * @param friendId 好友 ID
     * @author qingmeng
     * @createTime: 2023/12/01 09:20:40
     */
    public void removeByFriend(Long friendId) {
        lambdaUpdate()
                .eq(SysUserFriend::getFriendId, friendId)
                .set(SysUserFriend::getIsDeleted, LogicDeleteEnum.IS_DELETE.getCode())
                .update(new SysUserFriend());
    }

    /**
     * 通过两个ID获取好友
     *
     * @param userId   用户 ID
     * @param targetId 目标 ID
     * @return {@link SysUserFriend }
     * @author qingmeng
     * @createTime: 2023/12/01 09:28:04
     */
    public SysUserFriend getFriendByBothId(Long userId, Long targetId) {
        return lambdaQuery()
                .eq(SysUserFriend::getUserId,targetId)
                .eq(SysUserFriend::getFriendId,userId)
                .one();
    }
}
