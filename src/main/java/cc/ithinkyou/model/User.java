package cc.ithinkyou.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * 用户 Model
 *
 * @author GL
 * @created 2018/5/21.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private Integer id;
    private String username;         // 用户名
    @JSONField(serialize=false)
    private String password;         // 密码
    private String role;             // 角色
    private int sex;                 // 性别
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private Date created;            // 创建时间
}
