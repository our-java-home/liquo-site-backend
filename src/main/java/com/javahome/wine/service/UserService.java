package com.javahome.wine.service;

import com.javahome.wine.dto.UserDTO;
import com.javahome.wine.vo.PageVO;
import com.javahome.wine.vo.ResultDataVO;
import com.javahome.wine.vo.UserVO;

/**
 * @author 勿忘初心
 * @since 2023-06-21-17:41
 * 用户接口
 */

public interface UserService {
    /**
     * 新增用户
     * @param userDTO
     * @return
     */
    ResultDataVO addUser(UserDTO userDTO);

    /**
     * 更新用户
     * @param userDTO
     * @return
     */
    ResultDataVO updateUser(UserDTO userDTO);

    /**
     * 删除用户
     * @param id
     * @return
     */
    ResultDataVO deleteUser(Long id);

    /**
     * 获取所有用户(分页)
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageVO getAllUser(Integer pageNum, Integer pageSize);

    /**
     * 查询指定用户
     * @param id
     * @return
     */
    UserVO getUserById(Long id);

}
