package com.qingmeng.config.cache;

import com.qingmeng.constant.RedisConstant;
import com.qingmeng.constant.SystemConstant;
import com.qingmeng.dao.SysArticleDao;
import com.qingmeng.entity.SysArticle;
import com.qingmeng.utils.JsonUtils;
import com.qingmeng.utils.RedisUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 物品缓存
 * @createTime 2023年11月24日 21:11:00
 */
@Component
public class ArticleCache extends AbstractRedisStringCache<Long, SysArticle> {
    @Resource
    private SysArticleDao sysArticleDao;

    /**
     * 根据输入对象获取缓存的键。
     *
     * @param articleId 物品 ID
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2023/11/24 21:31:48
     */
    @Override
    protected String getKey(Long articleId) {
        return RedisConstant.ARTICLE_KEY + articleId;
    }

    /**
     * 获取缓存的过期时间（秒）。
     *
     * @return {@link Long }
     * @author qingmeng
     * @createTime: 2023/11/24 21:13:35
     */
    @Override
    protected Long getExpireSeconds() {
        return RedisConstant.ARTICLE_EXPIRE * SystemConstant.DAY;
    }

    /**
     * 批量加载缓存数据。
     *
     * @param req 批量请求列表
     * @return {@link Map }<{@link Long }, {@link SysArticle }>
     * @author qingmeng
     * @createTime: 2023/11/24 21:13:37
     */
    @Override
    protected Map<Long, SysArticle> load(List<Long> req) {
        return sysArticleDao.list().stream().collect(Collectors.toMap(SysArticle::getId, Function.identity()));
    }

    /**
     * 获取列表
     *
     * @return {@link List }<{@link SysArticle }>
     * @author qingmeng
     * @createTime: 2023/11/24 21:50:45
     */
    public List<SysArticle> getList() {
        String key = RedisConstant.ARTICLE_ALL_KEY;
        if (!RedisUtils.hasKey(key)) {
            List<SysArticle> articleList = sysArticleDao.list();
            List<String> collect = articleList.stream().map(JsonUtils::toStr).collect(Collectors.toList());
            RedisUtils.lRightPushAll(key, collect);
        }
        return RedisUtils.lRange(key, 0, -1, SysArticle.class);
    }
}
