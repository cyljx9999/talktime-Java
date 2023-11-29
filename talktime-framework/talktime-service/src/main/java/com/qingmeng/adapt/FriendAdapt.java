package com.qingmeng.adapt;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qingmeng.dto.user.ApplyFriendDTO;
import com.qingmeng.entity.SysUser;
import com.qingmeng.entity.SysUserApply;
import com.qingmeng.entity.SysUserFriend;
import com.qingmeng.entity.SysUserFriendSetting;
import com.qingmeng.enums.user.ApplyStatusEnum;
import com.qingmeng.enums.user.ReadStatusEnum;
import com.qingmeng.vo.common.CommonPageVO;
import com.qingmeng.vo.user.FriendApplyRecordVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 好友信息适配器
 * @createTime 2023年11月27日 14:26:00
 */
public class FriendAdapt {

    /**
     * 构建保存好友信息
     *
     * @param applyFriendDTO 申请好友 dto
     * @return {@link SysUserApply }
     * @author qingmeng
     * @createTime: 2023/11/27 14:31:48
     */
    public static SysUserApply buildSaveSysUserApply(ApplyFriendDTO applyFriendDTO){
        SysUserApply sysUserApply = new SysUserApply();
        sysUserApply.setUserId(applyFriendDTO.getUserId());
        sysUserApply.setApplyStatus(ApplyStatusEnum.APPLYING.getCode());
        sysUserApply.setTargetId(applyFriendDTO.getTargetId());
        sysUserApply.setApplyDescribe(applyFriendDTO.getApplyDescribe());
        sysUserApply.setApplyChannel(applyFriendDTO.getApplyChannel());
        sysUserApply.setReadStatus(ReadStatusEnum.UNREAD.getCode());
        return sysUserApply;
    }

    /**
     * 构建 好友 记录
     *
     * @param sysUserApply 申请好友记录参数
     * @return {@link SysUserFriendSetting }
     * @author qingmeng
     * @createTime: 2023/11/28 17:38:48
     */
    public static SysUserFriend buildFriendRecord(SysUserApply sysUserApply){
        SysUserFriend userFriend = new SysUserFriend();
        userFriend.setUserId(sysUserApply.getUserId());
        userFriend.setFriendId(sysUserApply.getTargetId());
        return userFriend;
    }

    /**
     * 建立 好友申请记录列表 VO
     *
     * @param pageList 页面列表
     * @param userList 用户列表
     * @return {@link CommonPageVO }<{@link FriendApplyRecordVO }>
     * @author qingmeng
     * @createTime: 2023/11/29 08:19:32
     */
    public static CommonPageVO<FriendApplyRecordVO> buildFriendApplyRecordListVO(IPage<SysUserApply> pageList, List<SysUser> userList){
        List<FriendApplyRecordVO> voList = pageList.getRecords().stream().map(apply -> {
            FriendApplyRecordVO applyRecordVO = new FriendApplyRecordVO();
            applyRecordVO.setApplyId(apply.getId());
            applyRecordVO.setApplyChannel(apply.getApplyChannel());
            applyRecordVO.setApplyStatus(apply.getApplyStatus());
            applyRecordVO.setCreateTime(apply.getCreateTime());
            userList.stream()
                    .filter(user -> user.getId().equals(apply.getUserId()))
                    .findFirst()
                    .ifPresent(user -> {
                        applyRecordVO.setUserName(user.getUserName());
                        applyRecordVO.setUserAvatar(user.getUserAvatar());
                    });
            return applyRecordVO;
        }).collect(Collectors.toList());
        return CommonPageVO.init(pageList.getCurrent(),pageList.getSize(), pageList.getTotal(), voList);
    }

}
