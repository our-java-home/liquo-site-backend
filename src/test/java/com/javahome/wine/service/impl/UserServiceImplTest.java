package com.javahome.wine.service.impl;

import com.javahome.wine.mapper.UserMapper;
import com.javahome.wine.model.User;
import com.javahome.wine.vo.ResultDataVO;
import com.javahome.wine.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author 勿忘初心
 * @since 2023-06-21-19:29
 * UserServiceImpl类单元测试
 */

@SpringBootTest
@Transactional
@Rollback
@Slf4j
public class UserServiceImplTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 单元测试代码无返回值,且方法不能私有化,无参数
     * @return
     */
    @Test
    public void getUserById() {
        User user = userMapper.selectById(1);
        log.info("user "+user.toString());
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        Assertions.assertEquals("张三", userVO.getUsername());
    }
}
