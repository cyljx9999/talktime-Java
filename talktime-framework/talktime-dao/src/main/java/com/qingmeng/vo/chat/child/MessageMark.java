package com.qingmeng.vo.chat.child;

import lombok.Data;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2024年06月06日 11:28:00
 */
@Data
public class MessageMark {
    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 该用户是否已经点赞 0否 1 是
     */
    private Integer userLike;

    /**
     * 举报数
     */
    private Integer dislikeCount;

    /**
     * 该用户是否已经举报 0否 1是
     */
    private Integer userDislike;
}