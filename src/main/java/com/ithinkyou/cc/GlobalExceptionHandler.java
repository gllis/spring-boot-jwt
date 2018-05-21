package com.ithinkyou.cc;

import com.ithinkyou.cc.config.ErrCode;
import com.ithinkyou.cc.model.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 全局异常
 *
 * Created by GL on 2017/12/23.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常处理
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler({Exception.class})
    public Object exception(Exception e) throws Exception {
        e.printStackTrace();

        if (e instanceof AccessDeniedException) {
            return new Result(ErrCode.FORBIDDEN, null, e.getMessage());
        }
        return new Result(-1, null, e.getMessage());
    }


}
