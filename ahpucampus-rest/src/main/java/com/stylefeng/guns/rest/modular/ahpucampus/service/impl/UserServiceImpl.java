package com.stylefeng.guns.rest.modular.ahpucampus.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.rest.modular.ahpucampus.dao.UserMapper;
import com.stylefeng.guns.rest.modular.ahpucampus.model.User;
import com.stylefeng.guns.rest.modular.ahpucampus.service.IUserService;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-02-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public int setStatus(Integer userId, int status) {
        return this.baseMapper.setStatus(userId, status);
    }

    @Override
    public int changePwd(Integer userId, String pwd) {
        return this.baseMapper.changePwd(userId, pwd);
    }


    @Override
    public int setRoles(Integer userId, String roleIds) {
        return this.baseMapper.setRoles(userId, roleIds);
    }

    @Override
    public User getByAccount(String account) {
        return this.baseMapper.getByAccount(account);
    }
}
