package com.qingmeng.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.qingmeng.adapt.FileAdapt;
import com.qingmeng.cache.UserCache;
import com.qingmeng.constant.SystemConstant;
import com.qingmeng.dao.SysUserDao;
import com.qingmeng.dto.file.MinioDTO;
import com.qingmeng.dto.file.UploadUrlDTO;
import com.qingmeng.enums.system.UploadSceneEnum;
import com.qingmeng.service.FileService;
import com.qingmeng.service.MinioSerivce;
import com.qingmeng.utils.CommonUtils;
import com.qingmeng.vo.file.MinioVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年12月05日 21:51:00
 */
@Service
public class FileServiceImpl implements FileService {
    @Resource
    private MinioSerivce minioSerivce;
    @Resource
    private UserCache userCache;
    @Resource
    private SysUserDao sysUserDao;

    /**
     * 获取二维码网址
     *
     * @param userId 用户 ID
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2023/12/05 21:59:23
     */
    @Override
    public String getQrcodeUrl(Long userId) {
        QrConfig config = new QrConfig();
        config.setHeight(SystemConstant.QRCODE_HEIGHT);
        config.setWidth(SystemConstant.QRCODE_WIDTH);
        config.setImg(CommonUtils.getLogoImage());
        File file = QrCodeUtil.generate(
                userId.toString(),
                config,
                new File(RandomUtil.randomString(10)));
        return minioSerivce.uploadFileByStream(userId, UploadSceneEnum.QRCODE.getType(), file);
    }

    /**
     * 获取预签名对象 URL
     *
     * @param userId       用户 ID
     * @param uploadUrlDTO 上传 URL DTO
     * @return {@link MinioVO }
     * @author qingmeng
     * @createTime: 2023/12/05 23:01:29
     */
    @Override
    public MinioVO getPreSignedObjectUrl(Long userId,UploadUrlDTO uploadUrlDTO) {
        MinioDTO minioDTO = FileAdapt.buildMinioDTO(userId,uploadUrlDTO);
        return minioSerivce.getPreSignedObjectUrl(minioDTO);
    }

    /**
     * 更新二维码网址
     *
     * @param userId 用户 ID
     * @author qingmeng
     * @createTime: 2023/12/05 23:08:15
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateQrcodeUrl(Long userId) {
        String qrcodeUrl = getQrcodeUrl(userId);
        sysUserDao.updateQrcode(userId,qrcodeUrl);
        userCache.delete(userId);
    }
}
