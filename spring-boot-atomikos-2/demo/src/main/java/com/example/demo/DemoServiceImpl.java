package com.example.demo;

import com.example.demo.mapper.primary.UserDataMapperPrimary;
import com.example.demo.mapper.secondary.UserDataMapperSecondary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.util.StringUtils;
import javax.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class DemoServiceImpl implements DemoService{

    /**
     * 分散データベースのトランザクション管理
     */
    @Autowired
    private JtaTransactionManager jtaTransactionManager;

    /**
     * Primaryデータベースのユーザーデータ(user_data)へアクセスするマッパー
     */
    @Autowired
    private UserDataMapperPrimary mapperPrimary;

    /**
     * Secondaryデータベースのユーザーデータ(user_data)へアクセスするマッパー
     */
    @Autowired
    private UserDataMapperSecondary mapperSecondary;

    //ログ出力のためのクラス
    private Logger logger = LogManager.getLogger(DemoServiceImpl.class);

    /**
     * 1ページに表示する行数(application.propertiesから取得)
     */
    @Value("${demo.list.pageSize}")
    private String listPageSize;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DemoForm> demoFormList(SearchForm searchForm, Pageable pageable) {
        List<DemoForm> demoFormList = new ArrayList<>();
        //ユーザーデータテーブル(user_data)から検索条件に合うデータを取得する
        Collection<UserData> userDataList = mapperPrimary.findBySearchForm(searchForm, pageable);
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
        UserData userData = mapperPrimary.findById(longId);
        return getDemoForm(userData);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteById(String id){
        UserTransaction userTransaction;
        try{
            //トランザクションを開始する
            userTransaction =  jtaTransactionManager.getUserTransaction();
            userTransaction.begin();
            try{
                //引数のIDに該当するデータを削除する
                Long longId = stringToLong(id);
                mapperPrimary.deleteById(longId);
                mapperSecondary.deleteById(longId);
                //トランザクションをコミットする
                userTransaction.commit();
                //「0:更新成功」を返す
                return 0;
            }catch (Exception ex2){
                //DB更新またはコミット処理でエラーが発生した場合
                logger.error(ex2);
                try{
                    //トランザクションをロールバックする
                    userTransaction.rollback();
                }catch (Exception ex3){
                    //ロールバック処理でエラーが発生した場合
                    logger.error(ex3);
                }finally {
                    //「1:更新失敗」を返す
                    return 1;
                }
            }
        }catch (Exception ex){
            //トランザクション開始処理でエラーが発生した場合
            logger.error(ex);
            //「1:更新失敗」を返す
            return 1;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createOrUpdate(DemoForm demoForm){
        UserTransaction userTransaction;
        try{
            //トランザクションを開始する
           userTransaction =  jtaTransactionManager.getUserTransaction();
           userTransaction.begin();
           try{
               //更新・追加処理を行うエンティティを生成
               UserData userData = getUserData(demoForm);
               //追加・更新処理を行う
               if(demoForm.getId() == null){
                   userData.setId(mapperPrimary.findMaxId() + 1);
                   mapperPrimary.create(userData);
                   mapperSecondary.create(userData);
               }else{
                   mapperPrimary.update(userData);
                   mapperSecondary.update(userData);
               }
               //トランザクションをコミットする
               userTransaction.commit();
               //「0:更新成功」を返す
               return 0;
           }catch (Exception ex2){
               //DB更新またはコミット処理でエラーが発生した場合
               logger.error(ex2);
               try{
                   //トランザクションをロールバックする
                   userTransaction.rollback();
               }catch (Exception ex3){
                   //ロールバック処理でエラーが発生した場合
                   logger.error(ex3);
               }finally {
                   //「1:更新失敗」を返す
                   return 1;
               }
           }
        }catch (Exception ex){
            //トランザクション開始処理でエラーが発生した場合
            logger.error(ex);
            //「1:更新失敗」を返す
            return 1;
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
                //現在ページ数を返却する
                return pageNumber;
            }

            @Override
            public int getPageSize() {
                //1ページに表示する行数を返却する
                //listPageSizeは、本プログラムの先頭に定義している
                return Integer.parseInt(listPageSize);
            }

            @Override
            public int getOffset() {
                //表示開始位置を返却する
                //例えば、1ページに2行表示する場合の、2ページ目の表示開始位置は
                //(2-1)*2+1=3 で計算される
                return ((pageNumber - 1) * Integer.parseInt(listPageSize) + 1);
            }

            @Override
            public Sort getSort() {
                //ソートは使わないのでnullを返却する
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
        //1ページに表示する行数を取得する
        int listPageSizeNum = Integer.parseInt(listPageSize);
        if(listPageSizeNum == 0){
            return 1;
        }
        //一覧画面に表示する全データを取得する
        //第二引数のpageableにnullを設定することで、一覧画面に表示する全データが取得できる
        Collection<UserData> userDataList = mapperPrimary.findBySearchForm(searchForm, null);
        //全ページ数を計算
        //例えば、1ページに2行表示する場合で、全データ件数が5の場合、
        //(5+2-1)/2=3 と計算される
        int allPageNum = (userDataList.size() + listPageSizeNum - 1) / listPageSizeNum;
        return allPageNum == 0 ? 1 : allPageNum;
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
        if(!StringUtils.isEmpty(demoForm.getId())){
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

}
