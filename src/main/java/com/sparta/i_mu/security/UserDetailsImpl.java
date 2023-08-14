package com.sparta.i_mu.security;

import com.sparta.i_mu.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

//보안과 사용자 인증과 관련된 작업을 수행
@Getter
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final User user;
    private final String ROLE_HEADER = "ROLE_";

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        String role = ROLE_HEADER + user.getRole();
        authorities.add(new SimpleGrantedAuthority(role));

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }


    public String getNickname() {
        return user.getNickname();
    }

    public String getUserImage() {
        return user.getUserImage();
    }

    public Long getUserId() {
        return user.getId();
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


}