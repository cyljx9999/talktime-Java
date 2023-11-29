package com.qingmeng.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 好友设置VO
 * @createTime 2023年11月29日 14:39:00
 */
@Data
public class UserFriendSettingVO {

    /**
     * 设置 ID
     */
    private Long settingId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 好友状态 0正常 1拉黑
     * @see com.qingmeng.enums.user.FriendStausEnum
     */
    private Integer friendStatus;

    /**
     * 置顶状态 0不置顶 1置顶
     * @see com.qingmeng.enums.chat.MessageTopStatusEnum
     */
    private Integer topStatus;

    /**
     * 添加渠道
     */
    private String addChannel;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;


}
