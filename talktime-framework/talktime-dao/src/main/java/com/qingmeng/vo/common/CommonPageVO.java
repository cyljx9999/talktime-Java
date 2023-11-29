package com.qingmeng.vo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 通用分页返回类
 * @createTime 2023年11月29日 08:14:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonPageVO<T> {

    /**
     * 页码
     */
    private Long pageNo;

    /**
     * 页面大小
     */
    private Long pageSize;

    /**
     * 记录总数
     */
    private Long totalRecords;

    /**
     * 是否最后一个
     */
    private Boolean isLastPage;

    /**
     * 列表
     */
    private List<T> list;

    /**
     * 空
     *
     * @return {@link CommonPageVO }<{@link T }>
     * @author qingmeng
     * @createTime: 2023/11/29 08:25:35
     */
    public static <T> CommonPageVO<T> empty() {
        CommonPageVO<T> pageVO = new CommonPageVO<>();
        pageVO.setPageNo(1L);
        pageVO.setPageSize(1L);
        pageVO.setIsLastPage(true);
        pageVO.setTotalRecords(0L);
        pageVO.setList(new ArrayList<>());
        return pageVO;
    }

    /**
     * 初始化
     *
     * @param pageNo       页码
     * @param pageSize     页面大小
     * @param totalRecords 记录总数
     * @param isLastPage   是最后一页
     * @param list         列表
     * @return {@link CommonPageVO }<{@link T }>
     * @author qingmeng
     * @createTime: 2023/11/29 08:25:20
     */
    public static <T> CommonPageVO<T> init(Long pageNo, Long pageSize, Long totalRecords, Boolean isLastPage, List<T> list) {
        return new CommonPageVO<>(pageNo, pageSize, totalRecords, isLastPage, list);
    }

    /**
     * 初始化
     *
     * @param pageNo       页码
     * @param pageSize     页面大小
     * @param totalRecords 记录总数
     * @param list         列表
     * @return {@link CommonPageVO }<{@link T }>
     * @author qingmeng
     * @createTime: 2023/11/29 08:26:16
     */
    public static <T> CommonPageVO<T> init(Long pageNo, Long pageSize, Long totalRecords, List<T> list) {
        return new CommonPageVO<>(pageNo, pageSize, totalRecords, isLastPage(totalRecords, pageNo, pageSize), list);
    }

    /**
     * 是否是最后一页
     */
    public static Boolean isLastPage(Long totalRecords, Long pageNo, Long pageSize) {
        long pageTotal = totalRecords / pageSize + (totalRecords % pageSize == 0 ? 0 : 1);
        return pageNo >= pageTotal;
    }
}
