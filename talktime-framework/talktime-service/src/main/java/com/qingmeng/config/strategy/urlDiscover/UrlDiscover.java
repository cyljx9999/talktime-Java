package com.qingmeng.config.strategy.urlDiscover;

import com.qingmeng.dto.chat.UrlInfo;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Document;

import java.util.Map;


/**
 * URL 发现
 *
 * @author qingmeng
 * @date 2024/06/13 12:35:04
 */
public interface UrlDiscover {


    /**
     * 获取 URL 内容映射
     *
     * @param content 内容
     * @return {@link Map }<{@link String }, {@link UrlInfo }>
     * @author qingmeng
     * @createTime: 2024/06/13 12:34:47
     */
    @Nullable
    Map<String, UrlInfo> getUrlContentMap(String content);

    /**
     * 获取内容
     *
     * @param url 网址
     * @return {@link UrlInfo }
     * @author qingmeng
     * @createTime: 2024/06/13 12:34:51
     */
    @Nullable
    UrlInfo getContent(String url);

    /**
     * 获取标题
     *
     * @param document 公文
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2024/06/13 12:34:53
     */
    @Nullable
    String getTitle(Document document);

    /**
     * 获取说明
     *
     * @param document 公文
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2024/06/13 12:34:54
     */
    @Nullable
    String getDescription(Document document);

    /**
     * 获取图片
     *
     * @param url      网址
     * @param document 公文
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2024/06/13 12:34:56
     */
    @Nullable
    String getImage(String url, Document document);

}
