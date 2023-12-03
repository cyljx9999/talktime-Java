package com.qingmeng.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingmeng.entity.SysUserFriend;
import com.qingmeng.enums.system.LogicDeleteEnum;
import com.qingmeng.mapper.SysUserFriendMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public void removeByFriend(Long userId,Long friendId) {
        lambdaUpdate()
                .eq(SysUserFriend::getUserId, userId)
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

    /**
     * 自定义保存或更新
     *
     * @param saveUserFriend 保存用户好友
     * @author qingmeng
     * @createTime: 2023/12/01 16:18:31
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateByCustom(SysUserFriend saveUserFriend) {
        LambdaQueryWrapper<SysUserFriend> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserFriend::getUserId, saveUserFriend.getUserId());
        wrapper.eq(SysUserFriend::getFriendId, saveUserFriend.getFriendId());
        wrapper.eq(SysUserFriend::getIsDeleted, LogicDeleteEnum.IS_DELETE.getCode());
        saveOrUpdate(saveUserFriend);
    }

    /**
     * 按ID获取好友列表
     *
     * @param userId 用户 ID
     * @return {@link List }<{@link Long }>
     * @author qingmeng
     * @createTime: 2023/12/03 11:03:58
     */
    public List<SysUserFriend> getFriendListById(Long userId) {
        return lambdaQuery().eq(SysUserFriend::getUserId,userId).list();
    }

    /**
     * 通过ID获取好友
     *
     * @param userId 用户 ID
     * @return {@link SysUserFriend }
     * @author qingmeng
     * @createTime: 2023/12/03 13:08:35
     */
    public SysUserFriend getFriendById(Long userId) {
        return lambdaQuery().eq(SysUserFriend::getUserId,userId).one();
    }
}
