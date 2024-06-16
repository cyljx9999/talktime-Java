package com.qingmeng.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.qingmeng.adapt.FileAdapt;
import com.qingmeng.config.adapt.UserInfoAdapt;
import com.qingmeng.config.cache.UserCache;
import com.qingmeng.config.cache.UserFriendSettingCache;
import com.qingmeng.constant.SystemConstant;
import com.qingmeng.dao.SysUserDao;
import com.qingmeng.dto.file.MinioDTO;
import com.qingmeng.dto.file.ScanQrcodeDTO;
import com.qingmeng.dto.file.UploadUrlDTO;
import com.qingmeng.dto.login.CheckFriendDTO;
import com.qingmeng.entity.SysUser;
import com.qingmeng.entity.SysUserFriendSetting;
import com.qingmeng.enums.system.ScanQrcodeEnum;
import com.qingmeng.enums.chat.UploadSceneEnum;
import com.qingmeng.service.FileService;
import com.qingmeng.service.MinioService;
import com.qingmeng.service.SysUserFriendService;
import com.qingmeng.utils.AssertUtils;
import com.qingmeng.utils.CommonUtils;
import com.qingmeng.vo.common.ScanQrcodeInfoVO;
import com.qingmeng.vo.file.MinioVO;
import com.qingmeng.vo.user.CheckFriendVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年12月05日 21:51:00
 */
@Service
public class FileServiceImpl implements FileService {
    @Resource
    private MinioService minioService;
    @Resource
    private UserCache userCache;
    @Resource
    private SysUserDao sysUserDao;
    @Resource
    private SysUserFriendService sysUserFriendService;
    @Resource
    private UserFriendSettingCache userFriendSettingCache;

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
        return minioService.uploadFileByStream(userId, UploadSceneEnum.QRCODE.getType(), file);
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
    public MinioVO getPreSignedObjectUrl(Long userId, UploadUrlDTO uploadUrlDTO) {
        MinioDTO minioDTO = FileAdapt.buildMinioDTO(userId, uploadUrlDTO);
        return minioService.getPreSignedObjectUrl(minioDTO);
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
        sysUserDao.updateQrcode(userId, qrcodeUrl);
        userCache.delete(userId);
    }

    /**
     * 扫描二维码信息
     *
     * @param userId        用户 ID
     * @param scanQrcodeDTO 扫描二维码 DTO
     * @return {@link ScanQrcodeInfoVO }<{@link ? }>
     * @author qingmeng
     * @createTime: 2023/12/06 11:19:13
     */
    @Override
    public ScanQrcodeInfoVO<?> scanQrcodeInfo(Long userId,ScanQrcodeDTO scanQrcodeDTO) {
        if (Objects.equals(scanQrcodeDTO.getScanType(), ScanQrcodeEnum.FRIEND.getCode())) {
            // 把访问地址转File类型
            File file = CommonUtils.urlToFile(scanQrcodeDTO.getUrl());
            // 解析二维码中的数据
            Long friendId = Long.parseLong(QrCodeUtil.decode(file));
            // 获取好友
            SysUser sysUser = getFriend(friendId);
            // 判断是否为当前用户好友
            Boolean checkStatus = checkFriend(userId, sysUser);
            if (checkStatus){
                // 是好友，返回对应好友的封面信息
                SysUserFriendSetting friendSetting = userFriendSettingCache.get(CommonUtils.getFriendSettingCacheKey(userId, friendId));
                // todo 查询共同好友
                return UserInfoAdapt.scanQrcodeInfoToFriendVO(friendSetting, sysUser, new ArrayList<>());
            }else {
                // 非好友，返回对应的好友部分信息
                return UserInfoAdapt.scanQrcodeInfoToUserPartialVO(sysUser);
            }

        } else {
            // todo 查询当前群聊封面信息
        }
        return null;
    }

    /**
     * 获取朋友
     *
     * @param friendId 好友ID
     * @return {@link SysUser }
     * @author qingmeng
     * @createTime: 2023/12/06 11:23:08
     */
    private SysUser getFriend(Long friendId) {
        SysUser sysUser = userCache.get(friendId);
        AssertUtils.isNull(sysUser, "非法请求");
        return sysUser;
    }

    /**
     * 检查是否为好友
     *
     * @param userId  用户 ID
     * @param sysUser sys 用户
     * @return {@link Boolean } true 是 false 不是
     * @author qingmeng
     * @createTime: 2023/12/06 11:21:10
     */
    private Boolean checkFriend(Long userId, SysUser sysUser) {
        CheckFriendDTO friendDTO = new CheckFriendDTO();
        friendDTO.setFriendId(sysUser.getId());
        CheckFriendVO checkFriendVO = sysUserFriendService.checkFriend(userId, friendDTO);
        return checkFriendVO.getCheckStatus();
    }
}
