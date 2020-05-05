package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
public class DemoServiceImpl implements DemoService{

    /**
     * ユーザーデータ(user_data)へアクセスするMongoTemplate
     */
    @Autowired
    private MongoTemplate template;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DemoForm> demoFormList(){
        List<DemoForm> demoFormList = new ArrayList<>();
        //ユーザーデータ(user_data)から全データを取得する
        Query query = new Query();
        query.with(new Sort(ASC, "id"));
        List<UserData> userDataList = template.find(query, UserData.class);
        for (UserData userData : userDataList) {
            demoFormList.add(getDemoForm(userData));
        }
        return demoFormList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DemoForm findByPKeyId(String pKeyID) {
        UserData userData = template.findById(pKeyID, UserData.class);
        return getDemoForm(userData);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(DemoForm demoForm){
        UserData userData = getUserData(demoForm);
        template.remove(userData);
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
            Query query = new Query();
            query.with(new Sort(DESC, "id"));
            UserData tmpUserData = template.findOne(query, UserData.class);
            userData.setId(tmpUserData == null ? 1 : tmpUserData.getId() + 1);
            template.save(userData);
        }else{
            template.save(userData);
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
        demoForm.setPKeyId(userData.getPKeyId());
        demoForm.setId(String.valueOf(userData.getId()));
        demoForm.setName(userData.getName());
        demoForm.setBirthYear(String.valueOf(userData.getBirth_year()));
        demoForm.setBirthMonth(String.valueOf(userData.getBirth_month()));
        demoForm.setBirthDay(String.valueOf(userData.getBirth_day()));
        demoForm.setSex(String.valueOf(userData.getSex()));
        demoForm.setMemo(userData.getMemo());
        demoForm.setSex_value(userData.getSex() == 1 ? "男" : "女");
        return demoForm;
    }

    /**
     * UserDataオブジェクトに引数のフォームの各値を設定する
     * @param demoForm DemoFormオブジェクト
     * @return ユーザーデータ
     */
    private UserData getUserData(DemoForm demoForm){
        UserData userData = new UserData();
        userData.setPKeyId(demoForm.getPKeyId());
        if(!StringUtils.isEmpty(demoForm.getId())){
            userData.setId(Long.valueOf(demoForm.getId()));
        }
        userData.setName(demoForm.getName());
        userData.setBirth_year(Integer.valueOf(demoForm.getBirthYear()));
        userData.setBirth_month(Integer.valueOf(demoForm.getBirthMonth()));
        userData.setBirth_day(Integer.valueOf(demoForm.getBirthDay()));
        userData.setSex(Integer.valueOf(demoForm.getSex()));
        userData.setMemo(demoForm.getMemo());
        return userData;
    }

}
