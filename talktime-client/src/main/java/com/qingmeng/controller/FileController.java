package com.qingmeng.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.qingmeng.annotation.SysLog;
import com.qingmeng.domain.rep.CommonResult;
import com.qingmeng.dto.file.UploadUrlDTO;
import com.qingmeng.service.FileService;
import com.qingmeng.vo.file.MinioVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年12月05日 23:03:32
 */
@RestController
@SaCheckLogin
@RequestMapping("/file")
public class FileController {
    @Resource
    private FileService fileService;


    /**
     * 获取预签名 URL
     *
     * @param uploadUrlDTO 上传 URL DTO
     * @return {@link CommonResult }<{@link MinioVO }>
     * @author qingmeng
     * @createTime: 2023/12/05 23:06:26
     */
    @PostMapping("/getPreSignedUrl")
    @SysLog(title = "文件模块",content = "获取预签名链接")
    public CommonResult<MinioVO> getPreSignedUrl(@Valid UploadUrlDTO uploadUrlDTO){
        MinioVO minioVO = fileService.getPreSignedObjectUrl(StpUtil.getLoginIdAsLong(), uploadUrlDTO);
        return CommonResult.success(minioVO);
    }


    /**
     * 更新二维码网址
     *
     * @return {@link CommonResult }<{@link String }>
     * @author qingmeng
     * @createTime: 2023/12/05 23:09:39
     */
    @PostMapping("/updateQrcodeUrl")
    @SysLog(title = "文件模块",content = "更新二维码")
    public CommonResult<String> updateQrcodeUrl(){
        fileService.updateQrcodeUrl(StpUtil.getLoginIdAsLong());
        return CommonResult.success();
    }


}
