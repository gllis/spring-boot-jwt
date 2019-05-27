package cc.ithinkyou.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import cc.ithinkyou.config.ErrCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回结果
 *
 * @author GL
 * @created 2018/5/21.
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {

    private int errcode = ErrCode.OK;
    private Object data;
    private Integer total;
    private Integer totalPage;
    private String errmsg = "ok";


    public Result(Object data) {
        this.data = data;
    }

    public Result(int errcode, Object data) {
        this.errcode = errcode;
        this.data = data;
    }

    public Result(int errcode, Object data, String errmsg) {
        this.errcode = errcode;
        this.data = data;
        this.errmsg = errmsg;
    }

    public Result(Object data, Integer total) {
        this.data = data;
        this.total = total;
    }

    public Result(Object data, Integer total, Integer totalPage) {
        this.data = data;
        this.total = total;
        this.totalPage = totalPage;
    }
}
