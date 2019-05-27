package cc.ithinkyou.mapper;

import java.util.List;

/**
 * Base Mapper
 *
 * @author GL
 * @created 2019/5/25.
 */
public interface BaseMapper<T> {

    T selectByPrimaryKey(Object id);

    int insert(T obj);

    int updateByPrimaryKey(T obj);

    List<T> selectAll();
}
