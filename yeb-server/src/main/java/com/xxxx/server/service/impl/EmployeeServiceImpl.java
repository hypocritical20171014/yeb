package com.xxxx.server.service.impl;

import com.xxxx.server.pojo.Employee;
import com.xxxx.server.mapper.EmployeeMapper;
import com.xxxx.server.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hefei
 * @since 2022-04-12
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
