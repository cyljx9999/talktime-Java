package com.qingmeng.config.strategy.urlDiscover;

import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Document;


/**
 * 针对微信公众号文章的标题获取类
 *
 * @author qingmeng
 * @date 2024/06/13 12:45:18
 */
public class WxUrlDiscover extends AbstractUrlDiscover {

    /**
     * 获取标题
     *
     * @param document 公文
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2024/06/13 12:41:00
     */
    @Nullable
    @Override
    public String getTitle(Document document) {
        return document.getElementsByAttributeValue("property", "og:title").attr("content");
    }

    /**
     * 获取说明
     *
     * @param document 公文
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2024/06/13 12:41:02
     */
    @Nullable
    @Override
    public String getDescription(Document document) {
        return document.getElementsByAttributeValue("property", "og:description").attr("content");
    }

    /**
     * 获取图片
     *
     * @param url      网址
     * @param document 公文
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2024/06/13 12:41:03
     */
    @Nullable
    @Override
    public String getImage(String url, Document document) {
        String href = document.getElementsByAttributeValue("property", "og:image").attr("content");
        return isConnect(href) ? href : null;
    }
}
