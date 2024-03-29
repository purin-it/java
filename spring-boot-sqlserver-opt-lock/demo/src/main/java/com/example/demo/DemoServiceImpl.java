package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
 
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class DemoServiceImpl implements DemoService{
 
    /**
     * ユーザーデータテーブル(user_data)へアクセスするマッパー
     */
    @Autowired
    private UserDataMapper mapper;
 
    /**
     * {@inheritDoc}
     */
    @Override
    public List<DemoForm> demoFormList() {
        List<DemoForm> demoFormList = new ArrayList<>();
        //ユーザーデータテーブル(user_data)から全データを取得する
        Collection<UserData> userDataList = mapper.findAll();
        for (UserData userData : userDataList) {
            demoFormList.add(getDemoForm(userData));
        }
        return demoFormList;
    }
 
    /**
     * {@inheritDoc}
     */
    @Override
    public DemoForm findById(String id) {
        Long longId = stringToLong(id);
        UserData userData = mapper.findById(longId);
        return getDemoForm(userData);
    }
 
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public int deleteById(DemoForm demoForm){
        //削除処理を行うエンティティを生成する
        UserData userData = getUserData(demoForm);
        //削除対象のデータを行ロックをかけ取得する
        Long longId = stringToLong(demoForm.getId());
        UserData updData = mapper.findByIdRowLock(longId);
        //バージョンが異なっている場合は、楽観ロックエラーとし、削除しない
        if(updData.getVersion() != demoForm.getVersion()){
            throw new RuntimeException("OptimisticLockingFailureException");
        }
        //削除処理を行い、更新件数を返却
        return mapper.deleteById(userData);
    }
 
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public int createOrUpdate(DemoForm demoForm){
        //更新・追加処理を行うエンティティを生成する
        UserData userData = getUserData(demoForm);
        //追加・更新処理を行い、更新件数を返却
        if(demoForm.getId() == null){
            userData.setId(mapper.findMaxId() + 1);
            return mapper.create(userData);
        }else{
            //更新対象のデータを行ロックをかけ取得する
            UserData updData = mapper.findByIdRowLock(userData.getId());
            //バージョンが異なっている場合は、楽観ロックエラーとし、更新しない
            if (updData.getVersion() != userData.getVersion()) {
                throw new RuntimeException("OptimisticLockingFailureException");
            }
            return mapper.update(userData);
        }
    }
 
    /**
     * {@inheritDoc}
     */
    @Override
    public String checkForm(DemoForm demoForm, BindingResult result, String normalPath){
        //formオブジェクトのチェック処理を行う
        if(result.hasErrors()){
            //エラーがある場合は、入力画面のままとする
            return "input";
        }
        //生年月日の日付チェック処理を行う
        //エラーがある場合は、エラーメッセージ・エラーフィールドの設定を行い、
        //入力画面のままとする
        int checkDate = DateCheckUtil.checkDate(demoForm.getBirthYear()
                , demoForm.getBirthMonth(), demoForm.getBirthDay());
        switch(checkDate){
            case 1:
                //生年月日_年が空文字の場合のエラー処理
                result.rejectValue("birthYear", "validation.date-empty"
                        , new String[]{"生年月日_年"}, "");
                return "input";
            case 2:
                //生年月日_月が空文字の場合のエラー処理
                result.rejectValue("birthMonth", "validation.date-empty"
                        , new String[]{"生年月日_月"}, "");
                return "input";
            case 3:
                //生年月日_日が空文字の場合のエラー処理
                result.rejectValue("birthDay", "validation.date-empty"
                        , new String[]{"生年月日_日"}, "");
                return "input";
            case 4:
                //生年月日の日付が不正な場合のエラー処理
                result.rejectValue("birthYear", "validation.date-invalidate");
                //生年月日_月・生年月日_日は、エラーフィールドの設定を行い、
                //メッセージを空文字に設定している
                result.rejectValue("birthMonth", "validation.empty-msg");
                result.rejectValue("birthDay", "validation.empty-msg");
                return "input";
            case 5:
                //生年月日の日付が未来日の場合のエラー処理
                result.rejectValue("birthYear", "validation.date-future");
                //生年月日_月・生年月日_日は、エラーフィールドの設定を行い、
                //メッセージを空文字に設定している
                result.rejectValue("birthMonth", "validation.empty-msg");
                result.rejectValue("birthDay", "validation.empty-msg");
                return "input";
            default:
                //性別が不正に書き換えられていないかチェックする
                if(!demoForm.getSexItems().keySet().contains(demoForm.getSex())){
                    result.rejectValue("sex", "validation.sex-invalidate");
                    return "input";
                }
                //エラーチェックに問題が無いので、正常時の画面遷移先に遷移
                return normalPath;
        }
    }
 
    /**
     * DemoFormオブジェクトに引数のユーザーデータの各値を設定する
     * @param userData ユーザーデータ
     * @return DemoFormオブジェクト
     */
    private DemoForm getDemoForm(UserData userData){
        if(userData == null){
            return null;
        }
        DemoForm demoForm = new DemoForm();
        demoForm.setId(String.valueOf(userData.getId()));
        demoForm.setName(userData.getName());
        demoForm.setBirthYear(String.valueOf(userData.getBirthY()));
        demoForm.setBirthMonth(String.valueOf(userData.getBirthM()));
        demoForm.setBirthDay(String.valueOf(userData.getBirthD()));
        demoForm.setSex(userData.getSex());
        demoForm.setSex_value(userData.getSex_value());
        demoForm.setVersion(userData.getVersion());
        return demoForm;
    }
 
    /**
     * UserDataオブジェクトに引数のフォームの各値を設定する
     * @param demoForm DemoFormオブジェクト
     * @return ユーザーデータ
     */
    private UserData getUserData(DemoForm demoForm){
        UserData userData = new UserData();
        if(!DateCheckUtil.isEmpty(demoForm.getId())){
            userData.setId(Long.valueOf(demoForm.getId()));
        }
        userData.setName(demoForm.getName());
        userData.setBirthY(Integer.valueOf(demoForm.getBirthYear()));
        userData.setBirthM(Integer.valueOf(demoForm.getBirthMonth()));
        userData.setBirthD(Integer.valueOf(demoForm.getBirthDay()));
        userData.setSex(demoForm.getSex());
        userData.setSex_value(demoForm.getSex_value());
        userData.setVersion(demoForm.getVersion());
        return userData;
    }
 
    /**
     * 引数の文字列をLong型に変換する
     * @param id ID
     * @return Long型のID
     */
    private Long stringToLong(String id){
        try{
            return Long.parseLong(id);
        }catch(NumberFormatException ex){
            return null;
        }
    }
 
}