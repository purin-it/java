package jpa;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * USER_DATAテーブルへアクセスするJPA.
 */
@RequestScoped
public class UserDataJpa {

    @PersistenceContext
    private EntityManager em;

    /**
     * USER_DATAテーブルに引数のデータを追加する
     * @param userData
     */
    @Transactional
    public void regist(UserData userData){
        // UserDataテーブルのid最大値を取得
        Integer maxId = em.createQuery("SELECT MAX(u.id) FROM UserData u"
               , Integer.class).getSingleResult();

        // id最大値がNULLの場合は、0に変換
        maxId = (maxId == null) ? 0 : maxId;

        // USER_DATAテーブルのIDを設定後、登録処理を実行
        userData.setId(maxId + 1);
        em.persist(userData);
        em.flush();
    }

    /**
     * USER_DATAテーブルの全件を取得する
     * @return USER_DATAテーブル全件のリスト
     */
    @Transactional
    public List<UserData> getAll(){
        // UserDataテーブルのデータを全件取得し返却
        List<UserData> uList =  em.createQuery("FROM UserData u ORDER BY u.id ASC"
            , UserData.class).getResultList();

        // 取得したデータを返却
        return uList;
    }

}
