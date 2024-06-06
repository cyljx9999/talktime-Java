package com.qingmeng.dto.chat.msg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 撤回消息参数类
 * @createTime 2024年06月04日 21:08:00
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MsgRecallDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 撤回消息的用户id
     */
    private Long recallUserId;

    /**
     * 撤回的时间点
     */
    private Date recallTime;
}
