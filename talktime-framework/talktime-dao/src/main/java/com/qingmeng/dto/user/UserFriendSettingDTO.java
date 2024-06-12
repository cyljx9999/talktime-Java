package com.qingmeng.dto.user;

import com.qingmeng.valid.custom.IntListValue;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 用户好友设置参数
 * @createTime 2023年11月29日 15:09:00
 */
@Data
public class UserFriendSettingDTO {

    /**
     * 设置 ID
     */
    @NotNull
    private Long settingId;

    /**
     * 好友ID
     */
    @NotNull
    private Long friendId;

    /**
     * 昵称
     */
    @Length(min = 1,max = 10)
    private String nickName;

    /**
     * 好友状态 0正常 1拉黑
     *
     * @see com.qingmeng.enums.user.FriendStausEnum
     */
    @IntListValue(values = {0, 1})
    private Integer friendStatus;

    /**
     * 提醒状态 0关闭 1开启
     * @see com.qingmeng.enums.common.OpenStatusEnum
     */
    @IntListValue(values = {0, 1})
    private Integer remindStatus;

    /**
     * 置顶状态 0不置顶 1置顶
     *
     * @see com.qingmeng.enums.chat.MessageTopStatusEnum
     */
    @IntListValue(values = {0, 1})
    private Integer topStatus;
}
