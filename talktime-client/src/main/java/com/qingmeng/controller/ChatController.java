package com.qingmeng.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.qingmeng.domain.rep.CommonResult;
import com.qingmeng.dto.chat.ChatMessageDTO;
import com.qingmeng.dto.chat.ChatMessageMarkDTO;
import com.qingmeng.service.ChatMessageService;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 发送消息
     *
     * @param chatMessageDTO 聊天消息 DTO
     * @return {@link CommonResult }<{@link ? }>
     * @author qingmeng
     * @createTime: 2024/06/08 13:23:40
     */
    @PostMapping("/sendMsg")
    public CommonResult<?> sendMsg(@Valid @RequestBody ChatMessageDTO chatMessageDTO){
        Long msgId = chatMessageService.sendMsg(chatMessageDTO, StpUtil.getLoginIdAsLong());
        //返回完整消息格式，方便前端展示
        return CommonResult.success(chatMessageService.getChatMessageVO(msgId, StpUtil.getLoginIdAsLong()));
    }

    /**
     * 设置消息标记
     *
     * @param chatMessageMarkDTO 聊天消息 Mark DTO
     * @return {@link CommonResult }<{@link String }>
     * @author qingmeng
     * @createTime: 2024/06/08 13:27:09
     */
    @PutMapping("/msg/mark")
    public CommonResult<String> setMsgMark(@Valid @RequestBody ChatMessageMarkDTO chatMessageMarkDTO) {
        chatMessageService.setMsgMark(StpUtil.getLoginIdAsLong(), chatMessageMarkDTO);
        return CommonResult.success();
    }


}
