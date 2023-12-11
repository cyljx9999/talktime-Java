package com.qingmeng.dto.chatGroup;

import com.qingmeng.valid.custom.IntListValue;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 修改群聊个人设置参数类
 * @createTime 2023年12月09日 13:36:00
 */
@Data
public class AlterGroupPersonalSettingDTO {
    /**
     * 房间id
     */
    @NotNull
    private Long roomId;

    /**
     * 置顶状态 0不置顶 1置顶
     *
     * @see com.qingmeng.enums.chat.MessageTopStatusEnum
     */
    @IntListValue(values = {0, 1})
    private Integer topStatus;

    /**
     * 展示群成员名字状态 0隐藏 1开启
     *
     * @see com.qingmeng.enums.chat.DisplayNameStatusEnum
     */
    @IntListValue(values = {0, 1})
    private Integer displayNameStatus;

    /**
     * 昵称
     */
    @Length(min = 1,max = 20)
    private String nickName;

    /**
     * 提醒状态 0关闭 1开启
     *
     * @see com.qingmeng.enums.chat.RemindStatusEnum
     */
    @IntListValue(values = {0, 1})
    private Integer remindStatus;
}
