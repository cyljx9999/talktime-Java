package com.qingmeng.config.netty.vo;

import lombok.Data;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 发送物品通知
 * @createTime 2024年06月12日 17:16:00
 */
@Data
public class WsSendItemInform {
    /**
     * 物品名称
     */
    private String itemName;
}
