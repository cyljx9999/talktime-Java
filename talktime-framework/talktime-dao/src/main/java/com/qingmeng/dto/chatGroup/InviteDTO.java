package com.qingmeng.dto.chatGroup;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 邀请参数
 * @createTime 2023年12月07日 08:37:00
 */
@Data
public class InviteDTO {

    /**
     * 房间 ID
     */
    @NotNull
    private Long roomId;

    /**
     * 用户 ID
     */
    @NotEmpty
    @Size(min = 1, max = 20)
    private List<Long> userIds;

}
