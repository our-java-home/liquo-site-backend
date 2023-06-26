package com.javahome.wine.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author 勿忘初心
 * @since 2023-06-21-17:25
 * 与数据库表做映射
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User {
    /**
     * @TableId 注解指定该字段为主键,auto属性已在application.yml中配置
     */
    @TableId
    private Long id;
    private String username;
    private String password;
    private Integer age;
    /***
     * @Version注解
     */
    @Version
    private Integer version;
    /**
     * 结合handle自动注入当前time
     * INSERT表示仅在插入操作执行时填充
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 结合handle自动注入当前time
     * INSERT_UPDATE表示在插入和更新操作执行时填充
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 逻辑删除标识,1已删除,0未删除
     */
    private Integer deleted;
}
