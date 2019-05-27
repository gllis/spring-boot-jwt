package cc.ithinkyou.service;

import cc.ithinkyou.model.User;

/**
 * User Service Interface
 *
 * @author GL
 * @created 2019/5/25.
 */
public interface UserService extends IServer<User> {

    /**
     * 通过用户名查找用户信息
     *
     * @param name
     * @return
     */
    User getByName(String name);
}
