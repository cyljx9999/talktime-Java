package com.qingmeng.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.qingmeng.domain.rep.CommonResult;
import com.qingmeng.dto.chat.ChatMessageDTO;
import com.qingmeng.service.ChatMessageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 聊天
 * @createTime 2024年06月06日 22:36:00
 */
@RestController
@RequestMapping("/chat")
public class ChatController {
    @Resource
    private ChatMessageService chatMessageService;

    @PostMapping("/sendMsg")
    public CommonResult<?> sendMsg(@Valid @RequestBody ChatMessageDTO chatMessageDTO){
        Long msgId = chatMessageService.sendMsg(chatMessageDTO, StpUtil.getLoginIdAsLong());
        //返回完整消息格式，方便前端展示
        return CommonResult.success(chatMessageService.getChatMessageVO(msgId, StpUtil.getLoginIdAsLong()));
    }

}
