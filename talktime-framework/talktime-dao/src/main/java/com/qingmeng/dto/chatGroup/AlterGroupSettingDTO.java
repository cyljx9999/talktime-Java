package com.qingmeng.dto.chatGroup;

import com.qingmeng.valid.custom.IntListValue;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 修改群聊设置
 * @createTime 2023年12月08日 10:30:00
 */
@Data
public class AlterGroupSettingDTO {

    /**
     * 群聊房间
     */
    @NotNull
    private Long groupRoomId;

    /**
     * 群聊名字
     */
    @Length(min = 1, max = 20)
    private String groupRoomName;

    /**
     * 群聊头像
     */
    @URL
    private String groupRoomAvatar;

    /**
     * 群公告
     */
    @Length(max = 100)
    private String groupNotice;


    /**
     * 邀请确认 0关闭 1开启
     *
     * @see com.qingmeng.enums.user.CloseOrOpenStatusEnum
     */
    @IntListValue(values = {0, 1})
    private Integer invitationConfirmation;
}
