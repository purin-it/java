package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.data.domain.Pageable;
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
     * メール送信オブジェクト
     */
    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * 1ページに表示する行数(application.ymlから取得)
     */
    @Value("${demo.list.pageSize}")
    private String listPageSize;

    /**
     * メールが利用可能か(application.ymlから取得)
     */
    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String mailEnabled;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DemoForm> demoFormList(SearchForm searchForm, Pageable pageable) {
        List<DemoForm> demoFormList = new ArrayList<>();
        //ユーザーデータテーブル(user_data)から検索条件に合うデータを取得する
        Collection<UserData> userDataList = mapper.findBySearchForm(searchForm, pageable);
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
    public void deleteById(String id){
        Long longId = stringToLong(id);
        mapper.deleteById(longId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public void createOrUpdate(DemoForm demoForm){
        //更新・追加処理を行うエンティティを生成
        UserData userData = getUserData(demoForm);
        //追加・更新処理
        if(demoForm.getId() == null){
            userData.setId(mapper.findMaxId() + 1);
            mapper.create(userData);
        }else{
            mapper.update(userData);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String checkForm(DemoForm demoForm, BindingResult result){
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
                //formオブジェクト・生年月日の日付のチェック処理を行い、問題なければ確認画面に遷移
                return "confirm";
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String checkSearchForm(SearchForm searchForm, BindingResult result){
        int checkDate =DateCheckUtil.checkSearchForm(searchForm);
        switch (checkDate){
            case 1:
                //生年月日_fromが不正な場合のエラー処理
                result.rejectValue("fromBirthYear", "validation.date-invalidate-from");
                result.rejectValue("fromBirthMonth", "validation.empty-msg");
                result.rejectValue("fromBirthDay", "validation.empty-msg");
                return "search";
            case 2:
                //生年月日_toが不正な場合のエラー処理
                result.rejectValue("toBirthYear", "validation.date-invalidate-to");
                result.rejectValue("toBirthMonth", "validation.empty-msg");
                result.rejectValue("toBirthDay", "validation.empty-msg");
                return "search";
            case 3:
                //生年月日_from＞生年月日_toの場合のエラー処理
                result.rejectValue("fromBirthYear", "validation.date-invalidate-from-to");
                result.rejectValue("fromBirthMonth", "validation.empty-msg");
                result.rejectValue("fromBirthDay", "validation.empty-msg");
                result.rejectValue("toBirthYear", "validation.empty-msg");
                result.rejectValue("toBirthMonth", "validation.empty-msg");
                result.rejectValue("toBirthDay", "validation.empty-msg");
                return "search";
            default:
                //正常な場合はnullを返却
                return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pageable getPageable(int pageNumber){
        Pageable pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                //現在ページ数を返却
                return pageNumber;
            }

            @Override
            public int getPageSize() {
                //1ページに表示する行数を返却
                //listPageSizeは、本プログラムの先頭に定義している
                return Integer.parseInt(listPageSize);
            }

            @Override
            public int getOffset() {
                //表示開始位置を返却
                //例えば、1ページに2行表示する場合の、2ページ目の表示開始位置は
                //(2-1)*2+1=3 で計算される
                return ((pageNumber - 1) * Integer.parseInt(listPageSize) + 1);
            }

            @Override
            public Sort getSort() {
                //ソートは使わないのでnullを返却
                return null;
            }
        };
        return pageable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getAllPageNum(SearchForm searchForm) {
        //1ページに表示する行数を取得
        int listPageSizeNum = Integer.parseInt(listPageSize);
        if(listPageSizeNum == 0){
            return 1;
        }
        //一覧画面に表示する全データを取得
        //第二引数のpageableにnullを設定することで、一覧画面に表示する全データが取得できる
        Collection<UserData> userDataList = mapper.findBySearchForm(searchForm, null);
        //全ページ数を計算
        //例えば、1ページに2行表示する場合で、全データ件数が5の場合、
        //(5+2-1)/2=3 と計算される
        int allPageNum = (userDataList.size() + listPageSizeNum - 1) / listPageSizeNum;
        return allPageNum == 0 ? 1 : allPageNum;
    }

    /**
     * {@inheritDoc}
     */
    //@Asyncアノテーションで、このメソッドが非同期で実行されるようにする
    @Async
    @Override
    public void sendCompleteMail(DemoForm demoForm){
        //メール利用可の場合は、メールを送信
        if("true".equals(mailEnabled)){
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("stanahas@amber.plala.or.jp");
            message.setSubject("登録・変更完了 : " + demoForm.getName());
            message.setText(getMailText(demoForm));
            javaMailSender.send(message);
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
        demoForm.setMemo(userData.getMemo());
        demoForm.setSex_value(userData.getSex_value());
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
        userData.setMemo(demoForm.getMemo());
        userData.setSex_value(demoForm.getSex_value());
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

    /**
     * メール本文を生成し返却する
     * @param demoForm DemoFormオブジェクト
     * @return メール本文
     */
    private String getMailText(DemoForm demoForm){
        String line_separator = System.lineSeparator();
        StringBuilder sb = new StringBuilder();
        sb.append("下記ユーザーの登録・変更処理が完了しました。");
        sb.append(line_separator);
        sb.append(line_separator);
        sb.append("名前 : " + demoForm.getName());
        sb.append(line_separator);
        sb.append("生年月日 : "
                + demoForm.getBirthYear() + "年"
                + demoForm.getBirthMonth() + "月"
                + demoForm.getBirthDay() + "日");
        sb.append(line_separator);
        sb.append("性別 : "
                + demoForm.getSexItems().get(demoForm.getSex()));
        sb.append(line_separator);
        sb.append("メモ : " + demoForm.getMemo());
        return sb.toString();
    }

}
