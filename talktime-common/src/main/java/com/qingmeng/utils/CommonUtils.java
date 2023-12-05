package com.qingmeng.utils;

import lombok.SneakyThrows;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.InputStream;
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
        return getKeyBySort(Arrays.asList(userId, friendId)) + ":" + userId;
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
            firstLetter = String.valueOf(ch).toUpperCase();
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
}
