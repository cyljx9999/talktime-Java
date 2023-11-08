package com.qingmeng.config.mybatisPlus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.qingmeng.enums.LogicDeleteEnum;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description mybatisPlus字段自动填充配置
 * @createTime 2023年11月08日 11:29:00
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {


    /**
     * @param metaObject
     * @Description：
     * @author qingmeng
     * @createTime: 2023/11/08 11:50:14
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        //设置创建时间
        this.setFieldValByName( "createTime", new Date(),metaObject);
        //设置更新时间
        this.setFieldValByName( "updateTime", new Date(),metaObject);
        //设置是否删除
        this.setFieldValByName( "isDeleted", LogicDeleteEnum.NOT_DELETE.getCode(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //设置更新时间
        this.setFieldValByName( "updateTime", new Date(),metaObject);
    }
}
