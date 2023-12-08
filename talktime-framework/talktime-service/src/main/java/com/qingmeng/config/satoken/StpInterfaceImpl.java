package com.qingmeng.config.satoken;

import cn.dev33.satoken.stp.StpInterface;
import com.qingmeng.dao.ChatGroupManagerDao;
import com.qingmeng.enums.chat.GroupRoleEnum;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 自定义satoken权限验证接口扩展
 * @createTime 2023年11月10日 10:41:00
 */
@Component
public class StpInterfaceImpl implements StpInterface {
    @Resource
    private ChatGroupManagerDao chatGroupManagerDao;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> getPermissionList(Object loginId, String loginType) {
        // todo:获取权限字段列表
        return null;
    }

    /**
     * 返回一个账号所拥有的群聊角色标识集合
     * 角色校验仅作用与群聊相关操作
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<Integer> roleTypeList = chatGroupManagerDao.getRoleTypeList(Long.parseLong(loginType));
        return GroupRoleEnum.getByCodeList(roleTypeList);
    }

}
