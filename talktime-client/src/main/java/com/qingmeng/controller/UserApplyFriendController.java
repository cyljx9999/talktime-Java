package com.qingmeng.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.qingmeng.annotation.SysLog;
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
    @SysLog(title = "添加好友模块",content = "提交好友申请")
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
    @SysLog(title = "添加好友模块",content = "同意好友申请")
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
    @SysLog(title = "添加好友模块",content = "获取当前申请列表")
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
    @SysLog(title = "添加好友模块",content = "获取未读申请的记录数")
    public CommonResult<Long> getUnReadApplyRecordCountByUserId() {
        Long count = sysUserApplyService.getUnReadApplyRecordCountByUserId(StpUtil.getLoginIdAsLong());
        return CommonResult.success(count);
    }


    /**
     * 拉黑申请记录
     *
     * @param applyId 申请 ID
     * @return {@link CommonResult }<{@link String }>
     * @author qingmeng
     * @createTime: 2023/11/29 10:33:12
     */
    @PutMapping("/blockApplyRecord/{applyId}")
    @SysLog(title = "添加好友模块",content = "拉黑对方好友申请")
    public CommonResult<String> blockApplyRecord(@PathVariable Long applyId) {
        sysUserApplyService.blockApplyRecord(applyId);
        return CommonResult.success("已拉黑");
    }

    /**
     * 取消拉黑申请记录
     *
     * @param applyId 申请 ID
     * @return {@link CommonResult }<{@link String }>
     * @author qingmeng
     * @createTime: 2023/11/29 11:23:40
     */
    @PutMapping("/cancelBlockApplyRecord/{applyId}")
    @SysLog(title = "添加好友模块",content = "取消拉黑好友申请")
    public CommonResult<String> cancelBlockApplyRecord(@PathVariable Long applyId) {
        sysUserApplyService.cancelBlockApplyRecord(applyId);
        return CommonResult.success("已取消拉黑");
    }

    /**
     * 获取拉黑申请记录列表
     *
     * @return {@link CommonResult }<{@link List }<{@link FriendApplyRecordVO }>>
     * @author qingmeng
     * @createTime: 2023/11/29 10:49:46
     */
    @GetMapping("/getBlockApplyList")
    @SysLog(title = "添加好友模块",content = "获取已拉黑好友申请列表")
    public CommonResult<List<FriendApplyRecordVO>> getBlockApplyList() {
        List<FriendApplyRecordVO> list = sysUserApplyService.getBlockApplyListByUserId(StpUtil.getLoginIdAsLong());
        return CommonResult.success(list);
    }


    /**
     * 删除申请记录
     *
     * @param applyId 应用 ID
     * @return {@link CommonResult }<{@link String }>
     * @author qingmeng
     * @createTime: 2023/11/29 11:07:34
     */
    @DeleteMapping("/deleteApplyRecord/{applyId}")
    @SysLog(title = "添加好友模块",content = "删除申请记录")
    public CommonResult<String> deleteApplyRecord(@PathVariable Long applyId) {
        sysUserApplyService.deleteApplyRecordByUserId(StpUtil.getLoginIdAsLong(),applyId);
        return CommonResult.success("删除成功");
    }

}
