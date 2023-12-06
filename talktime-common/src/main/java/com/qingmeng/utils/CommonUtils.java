package com.qingmeng.utils;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 通用工具
 * @createTime 2023年11月27日 15:09:00
 */
@Slf4j
public class CommonUtils {

    /**
     * 按排序获取密钥
     *
     * @param ids IDS
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2023/11/27 15:13:46
     */
    public static String getKeyBySort(List<Long> ids) {
        return ids.stream().sorted().map(Objects::toString).collect(Collectors.joining("-"));
    }

    /**
     * 获取好友设置缓存密钥
     *
     * @param userId   用户 ID
     * @param friendId 好友ID
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2023/12/02 10:00:04
     */
    public static String getFriendSettingCacheKey(Long userId, Long friendId) {
        return getKeyBySort(Arrays.asList(userId, friendId)) + StrUtil.COLON + userId;
    }

    /**
     * 检查是否为字母
     *
     * @param str str
     * @return {@link Boolean } true 字母 false 不是字母
     * @author qingmeng
     * @createTime: 2023/12/03 11:10:06
     */
    public static Boolean checkLetter(char str) {
        return Character.isLowerCase(str) || Character.isUpperCase(str);
    }

    /**
     * 检查是否为中文字符
     *
     * @param str str
     * @return {@link Boolean } true 中文字符 false 中文字符
     * @author qingmeng
     * @createTime: 2023/12/03 11:10:06
     */
    public static Boolean checkChineseCharacter(char str) {
        return Character.UnicodeBlock.of(str).equals(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS);
    }

    /**
     * 获取首字母
     *
     * @param ch CH
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2023/12/03 12:20:45
     */
    public static String getFirstLetter(char ch) {
        String firstLetter;

        if (checkLetter(ch)) {
            firstLetter = java.lang.String.valueOf(ch).toUpperCase();
        } else if (checkChineseCharacter(ch)) {
            firstLetter = PinyinHelper.toHanyuPinyinStringArray(ch)[0].substring(0, 1).toUpperCase();
        } else {
            firstLetter = "#";
        }
        return firstLetter;
    }

    /**
     * 获取徽标图像
     *
     * @return {@link Image }
     * @author qingmeng
     * @createTime: 2023/12/05 22:11:51
     */
    @SneakyThrows
    public static Image getLogoImage() {
        ClassPathResource classPathResource = new ClassPathResource("/static/logo.png");
        InputStream inputStreamImg = classPathResource.getInputStream();
        return ImageIO.read(inputStreamImg);
    }

    /**
     * URL 转文件
     *
     * @param imageUrl 图片网址
     * @return {@link File }
     * @author qingmeng
     * @createTime: 2023/12/06 11:11:24
     */
    @SneakyThrows
    public static File urlToFile(String imageUrl) {
        URL url = new URL(imageUrl);
        File tempFile = File.createTempFile(RandomUtil.randomString(10), ".tmp");
        tempFile.deleteOnExit();

        try (InputStream in = url.openStream(); FileOutputStream out = new FileOutputStream(tempFile)) {
            StreamUtils.copy(in, out);
        }

        return tempFile;
    }


    /**
     * 字符串截取
     *
     * @param str   字符串
     * @param start 起始位置
     * @param end   结束位置
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2023/11/10 11:48:47
     */
    public static String substring(String str, int start, int end) {
        if (str == null) {
            return null;
        } else {
            if (end < 0) {
                end += str.length();
            }
            if (start < 0) {
                start += str.length();
            }

            if (end > str.length()) {
                end = str.length();
            }

            if (start > end) {
                return "";
            } else {
                if (start < 0) {
                    start = 0;
                }

                if (end < 0) {
                    end = 0;
                }
                return str.substring(start, end);
            }
        }
    }


    /**
     * 参数拼装
     *
     * @param paramsArray params 数组
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2023/12/06 21:37:14
     */
    public static String argsArrayToString(Object[] paramsArray) {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null) {
            for (Object o : paramsArray) {
                if (o != null) {
                    try {
                        Object jsonObj = JSONUtil.toJsonStr(o);
                        params.append(jsonObj.toString()).append(" ");
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return params.toString().trim();
    }
}
