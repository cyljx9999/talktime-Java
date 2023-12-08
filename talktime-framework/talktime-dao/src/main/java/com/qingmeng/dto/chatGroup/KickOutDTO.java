package com.qingmeng.dto.chatGroup;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 踢出群聊参数类
 * @createTime 2023年12月08日 09:13:00
 */
@Data
public class KickOutDTO {

    /**
     * 组会议室 ID
     */
    @NotNull
    private Long groupRoomId;

    /**
     * 用户 ID
     */
    @NotNull
    private Long userId;

}

