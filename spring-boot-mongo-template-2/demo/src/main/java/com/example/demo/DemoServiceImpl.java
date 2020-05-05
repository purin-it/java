package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
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
        //ユーザーデータテーブル(user_data)から取得する開始位置・終了位置を算出
        int limit = Integer.parseInt(listPageSize);
        int offset = (searchForm.getCurrentPageNum() - 1) * limit;
        //ユーザーデータテーブル(user_data)から検索条件に合うデータを取得する
        List<UserData> userDataList = getUserDataList(searchForm, offset, limit);
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
            public long getOffset() {
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

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
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
        List<UserData> userDataList = getUserDataList(searchForm, 0, 0);
        //全ページ数を計算
        //例えば、1ページに2行表示する場合で、全データ件数が5の場合、
        //(5+2-1)/2=3 と計算される
        int allPageNum = (userDataList.size() + listPageSizeNum - 1) / listPageSizeNum;
        return allPageNum == 0 ? 1 : allPageNum;
    }

    /**
     * 検索用Formオブジェクトの条件にあてはまるユーザーデータを取得する
     * @param searchForm 検索用Formオブジェクト
     * @param offset  取得開始位置
     * @param limit   取得件数(全件取得する場合は0を指定)
     * @return ユーザーデータリスト
     */
    private List<UserData> getUserDataList(SearchForm searchForm, int offset, int limit){
        Query query = new Query();
        Criteria criteria1 = null;
        Criteria criteria2 = null;
        //検索条件を指定
        //氏名が指定された場合
        if(!StringUtils.isEmpty(searchForm.getSearchName())){
            query.addCriteria(Criteria.where("name").regex(".*" + searchForm.getSearchName() + ".*", "i"));
        }
        //生年月日(From)が指定された場合の条件を作成
        if(!StringUtils.isEmpty(searchForm.getFromBirthYear())){
            criteria1 = new Criteria().orOperator(
                    Criteria.where("birth_year").gt(Integer.parseInt(searchForm.getFromBirthYear())),
                    new Criteria().andOperator(
                            Criteria.where("birth_year").is(Integer.parseInt(searchForm.getFromBirthYear())),
                            Criteria.where("birth_month").gt(Integer.parseInt(searchForm.getFromBirthMonth()))
                    ),
                    new Criteria().andOperator(
                            Criteria.where("birth_year").is(Integer.parseInt(searchForm.getFromBirthYear())),
                            Criteria.where("birth_month").is(Integer.parseInt(searchForm.getFromBirthMonth())),
                            Criteria.where("birth_day").gte(Integer.parseInt(searchForm.getFromBirthDay()))
                    )
            );
        }
        //生年月日(To)が指定された場合の条件を作成
        if(!StringUtils.isEmpty(searchForm.getToBirthYear())){
            criteria2 = new Criteria().orOperator(
                    Criteria.where("birth_year").lt(Integer.parseInt(searchForm.getToBirthYear())),
                    new Criteria().andOperator(
                            Criteria.where("birth_year").is(Integer.parseInt(searchForm.getToBirthYear())),
                            Criteria.where("birth_month").lt(Integer.parseInt(searchForm.getToBirthMonth()))
                    ),
                    new Criteria().andOperator(
                            Criteria.where("birth_year").is(Integer.parseInt(searchForm.getToBirthYear())),
                            Criteria.where("birth_month").is(Integer.parseInt(searchForm.getToBirthMonth())),
                            Criteria.where("birth_day").lte(Integer.parseInt(searchForm.getToBirthDay()))
                    )
            );
        }
        //生年月日(From), 生年月日(To)の両方が指定された場合
        if(criteria1 != null && criteria2 != null){
            query.addCriteria(new Criteria().andOperator(criteria1, criteria2));
        //生年月日(From)が指定された場合
        }else if(criteria1 != null){
            query.addCriteria(criteria1);
        //生年月日(To)が指定された場合
        }else if(criteria2 != null){
            query.addCriteria(criteria2);
        }
        //性別が指定された場合
        if(!StringUtils.isEmpty(searchForm.getSearchSex())){
            query.addCriteria(Criteria.where("sex").is(Integer.parseInt(searchForm.getSearchSex())));
        }
        //ソート順を指定
        query.with(new Sort(ASC, "id"));
        //開始位置(offset)・終了位置(limit)を指定
        query.skip(offset);
        if(limit != 0){
            query.limit(limit);
        }
        List<UserData> userDataList = template.find(query, UserData.class);
        return userDataList;
    }

}
