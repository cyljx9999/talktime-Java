package com.qingmeng.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.qingmeng.domain.dto.CursorPageBaseDTO;
import com.qingmeng.domain.vo.CommonResult;
import com.qingmeng.domain.vo.CursorPageBaseVO;
import com.qingmeng.service.ChatSessionService;
import com.qingmeng.vo.chat.ChatRoomVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 聊天会话
 * @createTime 2024年06月15日 13:41:00
 */
@RestController
@RequestMapping("/chat/session")
public class ChatSessionController {

    @Resource
    private ChatSessionService chatSessionService;


    /**
     * 获取房间会话列表
     *
     * @param request 请求
     * @return {@link CommonResult }<{@link CursorPageBaseVO }<{@link ChatRoomVO }>>
     * @author qingmeng
     * @createTime: 2024/06/12 14:45:02
     */
    @GetMapping("/page")
    public CommonResult<CursorPageBaseVO<ChatRoomVO>> getRoomPage(@Valid CursorPageBaseDTO request) {
        CursorPageBaseVO<ChatRoomVO> list = chatSessionService.getSessionPage(request, StpUtil.getLoginIdAsLong());
        return CommonResult.success(list);
    }

    /**
     * 获取会话详细信息
     *
     * @param roomId 房间 ID
     * @return {@link CommonResult }<{@link ChatRoomVO }>
     * @author qingmeng
     * @createTime: 2024/06/12 15:33:32
     */
    @GetMapping("/detail/{roomId}")
    public CommonResult<ChatRoomVO> getSessionDetail(@PathVariable Long roomId) {
        ChatRoomVO chatRoomVO = chatSessionService.getSessionDetail(StpUtil.getLoginIdAsLong(), roomId);
        return CommonResult.success(chatRoomVO);
    }

    /**
     * 会话详情(联系人列表发消息用)
     *
     * @param friendId 好友 ID
     * @return {@link CommonResult }<{@link ChatRoomVO }>
     * @author qingmeng
     * @createTime: 2024/06/12 15:35:28
     */
    @GetMapping("/detail/friend/{friendId}")
    public CommonResult<ChatRoomVO> getSessionDetailByFriend(@PathVariable Long friendId) {
        ChatRoomVO chatRoomVO = chatSessionService.getSessionDetailByFriend(StpUtil.getLoginIdAsLong(), friendId);
        return CommonResult.success(chatRoomVO);
    }
}
