package com.qingmeng.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.qingmeng.annotation.SysLog;
import com.qingmeng.domain.vo.CommonResult;
import com.qingmeng.dto.article.WearArticleDTO;
import com.qingmeng.service.SysArticleService;
import com.qingmeng.vo.article.SysArticleListVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月24日 20:40:00
 */
@RestController
@RequestMapping("/article")
@SaCheckLogin
public class SysArticleController {
    @Resource
    private SysArticleService sysArticleService;

    /**
     * 获取物品展品列表
     *
     * @return {@link CommonResult }<{@link SysArticleListVO }>
     * @author qingmeng
     * @createTime: 2023/11/24 21:08:19
     */
    @GetMapping("/getArticleDisplayList")
    @SysLog(title = "物品模块",content = "获取物品展示列表")
    public CommonResult<SysArticleListVO> getArticleDisplayList() {
        return CommonResult.success(sysArticleService.getArticleDisplayList());
    }


    /**
     * 佩戴头像边框
     *
     * @return {@link CommonResult }<{@link String }>
     * @author qingmeng
     * @createTime: 2023/11/24 21:59:02
     */
    @PutMapping("/wearHeadBorder")
    @SysLog(title = "物品模块",content = "佩戴头像边框")
    public CommonResult<String> wearHeadBorder(@Valid @RequestBody WearArticleDTO wearArticleDTO) {
        sysArticleService.wearHeadBorder(StpUtil.getLoginIdAsLong(),wearArticleDTO);
        return CommonResult.success("佩戴成功");
    }

    /**
     * 佩戴徽章
     *
     * @return {@link CommonResult }<{@link String }>
     * @author qingmeng
     * @createTime: 2023/11/24 21:59:02
     */
    @PutMapping("/wearBadge")
    @SysLog(title = "物品模块",content = "佩戴徽章")
    public CommonResult<String> wearBadge(@Valid @RequestBody WearArticleDTO wearArticleDTO) {
        sysArticleService.wearBadge(StpUtil.getLoginIdAsLong(),wearArticleDTO);
        return CommonResult.success("佩戴成功");
    }

}
