package com.qingmeng.dto.chatGroup;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 移除管理员参数类
 * @createTime 2023年12月09日 13:31:00
 */
@Data
public class RemoveManagementDTO {

    /**
     * 组会议室 ID
     */
    @NotNull
    private Long roomId;

    /**
     * 用户 ID
     */
    @NotNull
    private Long userId;
}
