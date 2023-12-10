package com.qingmeng.dto.chatGroup;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 添加管理员参数类
 * @createTime 2023年12月08日 10:38:00
 */
@Data
public class AddManagementDTO {

    /**
     * 房间 ID
     */
    @NotNull
    private Long roomId;

    /**
     * 用户 ID
     */
    @NotNull
    @Size(min = 1, max = 10)
    private List<Long> userIds;

}
