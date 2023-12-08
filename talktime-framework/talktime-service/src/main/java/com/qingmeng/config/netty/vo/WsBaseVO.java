package com.qingmeng.config.netty.vo;

import com.qingmeng.config.netty.enums.WSResponseTypeEnum;
import lombok.Data;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description ws响应信息
 * @createTime 2023年11月13日 10:29:00
 */
@Data
public class WsBaseVO<T> {
    /**
     * 类型
     * @see WSResponseTypeEnum
     */
    private Integer type;

    /**
     * 数据
     */
    private T data;
}
