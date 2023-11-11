package com.qingmeng.enums.system;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月09日 22:16:00
 */
public interface CommonEnum {

    /**
     * 获取枚举code
     * @return {@link Integer }
     * @author qingmeng
     * @createTime: 2023/11/09 22:51:28
     */
    Integer getCode();

    /**
     * 获取枚举信息
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2023/11/09 22:51:32
     */
    String getMsg();
}
