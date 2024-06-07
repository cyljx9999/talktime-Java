package com.qingmeng.adapt;

import com.qingmeng.dto.file.MinioDTO;
import com.qingmeng.dto.file.UploadUrlDTO;
import com.qingmeng.enums.chat.UploadSceneEnum;
import com.qingmeng.vo.file.MinioVO;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 文件适配类
 * @createTime 2023年12月05日 21:32:00
 */
public class FileAdapt {


    /**
     * 构建 minio vo
     *
     * @param uploadUrl   上传网址
     * @param downloadUrl 下载网址
     * @return {@link MinioVO }
     * @author qingmeng
     * @createTime: 2023/12/05 21:36:00
     */
    public static MinioVO buildMinioVO(String uploadUrl, String downloadUrl) {
        MinioVO minioVO = new MinioVO();
        minioVO.setUploadUrl(uploadUrl);
        minioVO.setDownloadUrl(downloadUrl);
        return minioVO;
    }

    /**
     * 构建 Minio DTO
     *
     * @param userId       用户 ID
     * @param uploadUrlDTO 上传 URL DTO
     * @return {@link MinioDTO }
     * @author qingmeng
     * @createTime: 2023/12/05 23:00:10
     */
    public static MinioDTO buildMinioDTO(Long userId,UploadUrlDTO uploadUrlDTO) {
        UploadSceneEnum uploadSceneEnum = UploadSceneEnum.get(uploadUrlDTO.getUploadScene());
        MinioDTO minioDTO = new MinioDTO();
        minioDTO.setFilePath(uploadSceneEnum.getPath());
        minioDTO.setFileName(uploadUrlDTO.getFileName());
        minioDTO.setUserId(userId);
        return minioDTO;
    }
}
