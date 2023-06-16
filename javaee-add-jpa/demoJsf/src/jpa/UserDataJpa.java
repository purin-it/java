package jpa;

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

}
