package cc.ithinkyou.mapper;

import cc.ithinkyou.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 用户 Mapper
 *
 * @author GL
 * @created 2018/5/21.
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    User selectByName(String username);

}
