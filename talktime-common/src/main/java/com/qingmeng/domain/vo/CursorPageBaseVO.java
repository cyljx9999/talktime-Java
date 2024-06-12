package com.qingmeng.domain.vo;

import cn.hutool.core.collection.CollectionUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 游标翻页返回
 *
 * @author qingmeng
 * @date 2024/06/09 19:32:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CursorPageBaseVO<T> {
    /**
     * 游标
     */
    private String cursor;

    /**
     * 是否最后一页
     */
    private Boolean isLast = Boolean.FALSE;


    /**
     * 数据列表
     */
    private List<T> list;

    public static <T> CursorPageBaseVO<T> init(CursorPageBaseVO cursorPage, List<T> list) {
        CursorPageBaseVO<T> cursorPageBaseResp = new CursorPageBaseVO<>();
        cursorPageBaseResp.setIsLast(cursorPage.getIsLast());
        cursorPageBaseResp.setList(list);
        cursorPageBaseResp.setCursor(cursorPage.getCursor());
        return cursorPageBaseResp;
    }

    @JsonIgnore
    public Boolean isEmpty() {
        return CollectionUtil.isEmpty(list);
    }

    public static <T> CursorPageBaseVO<T> empty() {
        CursorPageBaseVO<T> cursorPageBaseResp = new CursorPageBaseVO<T>();
        cursorPageBaseResp.setIsLast(true);
        cursorPageBaseResp.setList(new ArrayList<T>());
        return cursorPageBaseResp;
    }

}
