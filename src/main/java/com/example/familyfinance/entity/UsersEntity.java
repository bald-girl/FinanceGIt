package com.example.familyfinance.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;


/**
 * <p>
 * 
 * </p>
 *
 * @author Miss Lee
 * @since 2022-01-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersEntity implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @TableId(value = "users_id", type = IdType.AUTO)
    private Integer usersId;

    private String usersName;

    private String usersPwd;

    private String usersRole;

    public UsersEntity(String usersName, String usersPwd) {
        this.usersName = usersName;
        this.usersPwd = usersPwd;
    }

    public UsersEntity(Integer usersId,String usersName, String usersPwd, Collection authorities) {
        this.usersId = usersId;
        this.usersName = usersName;
        this.usersPwd = usersPwd;
        this.authorities = authorities;
    }
    @TableField(exist = false)
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return usersPwd;
    }

    @Override
    public String getUsername() {
        return usersName;
    }

    /**
     * 用户账号是否过期
     * true: 未过期
     * false: 已过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 是否锁定
     * true: 未锁定
     * false: 锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 用户账号凭证(密码)是否过期
     * 简单的说就是可能会因为修改了密码导致凭证过期这样的场景
     * true: 过期
     * false: 无效
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 用户账号是否被启用
     * true: 启用
     * false: 未启用
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
