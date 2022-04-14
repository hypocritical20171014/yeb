package com.xxxx.server.controller;

import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.AdminLoginParam;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @author Hefei
 * @create 2022-04-12-22:24
 *
 */
@Api(tags = "LoginController")
@RestController
public class LoginController {

    @Autowired
    private IAdminService adminService;

    @ApiOperation(value="登录之后返回token")
    @PostMapping("/login")
    public RespBean login(AdminLoginParam adminLoginParam, HttpServletRequest httpServletRequest){
        //调取service层的登陆方法，正常返回token
        return adminService.login(adminLoginParam.getUsername(),adminLoginParam.getPassword(),httpServletRequest);
    }
    @ApiOperation(value = "获取当前登陆用户的信息")
    @GetMapping("/admin/info")
    //principal为Security的全局对象，登录之后Secrity将用户添加进去
    public Admin getAdminInfo(Principal principal){
        if(null == principal){
            return null;
        }
        //通过该对象来获取用户名
        String username = principal.getName();
        //通过传入用户名来调用service获取完整的用户对象
        Admin admin = adminService.getAdminByUserName(username);
        //对返回的用户对象的密码进行加密
        admin.setPassword(null);
        return admin;
    }

    @ApiOperation(value = "退出登陆")
    @PostMapping("/logout")
    public RespBean logout(){
        return RespBean.success("注销成功");//logout之后,给前端200状态码，前端删除请求头里的token
    }
}
