package com.qingmeng.config.captcha;

import com.google.code.kaptcha.text.impl.DefaultTextCreator;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 验证码文本生成器
 * @createTime 2023年11月11日 14:18:00
 */
public class KaptchaTextCreator extends DefaultTextCreator {
    private static final String[] NUMBERS = "0,1,2,3,4,5,6,7,8,9,10".split(",");

    @Override
    public String getText() {
        int result;
        Random random = new SecureRandom();
        int x = random.nextInt(10);
        int y = random.nextInt(10);
        StringBuilder suChinese = new StringBuilder();
        int round = (int) Math.round(Math.random() * 2);
        if (round == 0) {
            result = x * y;
            suChinese.append(NUMBERS[x]);
            suChinese.append("*");
            suChinese.append(NUMBERS[y]);
        } else if (round == 1) {
            if (!(x == 0) && y % x == 0) {
                result = y / x;
                suChinese.append(NUMBERS[y]);
                suChinese.append("/");
                suChinese.append(NUMBERS[x]);
            } else {
                result = x + y;
                suChinese.append(NUMBERS[x]);
                suChinese.append("+");
                suChinese.append(NUMBERS[y]);
            }
        } else if (round == 2) {
            if (x >= y) {
                result = x - y;
                suChinese.append(NUMBERS[x]);
                suChinese.append("-");
                suChinese.append(NUMBERS[y]);
            } else {
                result = y - x;
                suChinese.append(NUMBERS[y]);
                suChinese.append("-");
                suChinese.append(NUMBERS[x]);
            }
        } else {
            result = x + y;
            suChinese.append(NUMBERS[x]);
            suChinese.append("+");
            suChinese.append(NUMBERS[y]);
        }
        suChinese.append("=?@").append(result);
        return suChinese.toString();
    }
}
