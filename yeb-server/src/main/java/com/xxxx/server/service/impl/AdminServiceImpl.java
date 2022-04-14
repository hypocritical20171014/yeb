package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxx.server.config.security.JwtTokenUtil;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.mapper.AdminMapper;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hefei
 * @since 2022-04-12
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private UserDetailsService userDetailsService; //Spring Security 的内置接口，来获取输入的用户米和密码

    @Autowired
    private PasswordEncoder passwordEncoder; // 密码加密工具-md5

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private AdminMapper adminMapper;

    /**
    * 登录之后返回token
     * @param username
     * @param password
     * @param httpServletRequest
    * */
    @Override
    public RespBean login(String username, String password, HttpServletRequest httpServletRequest) {
        // 登录
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);// 通过Scurity load方法来实现登录
        //如果user为空或者密码不正确的情况下
        if( null == userDetails || passwordEncoder.matches(password,userDetails.getPassword())){
            return RespBean.error("密码不正确");
        }
        //数据库字段 isEnable 代表是否是可用的
        if( !userDetails.isEnabled()){
            return RespBean.error("账号被禁用，请联系管理员");
        }
        //更新security登录用户对象
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,null,userDetails.getAuthorities()
        );
        // 放在全局里面
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        //登录成功 生成token.通过SpringSecurity
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);// 头部信息给前端，让其放在请求头里

        return RespBean.success("登陆成功",tokenMap);
    }

    @Override
    public Admin getAdminByUserName(String username) {
        //从数据库中获取用户信息
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username).eq("enabled",true));
    }
}
