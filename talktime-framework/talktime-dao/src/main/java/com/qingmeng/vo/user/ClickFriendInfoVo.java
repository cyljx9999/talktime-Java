package com.qingmeng.vo.user;

import com.qingmeng.enums.user.SexEnum;
import com.qingmeng.vo.chat.SimpleChatInfoVO;
import lombok.Data;

import java.util.List;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 点击好友信息返回类
 * @createTime 2023年12月02日 09:44:00
 */
@Data
public class ClickFriendInfoVo {

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户帐户
     */
    private String userAccount;

    /**
     * 用户性别 0女 1男 2未知
     * @see SexEnum
     */
    private Integer userSex;

    /**
     * 共同群聊
     */
    private List<SimpleChatInfoVO> togetherGroupList;

    /**
     * 添加渠道
     */
    private String addChannel;

    /**
     * 备注状态
     */
    private Boolean remarkStatus;


}
