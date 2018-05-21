package com.ithinkyou.cc.model;

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
    private String username;
    @JsonIgnore()
    @JSONField(serialize=false)
    private String password;
    private String role;
    private int sex;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private Date created;
}
