package com.javahome.wine.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 勿忘初心
 * @since 2023-06-20-18:35
 * 数据传输对象，用于接收前端入参并绑定
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    // TODO 分组校验实现同一DTO复用

    @Min(value = 1,message = "id不能小于1")
    private Integer id;
    @NotBlank(message = "姓名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    @Range(min = 1,max=120,message = "年龄不在合法范围内")
    @NotNull(message = "age不能为空")
    private Integer age;
}
