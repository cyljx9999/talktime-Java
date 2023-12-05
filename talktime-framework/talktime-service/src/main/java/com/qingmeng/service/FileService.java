package com.qingmeng.service;

import com.qingmeng.dto.file.UploadUrlDTO;
import com.qingmeng.vo.file.MinioVO;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 文件服务
 * @createTime 2023年12月05日 21:50:00
 */

public interface FileService {

    /**
     * 获取二维码网址
     *
     * @param userId 用户 ID
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2023/12/05 21:59:23
     */
    String getQrcodeUrl(Long userId);

    /**
     * 获取预签名对象 URL
     *
     * @param userId       用户 ID
     * @param uploadUrlDTO 上传 URL DTO
     * @return {@link MinioVO }
     * @author qingmeng
     * @createTime: 2023/12/05 23:01:24
     */
    MinioVO getPreSignedObjectUrl(Long userId,UploadUrlDTO uploadUrlDTO);

    /**
     * 更新二维码网址
     *
     * @param userId 用户 ID
     * @author qingmeng
     * @createTime: 2023/12/05 23:08:15
     */
    void updateQrcodeUrl(Long userId);
}
