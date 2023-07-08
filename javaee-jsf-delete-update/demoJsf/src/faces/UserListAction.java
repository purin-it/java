package faces;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import jpa.UserData;
import jpa.UserDataJpa;
import lombok.Getter;

/**
 * USER_DATAリスト取得処理を定義.
 */
//「更新」「削除」リンクを機能させるため、RequestScopedからSessionScopeに変更
@Named(value="userListAction")
@SessionScoped
public class UserListAction implements Serializable{

    // シリアルバージョンUID
    private static final long serialVersionUID = -8890511854883241114L;

    /** USER_DATAリスト */
    @Getter
    private List<UserData> userDataList;

    /** UserDataテーブルへアクセスするJPA */
    @Inject
    private UserDataJpa userDataJpa;


    /** 一覧画面の初期表示処理 */
    public void initialize(){
        // USER_DATAテーブルの全件を取得する
        userDataList = userDataJpa.getAll();
    }

    /**
     * 入力画面への遷移(追加用).
     * @return 入力画面へのパス
     */
    public String toAdd(){
        return "add";
    }

    /**
     * 一覧画面への遷移.
     * @return 一覧画面へのパス
     */
    public String toList(){
        // 初期表示処理を呼び出し、一覧画面に遷移する
        this.initialize();
        return "list";
    }

}
