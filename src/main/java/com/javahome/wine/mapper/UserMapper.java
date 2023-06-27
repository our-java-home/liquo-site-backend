package com.javahome.wine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javahome.wine.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 勿忘初心
 * @since 2023-06-21-18:26
 * 继承BaseMapper,具有基本的操作数据库功能
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
