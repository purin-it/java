package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class UserPassAccountService implements UserDetailsService {

    /**
     * ユーザーパスワードデータテーブル(user_pass)へアクセスするマッパー
     */
    @Autowired
    private UserPassMapper userPassMapper;

    /**
     * 指定したユーザー名をもつSpring-Security用のユーザーアカウント情報を取得する
     * @param username ユーザー名
     * @return 指定したユーザー名をもつSpring-Security用のユーザーアカウント情報
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("ユーザー名を入力してください");
        }
        //指定したユーザー名をもつUserPassオブジェクトを取得する
        UserPass userPass = userPassMapper.findByName(username);
        if(userPass == null){
            throw new UsernameNotFoundException("ユーザーが見つかりません");
        }
        //指定したユーザー名をもつSpring-Security用のユーザーアカウント情報を取得する
        return new UserPassAccount(userPass);
    }

    /**
     * 指定したユーザー名・パスワードをもつレコードをユーザーパスワードデータテーブル(user_pass)に登録する
     * @param username ユーザー名
     * @param password パスワード
     * @param auth 権限
     */
    @Transactional
    public void registerUser(String username, String password, String auth){
        if(StringUtils.isEmpty(username)
                || StringUtils.isEmpty(password) || StringUtils.isEmpty(auth)){
            return;
        }
        //指定したユーザー名をもつUserPassオブジェクトを取得する
        UserPass userPass = userPassMapper.findByName(username);
        //UserPassオブジェクトが無ければ追加・あれば更新する
        if(userPass == null){
            userPass = new UserPass(username, password, auth);
            userPassMapper.create(userPass);
        }else{
            userPassMapper.update(userPass);
        }
    }
}
