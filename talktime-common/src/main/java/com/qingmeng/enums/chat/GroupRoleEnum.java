package com.qingmeng.enums.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description 群组角色
 * @createTime 2023年11月11日 12:42:00
 */
@Getter
@AllArgsConstructor
public enum GroupRoleEnum {
    /**
     * 群组角色枚举
     */
    GROUP_OWNER(0, "GroupOwner"),
    GROUP_MANAGEMENT(1, "Management"),
    GROUP_MEMBER(2, "Member"),
    ;

    private final Integer code;
    private final String msg;

    /**
     * 通过code集合获取对应的msg集合
     *
     * @return {@link List }<{@link Map }<{@link String }, {@link String }>>
     * @author qingmeng
     * @createTime: 2023/12/08 08:54:37
     */
    public static List<String> getByCodeList(List<Integer> codeList) {
        return codeList.stream().map(GroupRoleEnum::findNameByCode).collect(Collectors.toList());
    }

    /**
     * 根据code查找
     *
     * @param code 枚举code
     * @return {@link String }
     * @author qingmeng
     * @createTime: 2023/12/08 09:08:15
     */
    private static String findNameByCode(Integer code) {
        for (GroupRoleEnum statusEnum : GroupRoleEnum.values()) {
            if (statusEnum.getCode().equals(code)) {
                return statusEnum.getMsg();
            }
        }
        return null;
    }

}
