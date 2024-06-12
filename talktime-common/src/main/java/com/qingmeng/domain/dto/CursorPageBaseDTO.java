package com.qingmeng.domain.dto;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 游标翻页请求
 *
 * @author qingmeng
 * @date 2024/06/09 19:45:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CursorPageBaseDTO{

    /**
     * 页面大小
     */
    @Min(0)
    @Max(100)
    private Integer pageSize = 10;

    /**
     * 光标
     */
    private String cursor;

    public Page plusPage() {
        return new Page(1, this.pageSize, false);
    }

    @JsonIgnore
    public Boolean isFirstPage() {
        return StrUtil.isEmpty(cursor);
    }
}
