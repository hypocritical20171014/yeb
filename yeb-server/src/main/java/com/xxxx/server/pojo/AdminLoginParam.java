package com.xxxx.server.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Hefei
 * @create 2022-04-12-22:21
 * 用户登录实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AdminLOgin对象",description = "")
public class AdminLoginParam {
    @ApiModelProperty(value = "用户名",required = true)
    private String username;
    @ApiModelProperty(value = "密码",required = true)
    private String password;


}
