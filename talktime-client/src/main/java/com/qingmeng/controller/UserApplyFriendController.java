package com.qingmeng.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.qingmeng.domain.rep.CommonResult;
import com.qingmeng.dto.user.AgreeApplyFriendDTO;
import com.qingmeng.dto.user.ApplyFriendDTO;
import com.qingmeng.service.SysUserApplyService;
import com.qingmeng.vo.user.FriendApplyRecordVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

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
     * @return {@link CommonResult }<{@link List }<{@link FriendApplyRecordVO }>>
     * @author qingmeng
     * @createTime: 2023/11/28 23:12:30
     */
    @GetMapping("/getMyFriendApplyList")
    public CommonResult<List<FriendApplyRecordVO>> getMyFriendApplyList() {
        List<FriendApplyRecordVO> list = sysUserApplyService.getFriendApplyListByUserId(StpUtil.getLoginIdAsLong());
        return CommonResult.success(list);
    }


}
