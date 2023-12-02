package com.qingmeng.dto.user;

import com.qingmeng.valid.custom.StringListValue;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 申请好友参数类
 * @createTime 2023年11月27日 11:18:00
 */
@Data
public class ApplyFriendDTO {

    /**
     * 用户id
     */
    @Null
    private Long userId;

    /**
     * 目标好友id
     */
    @NotNull
    private Long targetId;

    /**
     * 分享卡片者的userId
     */
    private Long shareCardByUserId;

    /**
     * 申请描述
     */
    @Length(max = 20)
    private String applyDescribe;

    /**
     * 申请渠道
     */
    @NotBlank
    @StringListValue(values = {"account","phone","card","group","qrcode"})
    private String applyChannel;

}
