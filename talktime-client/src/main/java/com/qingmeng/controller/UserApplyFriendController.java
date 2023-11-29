package com.qingmeng.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.qingmeng.domain.rep.CommonResult;
import com.qingmeng.dto.common.PageDTO;
import com.qingmeng.dto.user.AgreeApplyFriendDTO;
import com.qingmeng.dto.user.ApplyFriendDTO;
import com.qingmeng.service.SysUserApplyService;
import com.qingmeng.vo.common.CommonPageVO;
import com.qingmeng.vo.user.FriendApplyRecordVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 用户申请好友控制层
 * @createTime 2023年11月27日 15:51:00
 */
@RestController
@RequestMapping("/apply")
@SaCheckLogin
public class UserApplyFriendController {
    @Resource
    private SysUserApplyService sysUserApplyService;

    /**
     * 申请好友
     *
     * @param applyFriendDTO 申请好友 dto
     * @return {@link CommonResult }<{@link String }>
     * @author qingmeng
     * @createTime: 2023/11/27 15:56:31
     */
    @PostMapping("/add")
    public CommonResult<String> applyFriend(@Valid @RequestBody ApplyFriendDTO applyFriendDTO) {
        applyFriendDTO.setUserId(StpUtil.getLoginIdAsLong());
        sysUserApplyService.applyFriend(applyFriendDTO);
        return CommonResult.success("申请成功");
    }

    /**
     * 同意申请
     *
     * @param agreeApplyFriendDTO 申请好友 dto
     * @return {@link CommonResult }<{@link String }>
     * @author qingmeng
     * @createTime: 2023/11/27 15:56:31
     */
    @PostMapping("/agreeApply")
    public CommonResult<String> agreeApply(@Valid @RequestBody AgreeApplyFriendDTO agreeApplyFriendDTO) {
        sysUserApplyService.agreeApply(agreeApplyFriendDTO);
        return CommonResult.success("已同意申请");
    }


    /**
     * 根据userId获取好友申请列表
     *
     * @param pageDTO 分页 dto
     * @return {@link CommonResult }<{@link CommonPageVO }<{@link FriendApplyRecordVO }>>
     * @author qingmeng
     * @createTime: 2023/11/29 08:16:01
     */
    @GetMapping("/getMyFriendApplyList")
    public CommonResult<CommonPageVO<FriendApplyRecordVO>> getMyFriendApplyList(@Valid PageDTO pageDTO) {
        CommonPageVO<FriendApplyRecordVO> list = sysUserApplyService.getFriendApplyListByUserId(StpUtil.getLoginIdAsLong(), pageDTO);
        return CommonResult.success(list);
    }

    /**
     * 根据id获取未读申请记录计数
     *
     * @return {@link CommonResult }<{@link Long }>
     * @author qingmeng
     * @createTime: 2023/11/29 09:09:43
     */
    @GetMapping("/getUnReadRecordCount")
    public CommonResult<Long> getUnReadApplyRecordCountByUserId() {
        Long count = sysUserApplyService.getUnReadApplyRecordCountByUserId(StpUtil.getLoginIdAsLong());
        return CommonResult.success(count);
    }


}
