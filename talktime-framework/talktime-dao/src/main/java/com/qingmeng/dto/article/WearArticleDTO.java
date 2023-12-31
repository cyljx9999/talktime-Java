package com.qingmeng.dto.article;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author 清梦
 * @version 1.0.0
 * @Description
 * @createTime 2023年11月24日 21:58:00
 */
@Data
public class WearArticleDTO {

    /**
     * 物品 ID
     */
    @NotEmpty
    private List<Long> articleIds;

}
