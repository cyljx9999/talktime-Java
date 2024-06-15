package com.qingmeng.config.cache;

import com.qingmeng.constant.RedisConstant;
import com.qingmeng.dao.SysSensitiveReplaceDao;
import com.qingmeng.entity.ChatEmoji;
import com.qingmeng.entity.SysSensitiveReplace;
import com.qingmeng.utils.JsonUtils;
import com.qingmeng.utils.RedisUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2024年06月15日 13:21:00
 */
@Component
public class SensitiveCache {
    @Resource
    private SysSensitiveReplaceDao sysSensitiveReplaceDao;

    /**
     * 获取列表
     *
     * @return {@link List }<{@link ChatEmoji }>
     * @author qingmeng
     * @createTime: 2024/06/15 13:22:25
     */
    public List<SysSensitiveReplace> getList() {
        String key = RedisConstant.SENSITIVE_KEY;
        if (!RedisUtils.hasKey(key)) {
            List<SysSensitiveReplace> list = sysSensitiveReplaceDao.getListWithOpen();
            List<String> collect = list.stream().map(JsonUtils::toStr).collect(Collectors.toList());
            RedisUtils.lRightPushAll(key, collect);
        }
        return RedisUtils.lRange(key, 0, -1, SysSensitiveReplace.class);
    }


    /**
     * 清除全部缓存
     *
     * @author qingmeng
     * @createTime: 2024/06/15 13:38:57
     */
    public void clearAllCache() {
        String key = RedisConstant.SENSITIVE_KEY;
        RedisUtils.delete(key);
    }

}
