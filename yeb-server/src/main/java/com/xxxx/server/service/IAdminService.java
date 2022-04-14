package com.xxxx.server.service;

import com.xxxx.server.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.RespBean;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hefei
 * @since 2022-04-12
 */
public interface IAdminService extends IService<Admin> {

    /**
     * 登录之后返回token
     */
    RespBean login(@Param("username") String username, @Param("password") String password, @Param("httpServletRequest") HttpServletRequest httpServletRequest);

    /**
     * 根据用户名返回用户对象
     * */
    Admin getAdminByUserName(String username);
}
