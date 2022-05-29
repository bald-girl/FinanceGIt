package com.example.familyfinance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.familyfinance.entity.UsersEntity;
import com.example.familyfinance.mapper.UsersEntityMapper;
import com.example.familyfinance.service.UsersEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImpl  extends ServiceImpl<UsersEntityMapper, UsersEntity> implements UsersEntityService, UserDetailsService {
    @Autowired
    UsersEntityMapper userMapper;

    @Autowired
    private UsersEntityService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    private RedisTemplate redisTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {//实际上这里的username是userId
        //根据用户名查询数据库
        UsersEntity user = userService.getById(username);
        if (user == null) {//数据库没有该账号，认证失败
            throw new UsernameNotFoundException("账号或密码错误");
        }else {
            //添加用户角色
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getUsersRole()));

            //数据库中密码已加密 所以登录时不需要再对获取到的密码加密
            UsersEntity newUser = new UsersEntity(user.getUsersId(),user.getUsersName(), user.getUsersPwd(), authorities);
//            redisTemplate.opsForValue().set(username,newUser);

            System.out.println("loadUserByUsername登录："+newUser.getUsername() + "   " + passwordEncoder.encode(newUser.getPassword()) + "  " + newUser.getAuthorities());
            return newUser;
        }
    }


    /**
     * 注册操作
     * @param user
     * @return
     */
    public boolean register(UsersEntity user) {
        //对密码进行加密
        user.setUsersPwd(new BCryptPasswordEncoder().encode(user.getUsersPwd()));
        //设置用户默认身份
        user.setUsersRole("user");
        boolean insert = userService.save(user);
        return insert;
    }
}
