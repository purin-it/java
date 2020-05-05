package com.example.demo;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import java.util.Collection;

@Mapper
public interface UserDataMapper {

    /**
     * ユーザーデータテーブル(user_data)を全件取得する
     * @return ユーザーデータテーブル(user_data)を全データ
     */
    @Select("SELECT u.id, u.name, u.birth_year as birthY, u.birth_month as birthM"
            + ", u.birth_day as birthD, u.sex as sex, m.sex_value as sex_value "
            + " FROM USER_DATA u, M_SEX m WHERE u.sex = m.sex_cd ORDER BY u.id ")
    Collection<UserData> findAll();

    /**
     * 指定したIDをもつユーザーデータテーブル(user_data)のデータを取得する
     * @param id ID
     * @return ユーザーデータテーブル(user_data)の指定したIDのデータ
     */
    @Select("SELECT id, name, birth_year as birthY, birth_month as birthM"
            + ", birth_day as birthD, sex FROM USER_DATA WHERE id = #{id}")
    UserData findById(Long id);

    /**
     * 指定したIDをもつユーザーデータテーブル(user_data)のデータを削除する
     * @param id ID
     */
    @Delete("DELETE FROM USER_DATA WHERE id = #{id}")
    void deleteById(Long id);

    /**
     * 指定したユーザーデータテーブル(user_data)のデータを追加する
     * @param userData ユーザーデータテーブル(user_data)の追加データ
     */
    @Insert("INSERT INTO USER_DATA ( id, name, birth_year, birth_month, birth_day, sex )"
            + " VALUES (#{id}, #{name}, #{birthY}, #{birthM}, #{birthD}, #{sex})")
    void create(UserData userData);

    /**
     * 指定したユーザーデータテーブル(user_data)のデータを更新する
     * @param userData ユーザーデータテーブル(user_data)の更新データ
     */
    @Update("UPDATE USER_DATA SET name = #{name}, birth_year = #{birthY}"
            + ", birth_month = #{birthM}, birth_day = #{birthD}"
            + ", sex = #{sex} WHERE id = #{id}")
    void update(UserData userData);

    /**
     * ユーザーデータテーブル(user_data)の最大値IDを取得する
     * @return ユーザーデータテーブル(user_data)の最大値ID
     */
    @Select("SELECT max(id) FROM USER_DATA")
    long findMaxId();
}
