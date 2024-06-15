package com.qingmeng.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.qingmeng.domain.vo.CommonResult;
import com.qingmeng.dto.chat.ChatEmojiTagDTO;
import com.qingmeng.service.ChatEmojiTagService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2024年06月15日 13:40:00
 */
@RestController
@RequestMapping("/chat/tag")
@SaCheckLogin
public class ChatEmojiTagController {
    @Resource
    private ChatEmojiTagService chatEmojiTagService;

    /**
     * 新增
     *
     * @param chatEmojiTagDTO 要求
     * @return {@link CommonResult }<{@link String }>
     * @author qingmeng
     * @createTime: 2024/06/15 13:25:30
     */
    @PostMapping
    public CommonResult<String> insert(@Valid @RequestBody ChatEmojiTagDTO chatEmojiTagDTO) {
        chatEmojiTagService.insert(chatEmojiTagDTO, StpUtil.getLoginIdAsLong());
        return CommonResult.success();
    }

    /**
     * 删除
     *
     * @param tagId 标签 ID
     * @return {@link CommonResult }<{@link String }>
     * @author qingmeng
     * @createTime: 2024/06/15 13:35:23
     */
    @DeleteMapping("/{tagId}")
    public CommonResult<String> delete(@PathVariable Long tagId) {
        chatEmojiTagService.remove(tagId,  StpUtil.getLoginIdAsLong());
        return CommonResult.success();
    }

}
