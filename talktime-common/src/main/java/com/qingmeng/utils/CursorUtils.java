package com.qingmeng.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Pair;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qingmeng.domain.dto.CursorPageBaseDTO;
import com.qingmeng.domain.vo.CursorPageBaseVO;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 游标分页工具类
 *
 * @author qingmeng
 * @date 2024/06/09 19:50:02
 */
public class CursorUtils {

    public static <T> CursorPageBaseVO<Pair<T, Double>> getCursorPageByRedis(CursorPageBaseDTO tCursorPageBaseDTO, String redisKey, Function<String, T> typeConvert) {
        Set<ZSetOperations.TypedTuple<String>> typedTuples;
        if (StrUtil.isBlank(tCursorPageBaseDTO.getCursor())) {
            typedTuples = RedisUtils.zReverseRangeWithScores(redisKey, tCursorPageBaseDTO.getPageSize());
        } else {
            typedTuples = RedisUtils.zReverseRangeByScoreWithScores(redisKey, Double.parseDouble(tCursorPageBaseDTO.getCursor()), tCursorPageBaseDTO.getPageSize());
        }
        List<Pair<T, Double>> result = typedTuples
                .stream()
                .map(t -> Pair.of(typeConvert.apply(t.getValue()), t.getScore()))
                .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                .collect(Collectors.toList());
        String cursor = Optional.ofNullable(CollectionUtil.getLast(result))
                .map(Pair::getValue)
                .map(String::valueOf)
                .orElse(null);
        Boolean isLast = result.size() != tCursorPageBaseDTO.getPageSize();
        return new CursorPageBaseVO<>(cursor, isLast, result);
    }

    public static <T> CursorPageBaseVO<T> getCursorPageByMysql(IService<T> mapper, CursorPageBaseDTO tCursorPageBaseDTO, Consumer<LambdaQueryWrapper<T>> initWrapper, SFunction<T, ?> cursorColumn) {
        //游标字段类型
        Class<?> cursorType = LambdaUtils.getReturnType(cursorColumn);
        LambdaQueryWrapper<T> wrapper = new LambdaQueryWrapper<>();
        //额外条件
        initWrapper.accept(wrapper);
        //游标条件
        if (StrUtil.isNotBlank(tCursorPageBaseDTO.getCursor())) {
            wrapper.lt(cursorColumn, parseCursor(tCursorPageBaseDTO.getCursor(), cursorType));
        }
        //游标方向
        wrapper.orderByDesc(cursorColumn);

        Page<T> page = mapper.page(tCursorPageBaseDTO.plusPage(), wrapper);
        //取出游标
        String cursor = Optional.ofNullable(CollectionUtil.getLast(page.getRecords()))
                .map(cursorColumn)
                .map(CursorUtils::toCursor)
                .orElse(null);
        //判断是否最后一页
        Boolean isLast = page.getRecords().size() != tCursorPageBaseDTO.getPageSize();
        return new CursorPageBaseVO<>(cursor, isLast, page.getRecords());
    }

    private static String toCursor(Object o) {
        if (o instanceof Date) {
            return String.valueOf(((Date) o).getTime());
        } else {
            return o.toString();
        }
    }

    private static Object parseCursor(String cursor, Class<?> cursorClass) {
        if (Date.class.isAssignableFrom(cursorClass)) {
            return new Date(Long.parseLong(cursor));
        } else {
            return cursor;
        }
    }
}
