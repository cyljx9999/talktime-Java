package com.qingmeng.utils;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description spring el表达式解析
 * @createTime 2023年11月08日 21:09:00
 */
public class SpElUtils {
    private static final ExpressionParser PARSER = new SpelExpressionParser();
    private static final DefaultParameterNameDiscoverer DEFAULT_PARAMETER_NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    /**
     * 解析 SpEL 表达式并替换其中的参数。
     *
     * @param method 方法对象，用于获取参数名
     * @param args   方法的参数数组
     * @param spEl   包含 SpEL 表达式的字符串
     * @return 解析后的字符串
     */
    public static String parseSpEl(Method method, Object[] args, String spEl) {
        // 解析参数名
        String[] params =
                Optional.ofNullable(DEFAULT_PARAMETER_NAME_DISCOVERER.getParameterNames(method))
                        .orElse(new String[]{});
        // SpEL 解析需要的上下文对象
        EvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < params.length; i++) {
            // 将所有参数作为原材料放入上下文
            context.setVariable(params[i], args[i]);
        }
        Expression expression = PARSER.parseExpression(spEl);
        return expression.getValue(context, String.class);
    }

    /**
     * 获取方法的唯一标识，格式为类名#方法名。
     *
     * @param method 方法对象
     * @return 方法的唯一标识字符串
     */
    public static String getMethodKey(Method method) {
        return method.getDeclaringClass() + "#" + method.getName();
    }
}
