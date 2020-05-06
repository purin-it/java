package com.example.demo;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileDataMapper {

    /**
     * ファイルデータテーブル(file_data)から全件取得する
     * @return ファイルデータテーブル(file_data)のデータリスト
     */
    @Select("SELECT id, file_path as filePath, file_obj as fileObj "
            + " FROM file_data ORDER BY id asc")
    List<FileData> findAll();

    /**
     * 指定したIDをもつファイルデータテーブル(file_data)のデータを取得する
     * @param id ID
     * @return ファイルデータテーブル(file_data)の指定したIDのデータ
     */
    @Select("SELECT id, file_path as filePath, file_obj as fileObj "
            + " FROM file_data WHERE id = #{id}")
    FileData findById(Long id);

    /**
     * ファイルデータテーブル(file_data)の最大値IDを取得する
     * @return ファイルデータテーブル(file_data)の最大値ID
     */
    @Select("SELECT NVL(max(id), 0) as maxId FROM file_data")
    long getMaxId();

    /**
     * 指定したファイルデータテーブル(file_data)のデータを追加する
     * @param fileData　ファイルデータテーブル(file_data)
     */
    @Insert("INSERT INTO file_data ( id, file_path, file_obj ) "
            + " VALUES ( #{id}, #{filePath}, #{fileObj} )")
    void insert(FileData fileData);
}