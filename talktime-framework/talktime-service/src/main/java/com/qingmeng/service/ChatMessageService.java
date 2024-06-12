package com.qingmeng.service;

import com.qingmeng.domain.dto.CursorPageBaseDTO;
import com.qingmeng.dto.chat.*;
import com.qingmeng.entity.ChatMessage;
import com.qingmeng.vo.chat.ChatMessageReadVO;
import com.qingmeng.vo.chat.ChatMessageVO;
import com.qingmeng.domain.vo.CursorPageBaseVO;
import com.qingmeng.vo.chat.ChatRoomVO;
import com.qingmeng.vo.chat.MsgReadInfoVO;

import java.util.List;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2024年06月04日 22:07:00
 */
public interface ChatMessageService {
    /**
     * 发送消息
     *
     * @param chatMessageDTO 聊天消息 DTO
     * @param userId         用户 ID
     * @return {@link Long }
     * @author qingmeng
     * @createTime: 2024/06/06 22:40:57
     */
    Long sendMsg(ChatMessageDTO chatMessageDTO, Long userId);

    /**
     * 获取聊天消息 VO
     *
     * @param msgId      消息 ID
     * @param receiveUid 接收 UID
     * @return {@link ChatMessageVO }
     * @author qingmeng
     * @createTime: 2024/06/07 22:53:52
     */
    ChatMessageVO getChatMessageVO(Long msgId, Long receiveUid);

    /**
     * 获取聊天消息 VO
     *
     * @param chatMessage 聊天消息
     * @param receiveUid  接收 UID
     * @return {@link ChatMessageVO }
     * @author qingmeng
     * @createTime: 2024/06/07 22:54:47
     */
    ChatMessageVO getChatMessageVO(ChatMessage chatMessage, Long receiveUid);

    /**
     * 设置消息标记
     *
     * @param userId             用户 ID
     * @param chatMessageMarkDTO 聊天消息 Mark DTO
     * @author qingmeng
     * @createTime: 2024/06/08 13:27:32
     */
    void setMsgMark(Long userId, ChatMessageMarkDTO chatMessageMarkDTO);

    /**
     * 撤回消息
     *
     * @param userId           用户 ID
     * @param chatRecallMsgDTO 聊天撤回消息 DTO
     * @author qingmeng
     * @createTime: 2024/06/09 18:38:03
     */
    void recallMsg(Long userId, ChatRecallMsgDTO chatRecallMsgDTO);

    /**
     * 获取 MSG 阅读信息
     *
     * @param userId                 用户 ID
     * @param chatMessageReadInfoDTO 聊天消息 阅读信息 DTO
     * @return {@link List }<{@link MsgReadInfoVO }>
     * @author qingmeng
     * @createTime: 2024/06/09 19:02:39
     */
    List<MsgReadInfoVO> getMsgReadInfo(Long userId, ChatMessageReadInfoDTO chatMessageReadInfoDTO);

    /**
     * 信息阅读
     *
     * @param userId             用户 ID
     * @param chatMessageReadDTO 聊天消息读取 DTO
     * @author qingmeng
     * @createTime: 2024/06/09 19:25:04
     */
    void msgRead(Long userId, ChatMessageReadDTO chatMessageReadDTO);

    /**
     * 消息的已读未读列表
     *
     * @param userId                 用户 ID
     * @param chatMessageReadTypeDTO 聊天消息读取类型 DTO
     * @return {@link CursorPageBaseVO }<{@link ChatMessageReadVO }>
     * @author qingmeng
     * @createTime: 2024/06/09 19:34:26
     */
    CursorPageBaseVO<ChatMessageReadVO> getReadPage(Long userId, ChatMessageReadTypeDTO chatMessageReadTypeDTO);

    /**
     * 获取 msg 页面
     *
     * @param chatMessageReadDTO 聊天消息读取 DTO
     * @param receiveUid             用户 ID
     * @return {@link CursorPageBaseVO }<{@link ChatMessageVO }>
     * @author qingmeng
     * @createTime: 2024/06/09 20:08:56
     */
    CursorPageBaseVO<ChatMessageVO> getMsgPage(ChatMessage1DTO chatMessageReadDTO, Long receiveUid);

    /**
     * 获取房间会话列表
     *
     * @param request 请求
     * @param userId  用户 ID
     * @return {@link CursorPageBaseVO }<{@link ChatRoomVO }>
     * @author qingmeng
     * @createTime: 2024/06/12 14:46:12
     */
    CursorPageBaseVO<ChatRoomVO> getSessionPage(CursorPageBaseDTO request, Long userId);

    /**
     * 获取会话详细信息
     *
     * @param userId 用户 ID
     * @param roomId 房间 ID
     * @return {@link ChatRoomVO }
     * @author qingmeng
     * @createTime: 2024/06/12 15:33:46
     */
    ChatRoomVO getSessionDetail(Long userId, Long roomId);

    /**
     * 获取会话详细信息
     * @param userId   用户 ID
     * @param friendId 好友 ID
     * @return {@link ChatRoomVO }
     * @author qingmeng
     * @createTime: 2024/06/12 15:37:03
     */
    ChatRoomVO getSessionDetailByFriend(Long userId, Long friendId);
}
