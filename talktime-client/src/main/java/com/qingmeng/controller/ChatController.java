package com.qingmeng.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.qingmeng.domain.vo.CommonResult;
import com.qingmeng.domain.vo.CursorPageBaseVO;
import com.qingmeng.dto.chat.*;
import com.qingmeng.service.ChatMessageService;
import com.qingmeng.vo.chat.ChatMessageReadVO;
import com.qingmeng.vo.chat.ChatMessageVO;
import com.qingmeng.vo.chat.MsgReadInfoVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 聊天
 * @createTime 2024年06月06日 22:36:00
 */
@RestController
@RequestMapping("/chat")
@SaCheckLogin
public class ChatController {
    @Resource
    private ChatMessageService chatMessageService;

    /**
     * 消息列表
     *
     * @param chatMessageReadDTO 请求
     * @return {@link CommonResult }<{@link CursorPageBaseVO }<{@link ChatMessageVO }>>
     * @author qingmeng
     * @createTime: 2024/06/09 20:04:58
     */
    @GetMapping("/public/msg/page")
    public CommonResult<CursorPageBaseVO<ChatMessageVO>> getMsgPage(@Valid ChatMessage1DTO chatMessageReadDTO) {
        CursorPageBaseVO<ChatMessageVO> msgPage = chatMessageService.getMsgPage(chatMessageReadDTO, StpUtil.getLoginIdAsLong());
        return CommonResult.success(msgPage);
    }


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

    /**
     * 撤回消息
     *
     * @param chatRecallMsgDTO 请求
     * @return {@link CommonResult }<{@link String }>
     * @author qingmeng
     * @createTime: 2024/06/09 18:35:41
     */
    @PutMapping("/msg/recall")
    public CommonResult<String> recallMsg(@Valid @RequestBody ChatRecallMsgDTO chatRecallMsgDTO) {
        chatMessageService.recallMsg(StpUtil.getLoginIdAsLong(), chatRecallMsgDTO);
        return CommonResult.success();
    }

    /**
     * 消息的已读未读列表
     *
     * @param chatMessageReadTypeDTO 请求
     * @author qingmeng
     * @createTime: 2024/06/09 19:28:16
     */
    @GetMapping("/msg/read/page")
    public CommonResult<CursorPageBaseVO<ChatMessageReadVO>> getReadPage(@Valid ChatMessageReadTypeDTO chatMessageReadTypeDTO) {
        CursorPageBaseVO<ChatMessageReadVO> cursorPageBaseVO = chatMessageService.getReadPage(StpUtil.getLoginIdAsLong(), chatMessageReadTypeDTO);
        return CommonResult.success(cursorPageBaseVO);
    }

    /**
     * 获取消息的已读未读总数
     *
     * @param chatMessageReadInfoDTO 请求
     * @return {@link CommonResult }<{@link List }<{@link MsgReadInfoVO }>>
     * @author qingmeng
     * @createTime: 2024/06/09 18:57:43
     */
    @GetMapping("/msg/read")
    public CommonResult<List<MsgReadInfoVO>> getReadInfo(@Valid ChatMessageReadInfoDTO chatMessageReadInfoDTO) {
        List<MsgReadInfoVO> list = chatMessageService.getMsgReadInfo(StpUtil.getLoginIdAsLong(), chatMessageReadInfoDTO);
        return CommonResult.success(list);
    }

    /**
     * 消息阅读
     *
     * @param chatMessageReadDTO 请求
     * @return {@link CommonResult }<{@link String }>
     * @author qingmeng
     * @createTime: 2024/06/09 19:23:01
     */
    @PutMapping("/msg/read")
    public CommonResult<String> msgRead(@Valid @RequestBody ChatMessageReadDTO chatMessageReadDTO) {
        chatMessageService.msgRead(StpUtil.getLoginIdAsLong(), chatMessageReadDTO);
        return CommonResult.success();
    }
}
