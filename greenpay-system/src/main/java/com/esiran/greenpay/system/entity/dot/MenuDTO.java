package com.esiran.greenpay.system.entity.dot;

import com.esiran.greenpay.common.entity.BaseMapperEntity;
import lombok.Data;

/**
 * @author han
 */
@Data
public class MenuDTO extends BaseMapperEntity {

    /**
     * 菜单标题
     */
    private String title;

    /**
     * 菜单标识
     */
    private String mark;

    /**
     * 菜单类型（1:目录,2:菜单,3:按钮）
     */
    private Boolean type;

    /**
     * 目录图标
     */
    private String icon;

    /**
     * 菜单路由
     */
    private String path;

    /**
     * 上级菜单ID
     */
    private Integer parentId;

    /**
     * 排序权重
     */
    private Integer sorts;

    private String extra;
}
