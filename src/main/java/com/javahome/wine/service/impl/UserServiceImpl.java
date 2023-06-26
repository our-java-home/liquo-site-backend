package com.javahome.wine.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javahome.wine.dto.UserDTO;
import com.javahome.wine.exception.BusinessException;
import com.javahome.wine.exception.ExceptionCodeEnum;
import com.javahome.wine.mapper.UserMapper;
import com.javahome.wine.model.User;
import com.javahome.wine.service.UserService;
import com.javahome.wine.vo.PageVO;
import com.javahome.wine.vo.ResultDataVO;
import com.javahome.wine.vo.UserVO;
import com.tuyang.beanutils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 勿忘初心
 * @since 2023-06-21-17:41
 * 用户接口实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResultDataVO addUser(UserDTO userDTO) {
        User user = new User();
        // 将userDTO获取的数据复制到user对象中
        userDTO.setId(null);
        BeanUtils.copyProperties(userDTO,user);
        userMapper.insert(user);
        return ResultDataVO.success(null);
    }

    /**
     * 更新删除操作前先查
     * @param userDTO
     * @return
     */
    @Override
    public ResultDataVO updateUser(UserDTO userDTO) {
        User user = userMapper.selectById(userDTO.getId());
        BeanUtils.copyProperties(userDTO, user);
        userMapper.updateById(user);
        return ResultDataVO.success(null);
    }

    @Override
    public ResultDataVO deleteUser(Long id) {
        User user = userMapper.selectById(id);
        if(user == null){
            throw new BusinessException(ExceptionCodeEnum.EC20001);
        }
        // 逻辑删除
        userMapper.deleteById(id);
        return ResultDataVO.success(null);
    }

    @Override
    public PageVO getAllUser(Integer pageNum, Integer pageSize) {
        // todo 参数二次校验，超出范围则抛异常
        // 添加查询条件
        Page<User> userPage = new Page<>(pageNum,pageSize);
        userMapper.selectPage(userPage, null);
        List<UserVO> userVos = userPage.getRecords().stream()
                .map(user -> {
                    UserVO userDTO = new UserVO();
                    BeanUtils.copyProperties(user,userDTO);
                    return userDTO;
                }).collect(Collectors.toList());
        return new PageVO(userVos, userVos.size());
    }

    @Override
    public UserVO getUserById(Long id) {
       User user = userMapper.selectById(id);
       // todo 待使用Optional优化
       if(user == null){
           throw new BusinessException(ExceptionCodeEnum.EC20001);
       }
       UserVO userVO = new UserVO();
       BeanUtils.copyProperties(user,userVO);
       return userVO;
    }
}
