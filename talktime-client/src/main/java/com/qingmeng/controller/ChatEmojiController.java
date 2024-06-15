package com.qingmeng.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.qingmeng.domain.vo.CommonResult;
import com.qingmeng.dto.chat.ChatEmojiDTO;
import com.qingmeng.service.ChatEmojiService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2024年06月15日 13:40:00
 */
@RestController
@RequestMapping("/chat/emoji")
@SaCheckLogin
public class ChatEmojiController {
    @Resource
    private ChatEmojiService chatEmojiService;

    /**
     * 获取表情包列表
     *
     * @return {@link CommonResult }<{@link Map }<{@link String },{@link Object }>>
     * @author qingmeng
     * @createTime: 2024/06/15 13:11:39
     */
    @GetMapping("/list")
    public CommonResult<Map<String,Object>> getEmojisList() {
        Map<String,Object> map = chatEmojiService.getEmojisList(StpUtil.getLoginIdAsLong());
        return CommonResult.success(map);
    }


    /**
     * 新增表情包
     *
     * @param chatEmojiDTO 要求
     * @return {@link CommonResult }<{@link String }>
     * @author qingmeng
     * @createTime: 2024/06/15 13:25:30
     */
    @PostMapping
    public CommonResult<String> insertEmojis(@Valid @RequestBody ChatEmojiDTO chatEmojiDTO) {
        chatEmojiService.insert(chatEmojiDTO, StpUtil.getLoginIdAsLong());
        return CommonResult.success();
    }

    /**
     * 删除表情包
     *
     * @param emojiId 表情符号 ID
     * @return {@link CommonResult }<{@link String }>
     * @author qingmeng
     * @createTime: 2024/06/15 13:35:23
     */
    @DeleteMapping("/{emojiId}")
    public CommonResult<String> deleteEmojis(@PathVariable Long emojiId) {
        chatEmojiService.remove(emojiId,  StpUtil.getLoginIdAsLong());
        return CommonResult.success();
    }

}
