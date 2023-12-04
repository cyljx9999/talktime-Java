package com.qingmeng.adapt;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qingmeng.dto.login.CheckFriendDTO;
import com.qingmeng.dto.login.CheckFriendListDTO;
import com.qingmeng.dto.user.ApplyFriendDTO;
import com.qingmeng.entity.SysUser;
import com.qingmeng.entity.SysUserApply;
import com.qingmeng.entity.SysUserFriend;
import com.qingmeng.entity.SysUserFriendSetting;
import com.qingmeng.enums.user.ApplyStatusEnum;
import com.qingmeng.enums.user.ReadStatusEnum;
import com.qingmeng.utils.CommonUtils;
import com.qingmeng.vo.common.CommonPageVO;
import com.qingmeng.vo.user.CheckFriendVO;
import com.qingmeng.vo.user.FriendApplyRecordVO;
import com.qingmeng.vo.user.FriendTypeVO;
import com.qingmeng.vo.user.SimpleUserInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 好友信息适配器
 * @createTime 2023年11月27日 14:26:00
 */
public class FriendAdapt {

    /**
     * 构建保存好友信息
     *
     * @param applyFriendDTO 申请好友 dto
     * @return {@link SysUserApply }
     * @author qingmeng
     * @createTime: 2023/11/27 14:31:48
     */
    public static SysUserApply buildSaveSysUserApply(ApplyFriendDTO applyFriendDTO) {
        SysUserApply sysUserApply = new SysUserApply();
        sysUserApply.setUserId(applyFriendDTO.getUserId());
        sysUserApply.setApplyStatus(ApplyStatusEnum.APPLYING.getCode());
        sysUserApply.setTargetId(applyFriendDTO.getTargetId());
        sysUserApply.setApplyDescribe(applyFriendDTO.getApplyDescribe());
        sysUserApply.setApplyChannel(applyFriendDTO.getApplyChannel());
        sysUserApply.setReadStatus(ReadStatusEnum.UNREAD.getCode());
        return sysUserApply;
    }

    /**
     * 构建 好友 记录
     *
     * @param sysUserApply 申请好友记录参数
     * @return {@link SysUserFriendSetting }
     * @author qingmeng
     * @createTime: 2023/11/28 17:38:48
     */
    public static SysUserFriend buildFriendRecord(SysUserApply sysUserApply) {
        SysUserFriend userFriend = new SysUserFriend();
        userFriend.setUserId(sysUserApply.getUserId());
        userFriend.setFriendId(sysUserApply.getTargetId());
        return userFriend;
    }

    /**
     * 构建逆向 好友 记录
     *
     * @param sysUserApply 申请好友记录参数
     * @return {@link SysUserFriend }
     * @author qingmeng
     * @createTime: 2023/12/01 16:27:54
     */
    public static SysUserFriend buildFriendRecordReverse(SysUserApply sysUserApply) {
        SysUserFriend userFriend = new SysUserFriend();
        userFriend.setUserId(sysUserApply.getTargetId());
        userFriend.setFriendId(sysUserApply.getUserId());
        return userFriend;
    }

    /**
     * 建立 好友申请记录分页列表 VO
     *
     * @param pageList 页面列表
     * @param userList 用户列表
     * @return {@link CommonPageVO }<{@link FriendApplyRecordVO }>
     * @author qingmeng
     * @createTime: 2023/11/29 08:19:32
     */
    public static CommonPageVO<FriendApplyRecordVO> buildFriendApplyRecordPageListVO(IPage<SysUserApply> pageList, List<SysUser> userList) {
        List<FriendApplyRecordVO> voList = buildFriendApplyRecordListVO(pageList.getRecords(), userList);
        return CommonPageVO.init(pageList.getCurrent(), pageList.getSize(), pageList.getTotal(), voList);
    }


