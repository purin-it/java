package com.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DemoDbRepository {

    //ログ出力のためのクラス
    private Logger logger = LogManager.getLogger(DemoDbRepository.class);

    /**
     * データソース名(application.propertiesから取得)
     */
    @Value("${spring.datasource.name}")
    private String jndiName;

    /**
     * 指定したIDをもつユーザーデータテーブル(user_data)のデータを取得するSQL
     */
    private static final String findByIdSql = "SELECT id, name"
            + ", birth_year AS birthY, birth_month AS birthM, birth_day AS birthD "
            + ", sex, memo FROM USER_DATA WHERE id = ?";

    /**
     * 指定したIDをもつユーザーデータテーブル(user_data)のデータを削除するSQL
     */
    private static final String deleteSql = "DELETE FROM USER_DATA WHERE id = ?";

    /**
     * 指定したユーザーデータテーブル(user_data)のデータを追加するSQL
     */
    private static final String insertSql = "INSERT INTO USER_DATA ( id, name "
            + ", birth_year, birth_month, birth_day, sex, memo ) "
            + " VALUES ( ?, ?, ?, ?, ?, ?, ? ) ";

    /**
     * 指定したユーザーデータテーブル(user_data)のデータを更新するSQL
     */
    private static final String updateSql = "UPDATE USER_DATA SET name = ? "
            + ", birth_year = ?, birth_month = ?, birth_day = ?, sex = ?"
            + ", memo = ? WHERE id = ?";

    /**
     * ユーザーデータテーブル(user_data)の最大値IDを取得するSQL
     */
    private static final String findMaxIdSql
            = "SELECT NVL(max(id), 0) AS maxId FROM USER_DATA";

    /**
     * ユーザーデータテーブル(user_data)から検索条件に合うデータを取得する
     * @param searchForm 検索用Formオブジェクト
     * @param pageable ページネーションオブジェクト
     * @return ユーザーデータテーブル(user_data)の検索条件に合うデータ
     */
    public List<UserData> findBySearchForm(SearchForm searchForm, Pageable pageable){
        List<UserData> userDataList = new ArrayList<>();
        Connection conn = getDbConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            String selectSql = getFindBySearchSql(searchForm, pageable);
            stmt = conn.prepareStatement(selectSql);
            logger.info(selectSql);
            rs = stmt.executeQuery();
            while(rs.next()){
                UserData userData = new UserData();
                userData.setId(rs.getLong("id"));
                userData.setName(rs.getString("name"));
                userData.setBirthY(rs.getInt("birthY"));
                userData.setBirthM(rs.getInt("birthM"));
                userData.setBirthD(rs.getInt("birthD"));
                userData.setSex(String.valueOf(rs.getInt("sex")));
                userData.setMemo(rs.getString("memo"));
                userData.setSex_value(rs.getString("sex_value"));
                userDataList.add(userData);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeResultSet(rs);
            closeStatement(stmt);
            closeDbConnection(conn);
        }
        return userDataList;
    }

    /**
     * ユーザーデータテーブル(user_data)から検索条件に合うデータを取得するSQLを生成する
     * @param searchForm 検索用Formオブジェクト
     * @param pageable ページネーションオブジェクト
     * @return ユーザーデータテーブル(user_data)から取得するSQL
     */
    private String getFindBySearchSql(SearchForm searchForm, Pageable pageable){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT u.id, u.name, u.birth_year as birthY, u.birth_month as birthM ");
        sb.append(", u.birth_day as birthD, u.sex, u.memo, u.sex_value ");
        sb.append("FROM ( SELECT ");
        sb.append("u1.id, u1.name, u1.birth_year, u1.birth_month, u1.birth_day ");
        sb.append(", u1.sex, u1.memo, m.sex_value, ROW_NUMBER() OVER (ORDER BY u1.id) AS rn ");
        sb.append(" FROM USER_DATA u1, M_SEX m ");
        sb.append(" WHERE u1.sex = m.sex_cd ");
        if(!DateCheckUtil.isEmpty(searchForm.getSearchName())){
            sb.append(" AND u1.name like '%" + searchForm.getSearchName() + "%' ");
        }
        if(!DateCheckUtil.isEmpty(searchForm.getFromBirthYear())){
            sb.append(" AND " + searchForm.getFromBirthYear()
                    + " || lpad(" + searchForm.getFromBirthMonth() +  ", 2, '0')"
                    + " || lpad(" + searchForm.getFromBirthDay() +  ", 2, '0')"
                    + " <= u1.birth_year || lpad(u1.birth_month, 2, '0') "
                    + "|| lpad(u1.birth_day, 2, '0') ");
        }
        if(!DateCheckUtil.isEmpty(searchForm.getToBirthYear())){
            sb.append(" AND u1.birth_year || lpad(u1.birth_month, 2, '0') "
                    + "|| lpad(u1.birth_day, 2, '0') "
                    + " <= " + searchForm.getToBirthYear()
                    + "|| lpad(" + searchForm.getToBirthMonth() +  ", 2, '0')"
                    + "|| lpad(" + searchForm.getToBirthDay() + ", 2, '0') ");
        }
        if(!DateCheckUtil.isEmpty(searchForm.getSearchSex())){
            sb.append(" AND u1.sex = " + searchForm.getSearchSex());
        }
        sb.append(" ORDER BY u1.id");
        sb.append(" ) u");
        if(pageable != null && pageable.getPageSize() > 0){
            sb.append(" WHERE u.rn BETWEEN " + pageable.getOffset()
                    + " AND " + (pageable.getOffset() + pageable.getPageSize() - 1));
        }
        return sb.toString();
    }

    /**
     * 指定したIDをもつユーザーデータテーブル(user_data)のデータを取得する
     * @param id ID
     * @return ユーザーデータテーブル(user_data)の指定したIDのデータ
     */
    public UserData findById(Long id){
        UserData userData = null;
        Connection conn = getDbConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            stmt = conn.prepareStatement(findByIdSql);
            stmt.setLong(1, id);
            logger.info(findByIdSql);
            logger.info("Parameters: " + id + "(Long)");
            rs = stmt.executeQuery();
            while(rs.next()){
                userData = new UserData();
                userData.setId(rs.getLong("id"));
                userData.setName(rs.getString("name"));
                userData.setBirthY(rs.getInt("birthY"));
                userData.setBirthM(rs.getInt("birthM"));
                userData.setBirthD(rs.getInt("birthD"));
                userData.setSex(String.valueOf(rs.getInt("sex")));
                userData.setMemo(rs.getString("memo"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeResultSet(rs);
            closeStatement(stmt);
            closeDbConnection(conn);
        }
        return userData;
    }

    /**
     * 指定したIDをもつユーザーデータテーブル(user_data)のデータを削除する
     * @param id ID
     */
    public void deleteById(Long id){
        Connection conn = getDbConnection();
        PreparedStatement stmt = null;
        try{
            stmt = conn.prepareStatement(deleteSql);
            stmt.setLong(1, id);
            logger.info(deleteSql);
            logger.info("Parameters: " + id + "(Long)");
            stmt.executeUpdate();
            commitDbConnection(conn);
        }catch (SQLException e){
            rollbackDbConnection(conn);
            e.printStackTrace();
        }finally {
            closeStatement(stmt);
            closeDbConnection(conn);
        }
    }

    /**
     * 指定したユーザーデータテーブル(user_data)のデータを追加する
     * @param userData ユーザーデータテーブル(user_data)の追加データ
     */
    public void create(UserData userData){
        Connection conn = getDbConnection();
        PreparedStatement stmt = null;
        try{
            stmt = conn.prepareStatement(insertSql);
            stmt.setLong(1, userData.getId());
            stmt.setString(2, userData.getName());
            stmt.setInt(3, userData.getBirthY());
            stmt.setInt(4, userData.getBirthM());
            stmt.setInt(5, userData.getBirthD());
            stmt.setString(6, userData.getSex());
            stmt.setString(7, userData.getMemo());
            logger.info(insertSql);
            logger.info("Parameters: " + userData.getId() + "(Long), "
                    + userData.getName() + "(String), "
                    + userData.getBirthY() + "(Integer), "
                    + userData.getBirthM() + "(Integer), "
                    + userData.getBirthD() + "(Integer), "
                    + userData.getSex() + "(String), "
                    + userData.getMemo() + "(String) ");
            stmt.executeUpdate();
            commitDbConnection(conn);
        }catch (SQLException e){
            rollbackDbConnection(conn);
            e.printStackTrace();
        }finally {
            closeStatement(stmt);
            closeDbConnection(conn);
        }
    }

    /**
     * 指定したユーザーデータテーブル(user_data)のデータを更新する
     * @param userData ユーザーデータテーブル(user_data)の更新データ
     */
    public void update(UserData userData){
        Connection conn = getDbConnection();
        PreparedStatement stmt = null;
        try{
            stmt = conn.prepareStatement(updateSql);
            stmt.setString(1, userData.getName());
            stmt.setInt(2, userData.getBirthY());
            stmt.setInt(3, userData.getBirthM());
            stmt.setInt(4, userData.getBirthD());
            stmt.setString(5, userData.getSex());
            stmt.setString(6, userData.getMemo());
            stmt.setLong(7, userData.getId());
            logger.info(updateSql);
            logger.info("Parameters: " + userData.getName() + "(String), "
                    + userData.getBirthY() + "(Integer), "
                    + userData.getBirthM() + "(Integer), "
                    + userData.getBirthD() + "(Integer), "
                    + userData.getSex() + "(String), "
                    + userData.getMemo() + "(String), "
                    + userData.getId() + "(Long)");
            stmt.executeUpdate();
            commitDbConnection(conn);
        }catch (SQLException e){
            rollbackDbConnection(conn);
            e.printStackTrace();
        }finally {
            closeStatement(stmt);
            closeDbConnection(conn);
        }
    }

    /**
     * ユーザーデータテーブル(user_data)の最大値IDを取得する
     * @return ユーザーデータテーブル(user_data)の最大値ID
     */
    public long findMaxId(){
        long maxId = 0;
        Connection conn = getDbConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            stmt = conn.prepareStatement(findMaxIdSql);
            logger.info(findMaxIdSql);
            rs = stmt.executeQuery();
            while(rs.next()){
                maxId = rs.getLong("maxId");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeResultSet(rs);
            closeStatement(stmt);
            closeDbConnection(conn);
        }
        return maxId;
    }

    /**
     * データベースコネクションを取得する
     * @return データベースコネクション
     */
    private Connection getDbConnection(){
        Connection conn = null;
        try{
            // DemoDbConfig.javaで登録したjndiNameに紐付くデータベース接続情報を取得
            InitialContext context = new InitialContext();
            DataSource dataSource = (DataSource)context.lookup("java:comp/env/" + jndiName);
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * ResultSetオブジェクトを閉じる
     * @param rs Statementオブジェクト
     */
    private void closeResultSet(ResultSet rs){
        try{
            if(rs != null){
                rs.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * データベースコネクションをコミットする
     * @param conn データベースコネクション
     */
    private void commitDbConnection(Connection conn){
        try{
            if(conn != null){
                conn.commit();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * データベースコネクションをロールバックする
     * @param conn データベースコネクション
     */
    private void rollbackDbConnection(Connection conn){
        try{
            if(conn != null){
                conn.rollback();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Statementオブジェクトを閉じる
     * @param stmt Statementオブジェクト
     */
    private void closeStatement(Statement stmt){
        try{
            if(stmt != null){
                stmt.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * データベースコネクションを閉じる
     * @param conn データベースコネクション
     */
    private void closeDbConnection(Connection conn){
        try{
            if(conn != null){
                conn.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
