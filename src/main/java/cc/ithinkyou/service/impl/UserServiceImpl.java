package cc.ithinkyou.service.impl;

import cc.ithinkyou.mapper.UserMapper;
import cc.ithinkyou.model.User;
import cc.ithinkyou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User Service
 *
 * @author GL
 * @created 2019/5/25.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getAll() {
        return userMapper.selectAll();
    }

    @Override
    public User get(Object id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User getByName(String name) {
        return userMapper.selectByName(name);
    }

    @Override
    public void save(User user) {
        if (user.getId() != null && user.getId() > 0) {
            userMapper.updateByPrimaryKey(user);
        } else {
            userMapper.insert(user);
        }
    }
}
