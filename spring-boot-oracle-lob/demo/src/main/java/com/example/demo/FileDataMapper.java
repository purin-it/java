package com.example.demo;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FileDataMapper {

    /**
     * 指定したIDをもつファイルデータテーブル(file_data)のデータを取得する
     * @param id ID
     * @return ファイルデータテーブル(file_data)の指定したIDのデータ
     */
    @Select("SELECT id, file_path as filePath, file_obj as fileObj "
            + " FROM file_data WHERE id = #{id}")
    FileData findById(Long id);

    /**
     * 指定したファイルデータテーブル(file_data)のデータを追加する
     * @param fileData　ファイルデータテーブル(file_data)
     */
    @Insert("INSERT INTO file_data ( id, file_path, file_obj ) "
            + " VALUES ( #{id}, #{filePath}, #{fileObj} )")
    void insert(FileData fileData);

    /**
     * ファイルデータテーブル(file_data)のデータを全件削除する
     */
    @Delete("DELETE FROM file_data")
    void deleteAll();
}