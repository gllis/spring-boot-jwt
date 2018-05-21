package com.ithinkyou.cc.config;

/**
 * 错误代码
 *
 * @author GL
 * @created 2018/5/21.
 */
public class ErrCode {

    /**
     * 成功
     */
    public static final int OK = 0;
    /**
     * 系统繁忙
     */
    public static final int SYS_BUSY = -1;
    /**
     * 用户不存在
     */
    public static final int USER_NOT_FOUND = 4001;
    /**
     * 密码错误
     */
    public static final int AUTH_FAIL = 4002;
    /**
     * 权限不足
     */
    public static final int FORBIDDEN = 4003;

    /**
     * TOKEN 过期
     */
    public static final int TOKEN_EXPIRE = 40013;
}
