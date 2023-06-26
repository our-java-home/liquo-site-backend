package com.javahome.wine.api.v1;

import com.javahome.wine.dto.UserDTO;
import com.javahome.wine.exception.ExceptionCodeEnum;
import com.javahome.wine.service.UserService;
import com.javahome.wine.vo.PageVO;
import com.javahome.wine.vo.ResultDataVO;
import com.javahome.wine.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * UserController 用户模块简单示例
 * @Author: 勿忘初心
 * @Date: 2023-06-26 0:56
 */


@RestController
@RequestMapping("/v1/user")
@Slf4j
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据Id获取用户信息
     * @param uid
     * @return
     */
    @GetMapping("/{id}")
    public UserVO getUser(@Min(value = 1,message = "输入的参数不合法") @PathVariable(name = "id") Long uid) {
        return userService.getUserById(uid);
    }

    /**
     * 新增用户信息
     * @param addUserDto
     * @return
     */
    @PostMapping
    public ResultDataVO addUser(@Validated @RequestBody UserDTO addUserDto) {
        return userService.addUser(addUserDto);
    }

    /**
     * 修改用户信息
     * @param updateUserDto
     * @return
     */
    @PutMapping
    public ResultDataVO updateUser(@Validated @RequestBody UserDTO updateUserDto) {
        return userService.updateUser(updateUserDto);
    }

    /***
     * 根据id删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResultDataVO deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    /**
     * 分页查询用户
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping
    public PageVO getUser(@NotNull @Range(min = 1,max = 100,message = "参数错误") Integer pageNum,
                          @NotNull @Range(min = 1,max = 10,message = "参数错误") Integer pageSize) {
        ExceptionCodeEnum.EC20001.getMessage();
        return userService.getAllUser(pageNum, pageSize);
    }
}
