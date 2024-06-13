package com.qingmeng.config.strategy.urlDiscover;

import cn.hutool.core.util.StrUtil;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;


/**
 * 具有优先级的title查询器
 *
 * @author qingmeng
 * @date 2024/06/13 12:45:30
 */
public class PrioritizedUrlDiscover extends AbstractUrlDiscover {

    private final List<UrlDiscover> urlDiscovers = new ArrayList<>(2);

    /**
     * 优先 URL 发现
     *
     * @author qingmeng
     * @createTime: 2024/06/13 12:40:49
     */
    public PrioritizedUrlDiscover() {
        urlDiscovers.add(new WxUrlDiscover());
        urlDiscovers.add(new CommonUrlDiscover());
    }


    /**
     * 获取标题
     *
     * @param document 公文
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2024/06/13 12:40:51
     */
    @Nullable
    @Override
    public String getTitle(Document document) {
        for (UrlDiscover urlDiscover : urlDiscovers) {
            String urlTitle = urlDiscover.getTitle(document);
            if (StrUtil.isNotBlank(urlTitle)) {
                return urlTitle;
            }
        }
        return null;
    }

    /**
     * 获取说明
     *
     * @param document 公文
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2024/06/13 12:40:54
     */
    @Nullable
    @Override
    public String getDescription(Document document) {
        for (UrlDiscover urlDiscover : urlDiscovers) {
            String urlDescription = urlDiscover.getDescription(document);
            if (StrUtil.isNotBlank(urlDescription)) {
                return urlDescription;
            }
        }
        return null;
    }

    /**
     * 获取图片
     *
     * @param url      网址
     * @param document 公文
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2024/06/13 12:40:56
     */
    @Nullable
    @Override
    public String getImage(String url, Document document) {
        for (UrlDiscover urlDiscover : urlDiscovers) {
            String urlImage = urlDiscover.getImage(url, document);
            if (StrUtil.isNotBlank(urlImage)) {
                return urlImage;
            }
        }
        return null;
    }
}
