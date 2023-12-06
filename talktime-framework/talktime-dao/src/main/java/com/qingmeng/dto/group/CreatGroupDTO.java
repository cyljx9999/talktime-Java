package com.qingmeng.dto.group;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 创建群聊参数类
 * @createTime 2023年12月06日 22:05:00
 */
@Data
public class CreatGroupDTO {

    /**
     * 成员ids
     */
    @NotEmpty
    @Size(min = 3, max = 200)
    private List<Long> memberIds;

}
