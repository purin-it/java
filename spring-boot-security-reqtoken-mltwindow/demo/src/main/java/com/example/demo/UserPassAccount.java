package com.example.demo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

public class UserPassAccount implements UserDetails {

    /** UserPassオブジェクト */
    private UserPass userPass;

    /** ユーザー権限情報 */
    private Collection<GrantedAuthority> authorities;

    /**
     * Spring-Security用のユーザーアカウント情報(UserDetails)を作成する
     * @param userPass UserPassオブジェクト
     * @param authorities ユーザー権限情報
     */
    public UserPassAccount(UserPass userPass, Collection<GrantedAuthority> authorities){
        this.userPass = userPass;
        this.authorities = authorities;
    }

    /**
     * ユーザー権限情報を取得する
     * @return ユーザー権限情報
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    /**
     * パスワードを取得する
     * @return パスワード
     */
    @Override
    public String getPassword() {
        return userPass.getPass();
    }

    /**
     * ユーザー名を取得する
     * @return ユーザー名
     */
    @Override
    public String getUsername() {
        return userPass.getName();
    }

    /**
     * アカウントが期限切れでないかを取得する
     * @return アカウントが期限切れでないか
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * アカウントがロックされていないかを取得する
     * @return アカウントがロックされていないか
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * アカウントが認証期限切れでないかを取得する
     * @return アカウントが認証期限切れでないか
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * アカウントが利用可能かを取得する
     * @return アカウントが利用可能か
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
