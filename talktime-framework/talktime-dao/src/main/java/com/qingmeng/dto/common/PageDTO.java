package com.qingmeng.dto.common;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 分页请求
 * @createTime 2023年11月29日 07:58:00
 */
@Data
public class PageDTO {

    /**
     * 页码
     */
    @Min(1)
    @Max(50)
    @NotNull
    private Long pageNo;

    /**
     * 大小
     */
    @NotNull
    @Min(1)
    @Max(20)
    private Long size;

}
