package com.qingmeng.service;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.StrUtil;
import com.qingmeng.adapt.FileAdapt;
import com.qingmeng.config.MinioProperties;
import com.qingmeng.dto.file.MinioDTO;
import com.qingmeng.enums.chat.UploadSceneEnum;
import com.qingmeng.utils.IdUtils;
import com.qingmeng.vo.file.MinioVO;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description minio服务接口
 * @createTime 2023年12月05日 21:18:00
 */
@Service
@Slf4j
public class MinioService {
    @Resource
    private MinioProperties minioProperties;

    @Resource
    private MinioClient minioClient;

    /**
     * 查看存储bucket是否存在
     *
     * @param bucketName 存储桶名称
     * @return boolean
     * @author qingmeng
     * @createTime: 2023/12/05 21:21:46
     */
    @SneakyThrows
    public boolean bucketExists(String bucketName) {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 创建存储bucket
     *
     * @param bucketName 存储桶名称
     * @author qingmeng
     * @createTime: 2023/12/05 22:12:56
     */
    @SneakyThrows
    public void makeBucket(String bucketName) {
        minioClient.makeBucket(MakeBucketArgs.builder()
                .bucket(bucketName)
                .build());
    }

    /**
     * 删除存储bucket
     *
     * @param bucketName 存储桶名称
     * @author qingmeng
     * @createTime: 2023/12/05 22:13:10
     */
    @SneakyThrows
    public void removeBucket(String bucketName) {
        minioClient.removeBucket(RemoveBucketArgs.builder()
                .bucket(bucketName)
                .build());
    }

    /**
     * 获取全部bucket
     *
     * @return {@link List }<{@link Bucket }>
     * @author qingmeng
     * @createTime: 2023/12/05 21:22:24
     */
    @SneakyThrows
    public List<Bucket> getAllBuckets() {
        return minioClient.listBuckets();
    }

    /**
     * 获取预签名对象 URL
     *
     * @param minioDTO Minio DTO
     * @return {@link MinioVO }
     * @author qingmeng
     * @createTime: 2023/12/05 21:36:53
     */
    @SneakyThrows
    public MinioVO getPreSignedObjectUrl(MinioDTO minioDTO) {
        String path = getPath(minioDTO);
        String uploadUrl = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.PUT)
                        .bucket(minioProperties.getBucketName())
                        .object(path)
                        .expiry(60 * 60 * 24)
                        .build());
        String downloadUrl = getDownloadUrl(minioProperties.getBucketName(), path);
        return FileAdapt.buildMinioVO(uploadUrl, downloadUrl);
    }

    @NotNull
    private static String getPath(MinioDTO minioDTO) {
        Long userId = minioDTO.getUserId();
        String uuid = IdUtils.fastSimpleUUID();
        String suffix = FileNameUtil.getSuffix(minioDTO.getFileName());
        String yearAndMonth = DateUtil.format(new Date(), DatePattern.NORM_MONTH_PATTERN);
        return minioDTO.getFilePath() + StrUtil.SLASH + yearAndMonth + StrUtil.SLASH + userId + StrUtil.SLASH + uuid + StrUtil.DOT + suffix;
    }

    /**
     * 按流上传文件
     *
     * @param userId     用户 ID
     * @param uploadType 上传类型
     * @param file       文件
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2023/12/05 22:49:51
     */
    @SneakyThrows
    public String uploadFileByStream(Long userId, Integer uploadType, File file) {
        String bucketName = minioProperties.getBucketName();
        UploadSceneEnum uploadSceneEnum = UploadSceneEnum.get(uploadType);
        String path = uploadSceneEnum.getPath() + StrUtil.SLASH + userId;
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(path)
                        .stream(Files.newInputStream(file.toPath()), file.length(), -1)
                        .contentType("image/png")
                        .build());
        return getDownloadUrl(minioProperties.getBucketName(), path);
    }


    /**
     * 获取下载网址
     *
     * @param bucket   桶
     * @param pathFile 路径文件
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2023/12/05 21:31:28
     */
    private String getDownloadUrl(String bucket, String pathFile) {
        return minioProperties.getEndpoint() + StrUtil.SLASH + bucket + pathFile;
    }

}
