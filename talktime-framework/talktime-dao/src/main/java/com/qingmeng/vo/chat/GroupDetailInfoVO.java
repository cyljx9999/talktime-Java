package com.qingmeng.vo.chat;

import com.qingmeng.enums.common.CloseOrOpenStatusEnum;
import com.qingmeng.vo.user.ManagerInfo;
import com.qingmeng.vo.user.SimpleUserInfo;
import lombok.Data;

import java.util.List;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 群聊详细信息
 * @createTime 2023年12月09日 13:54:00
 */
@Data
public class GroupDetailInfoVO {

    /**
     * 群聊名字
     */
    private String groupRoomName;

    /**
     * 群聊头像
     */
    private String groupRoomAvatar;

    /**
     * 群公告
     */
    private String groupNotice;

    /**
     * 邀请确认 0关闭 1开启
     * @see CloseOrOpenStatusEnum
     */
    private Integer invitationConfirmation;

    /**
     * 群聊二维码
     */
    private String groupRoomQrCode;

    /**
     * 置顶状态 0不置顶 1置顶
     *
     * @see com.qingmeng.enums.chat.MessageTopStatusEnum
     */
    private Integer topStatus;

    /**
     * 展示群成员名字状态 0隐藏 1开启
     *
     * @see com.qingmeng.enums.chat.DisplayNameStatusEnum
     */
    private Integer displayNameStatus;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 提醒状态 0关闭 1开启
     *
     * @see com.qingmeng.enums.common.OpenStatusEnum
     */
    private Integer remindStatus;

    /**
     * 成员名单
     */
    private List<SimpleUserInfo> memberList;

    /**
     * 管理员列表
     */
    private List<ManagerInfo> managerList;
}
