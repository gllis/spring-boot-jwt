package com.ithinkyou.cc.mapper;

import com.ithinkyou.cc.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户 Mapper
 *
 * @author GL
 * @created 2018/5/21.
 */
@Mapper
public interface UserMapper {

    User selectUser(String username);

    List<User> selectAll();

    int insert(User user);
}
