package com.qingmeng.vo.user;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 好友申请记录
 * @createTime 2023年11月28日 21:57:00
 */
@Data
public class FriendApplyRecordVO {

    /**
     * 申请 ID
     */
    private Long applyId;

    /**
     * 申请用户 ID
     */
    private Long applyUserId;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 申请渠道
     */
    private String applyChannel;

    /**
     * 申请状态 0 申请中 1已同意 2拒绝接受 3拉黑
     * @see com.qingmeng.enums.user.ApplyStatusEnum
     */
    private Integer applyStatus;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

}
