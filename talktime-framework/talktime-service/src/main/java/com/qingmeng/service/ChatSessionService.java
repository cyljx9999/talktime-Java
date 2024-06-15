package com.qingmeng.service;

import com.qingmeng.domain.dto.CursorPageBaseDTO;
import com.qingmeng.domain.vo.CursorPageBaseVO;
import com.qingmeng.vo.chat.ChatRoomVO;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2024年06月09日 19:18:00
 */
public interface ChatSessionService {

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
