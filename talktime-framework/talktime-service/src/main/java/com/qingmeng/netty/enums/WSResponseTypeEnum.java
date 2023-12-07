package com.qingmeng.netty.enums;

import com.qingmeng.netty.vo.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 表示 WebSocket 响应的不同类型
 * @createTime 2023年11月13日 10:30:00
 */
@AllArgsConstructor
@Getter
public enum WSResponseTypeEnum {

    /**
     * 登录二维码返回
     */
    LOGIN_URL(1, "登录二维码返回", WsLoginUrlVO.class),

    /**
     * 用户扫描成功等待授权
     */
    LOGIN_SCAN_SUCCESS(2, "用户扫描成功等待授权", null),

    /**
     * 用户登录成功返回用户信息
     */
    LOGIN_SUCCESS(3, "用户登录成功返回用户信息", WsLoginSuccessVO.class),

    /**
     * 新消息
     */
    MESSAGE(4, "新消息", WsMessageVO.class),

    /**
     * 上下线通知
     */
    ONLINE_OFFLINE_NOTIFY(5, "上下线通知", WsOnlineOfflineNotifyVO.class),

    /**
     * 使前端的token失效，意味着前端需要重新登录
     */
    INVALIDATE_TOKEN(6, "使前端的token失效，意味着前端需要重新登录", null),

    /**
     * 拉黑用户
     */
    BLACK(7, "拉黑用户", WsBlackVO.class),

    /**
     * 消息标记
     */
    MARK(8, "消息标记", WsMsgMarkVO.class),

    /**
     * 消息撤回
     */
    RECALL(9, "消息撤回", WsMsgRecallVO.class),

    /**
     * 好友申请
     */
    APPLY(10, "好友申请", WsFriendApplyVO.class),

    /**
     * 成员变动
     */
    MEMBER_CHANGE(11, "成员变动", WsMemberChangeVO.class),

    /**
     * 群聊邀请
     */
    GROUP_INVITE(12, "群聊邀请", WsGroupInviteVO.class),
    ;

    /**
     * 枚举对象的类型
     * -- GETTER --
     *  获取枚举对象的类型值
     *
     */
    private final Integer type;

    /**
     * 枚举对象的描述信息
     * -- GETTER --
     *  获取枚举对象的描述信息
     *
     */
    private final String desc;

    /**
     * 枚举对象关联的数据类，可能为null
     * -- GETTER --
     *  获取枚举对象关联的数据类
     *
     */
    private final Class dataClass;

    /**
     * 缓存枚举类型，将类型和枚举对象映射起来，以便快速查找
     */
    private static final Map<Integer, WSResponseTypeEnum> CACHE;

    static {
        // 使用流操作将枚举值构建成类型和枚举对象的映射关系
        CACHE = Arrays.stream(WSResponseTypeEnum.values()).collect(Collectors.toMap(WSResponseTypeEnum::getType, Function.identity()));
    }

    /**
     * 根据类型返回对应的枚举对象
     * @param type 类型
     * @return {@link WSResponseTypeEnum} 对应的枚举对象，如果找不到则返回null
     */
    public static WSResponseTypeEnum of(Integer type) {
        return CACHE.get(type);
    }

}

