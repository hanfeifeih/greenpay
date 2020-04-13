package com.esiran.greenpay.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.esiran.greenpay.common.entity.BaseMapperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统菜单
 * </p>
 *
 * @author Militch
 * @since 2020-04-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("system_menu")
public class Menu extends BaseMapperEntity {

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
