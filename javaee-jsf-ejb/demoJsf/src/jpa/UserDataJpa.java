package jpa;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * USER_DATAテーブルへアクセスするJPA.
 */
// EJBのステートレスセッションBean(各クライアントに固有の値を保持しない)
@Stateless
public class UserDataJpa {

    @PersistenceContext
    private EntityManager em;

    /**
     * USER_DATAテーブルに引数のデータを追加する.
     * @param userData
     */
    // トランザクション属性をREQUIRED(トランザクションが開始していれば
    // そのトランザクションで、トランザクションが開始していない場合は
    // 新しいトランザクションを開始して実行)に設定
    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
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
     * USER_DATAテーブルの全件を取得する.
     * @return USER_DATAテーブル全件のリスト
     */
    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public List<UserData> getAll(){
        // UserDataテーブルのデータを全件取得し返却
        List<UserData> uList =  em.createQuery("FROM UserData u ORDER BY u.id ASC"
            , UserData.class).getResultList();

        // 取得したデータを返却
        return uList;
    }

    /**
     * 選択したIDをもつUSER_DATAテーブルの値を取得する.
     * @param selectId 選択したID
     * @return 選択したIDをもつUSER_DATAテーブルの値
     */
    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public UserData getById(String selectId) throws NumberFormatException{
        // UserDataテーブルの選択したIDをもつデータを取得
        Integer intId = Integer.parseInt(selectId);
        UserData userData = em.createQuery("FROM UserData u WHERE u.id = :intId", UserData.class)
                .setParameter("intId", intId)
                .getSingleResult();

        // 取得したデータを返却
        return userData;
    }

    /**
     * 引数のデータをもつUSER_DATAテーブルのデータを更新する.
     * @param userData
     */
    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public void update(UserData userData){
    	// IDが設定されている場合、更新
        if(userData.getId() != null){
            em.merge(userData);
            em.flush();
        }
    }

    /**
     * 引数のデータをもつUSER_DATAテーブルのデータを削除する.
     * @param userData
     */
    @TransactionAttribute(value = TransactionAttributeType.REQUIRED)
    public void delete(UserData userData){
    	// IDが設定されている場合、削除
        if(userData.getId() != null){
            em.remove(this.getById(String.valueOf(userData.getId())));
            em.flush();
        }
    }

}