    /**
     * 建立 好友申请记录 列表 VO
     *
     * @param applyList 申请列表
     * @param userList  用户列表
     * @return {@link List }<{@link FriendApplyRecordVO }>
     * @author qingmeng
     * @createTime: 2023/11/29 11:03:59
     */
    public static List<FriendApplyRecordVO> buildFriendApplyRecordListVO(List<SysUserApply> applyList, List<SysUser> userList) {
        return applyList.stream().map(apply -> {
            FriendApplyRecordVO applyRecordVO = new FriendApplyRecordVO();
            applyRecordVO.setApplyId(apply.getId());
            applyRecordVO.setApplyUserId(apply.getUserId());
            applyRecordVO.setApplyChannel(apply.getApplyChannel());
            applyRecordVO.setApplyStatus(apply.getApplyStatus());
            applyRecordVO.setCreateTime(apply.getCreateTime());
            userList.stream()
                    .filter(user -> user.getId().equals(apply.getUserId()))
                    .findFirst()
                    .ifPresent(user -> {
                        applyRecordVO.setUserName(user.getUserName());
                        applyRecordVO.setUserAvatar(user.getUserAvatar());
                    });
            return applyRecordVO;
        }).collect(Collectors.toList());
    }

    /**
     * 建立好友列表
     *
     * @param listMap           列表映射
     * @param friendSettingList 好友设置列表
     * @return {@link List }<{@link FriendTypeVO }>
     * @author qingmeng
     * @createTime: 2023/12/03 12:28:59
     */
    public static List<FriendTypeVO> buildFriendList(Map<String, List<SysUser>> listMap, List<SysUserFriendSetting> friendSettingList, Long userId) {
        List<FriendTypeVO> categorizedUserList = new ArrayList<>();
        listMap.forEach((key, value) -> {
            FriendTypeVO vo = new FriendTypeVO();
            vo.setType(key);
            List<SimpleUserInfo> userInfoList = value.stream().map(friendUser -> {
                // 获取 当前用户 对 对方 的设置数据，判断是否进行备注
                SysUserFriendSetting friendSetting = friendSettingList.stream()
                        .filter(setting -> {
                            // 通过tagKey和userId定位数据 tagKey:1-2 userId:1 表示用户1对用户2的设置
                            boolean flagA = setting.getTagKey().equals(CommonUtils.getKeyBySort(Arrays.asList(userId, friendUser.getId())));
                            boolean flagB = setting.getUserId().equals(userId);
                            return flagA && flagB;
                        })
                        .findAny()
                        .orElse(new SysUserFriendSetting());
                SimpleUserInfo userInfo = new SimpleUserInfo();
                userInfo.setUserAvatar(friendUser.getUserAvatar());
                // 如果有备注则采用备注，否则则使用对方原本的用户名
                userInfo.setUserName(StrUtil.isNotBlank(friendSetting.getNickName()) ? friendSetting.getNickName() : friendUser.getUserName());
                return userInfo;
            }).collect(Collectors.toList());
            vo.setUserList(userInfoList);
            categorizedUserList.add(vo);
        });
        return categorizedUserList;
    }

    /**
     * 构造 检查好友列表
     *
     * @param friendIds      好友ID
     * @param checkFriendDTO 检查好友 DTO
     * @return {@link List }<{@link CheckFriendVO }>
     * @author qingmeng
     * @createTime: 2023/12/03 12:53:44
     */
    public static List<CheckFriendVO> buildCheckFriendList(List<Long> friendIds, CheckFriendListDTO checkFriendDTO) {
        return checkFriendDTO.getFriendIdList().stream().map(id -> {
            CheckFriendVO vo = new CheckFriendVO();
            vo.setFriendId(id);
            vo.setCheckStatus(friendIds.contains(id));
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 构建检查好友
     *
     * @param sysUserFriend  sys 用户好友
     * @param checkFriendDTO 检查好友 DTO
     * @return {@link CheckFriendVO }
     * @author qingmeng
     * @createTime: 2023/12/03 13:10:41
     */
    public static CheckFriendVO buildCheckFriend(SysUserFriend sysUserFriend, CheckFriendDTO checkFriendDTO) {
        CheckFriendVO vo = new CheckFriendVO();
        vo.setFriendId(checkFriendDTO.getFriendId());
        vo.setCheckStatus(sysUserFriend.getFriendId().equals(checkFriendDTO.getFriendId()));
        return vo;
    }
}
