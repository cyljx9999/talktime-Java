package com.qingmeng.vo.common;

import lombok.Data;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 扫码二维码信息返回类
 * @createTime 2023年12月06日 10:50:00
 */
@Data
public class ScanQrcodeInfoVO<T> {

    /**
     * 数据信息
     */
    T dataInfo;

}
