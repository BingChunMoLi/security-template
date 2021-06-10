package com.bingchunmoli.admintemplate.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * @author BingChunMoLi
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails {
    /**
     * 用户标识
     */
    private String token;

    /**
     * 登录时间
     */
    private Long time;
    /**
     * 过期时间
     */
    private Long jtime;
    /**
     * IP
     */
    private String ip;
    /**
     * UA
     */
    private String Ua;
    /**
     * 登录地址
     */
    private String loginLocation;
    /**
     * 系统
     */
    private String Os;

    /**
     * 浏览器
     */
    private String brower;

    private Set<String> permissions;

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public LoginUser(User user, Set<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }
}
