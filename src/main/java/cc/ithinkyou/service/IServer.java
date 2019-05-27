package cc.ithinkyou.service;

import java.util.List;

/**
 * IServer Base Interface
 *
 * @author GL
 * @created 2019/5/25.
 */
public interface IServer<T> {

    List<T> getAll();

    T get(Object id);

    void save(T obj);

}
