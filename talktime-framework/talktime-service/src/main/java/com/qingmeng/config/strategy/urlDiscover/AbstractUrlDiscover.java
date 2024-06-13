package com.qingmeng.config.strategy.urlDiscover;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.qingmeng.constant.SystemConstant;
import com.qingmeng.dto.chat.UrlInfo;
import com.qingmeng.utils.FutureUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.data.util.Pair;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 摘要 URL 发现
 *
 * @author qingmeng
 * @date 2024/06/13 12:35:18
 */
@Slf4j
public abstract class AbstractUrlDiscover implements UrlDiscover {


    /**
     * 获取 URL 内容映射
     *
     * @param content 内容
     * @return {@link Map }<{@link String }, {@link UrlInfo }>
     * @author qingmeng
     * @createTime: 2024/06/13 12:37:03
     */
    @Nullable
    @Override
    public Map<String, UrlInfo> getUrlContentMap(String content) {

        if (StrUtil.isBlank(content)) {
            return new HashMap<>(SystemConstant.ZERO_INT);
        }
        List<String> matchList = ReUtil.findAll(SystemConstant.PATTERN, content, SystemConstant.ZERO_INT);

        //并行请求
        List<CompletableFuture<Pair<String, UrlInfo>>> futures = matchList.stream().map(match -> CompletableFuture.supplyAsync(() -> {
            UrlInfo urlInfo = getContent(match);
            return Objects.isNull(urlInfo) ? null : Pair.of(match, urlInfo);
        })).collect(Collectors.toList());
        CompletableFuture<List<Pair<String, UrlInfo>>> future = FutureUtils.sequenceNonNull(futures);
        //结果组装
        return future.join().stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond, (a, b) -> a));
    }

    /**
     * 获取内容
     *
     * @param url 网址
     * @return {@link UrlInfo }
     * @author qingmeng
     * @createTime: 2024/06/13 12:37:05
     */
    @Nullable
    @Override
    public UrlInfo getContent(String url) {
        Document document = getUrlDocument(assemble(url));
        if (Objects.isNull(document)) {
            return null;
        }

        return UrlInfo.builder()
                .title(getTitle(document))
                .description(getDescription(document))
                .image(getImage(assemble(url), document)).build();
    }


    private String assemble(String url) {

        if (!StrUtil.startWith(url, "http")) {
            return "http://" + url;
        }

        return url;
    }

    /**
     * 获取 URL 文档
     *
     * @param matchUrl 匹配网址
     * @return {@link Document }
     * @author qingmeng
     * @createTime: 2024/06/13 12:37:09
     */
    protected Document getUrlDocument(String matchUrl) {
        try {
            Connection connect = Jsoup.connect(matchUrl);
            connect.timeout(2000);
            return connect.get();
        } catch (Exception e) {
            log.error("find error:url:{}", matchUrl, e);
        }
        return null;
    }

    /**
     * 判断链接是否有效
     * 输入链接
     * 返回true或者false
     *
     * @param href HREF
     * @return boolean
     * @author qingmeng
     * @createTime: 2024/06/13 12:37:13
     */
    public static boolean isConnect(String href) {
        //请求地址
        URL url;
        //请求状态码
        int state;
        //下载链接类型
        String fileType;
        try {
            url = new URL(href);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            state = connection.getResponseCode();
            fileType = connection.getHeaderField("Content-Disposition");
            // 如果成功200，缓存304，移动302都算有效链接，并且不是下载链接
            if ((state == 200 || state == 302 || state == 304) && fileType == null) {
                return true;
            }
            connection.disconnect();
        } catch (Exception e) {
            return false;
        }
        return false;
    }

}
