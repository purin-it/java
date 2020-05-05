package com.example.demo;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DownloadHistoryMapper {

    @Select("select DOWNLOAD_SEQUENCE.nextval as nextval from dual")
    long gen_history_sequence();

    /**
     * 指定したダウンロード履歴テーブル(download_history)のデータを追加する
     * @param downloadHistory ダウンロード履歴(download_history)
     */
    @Insert("INSERT INTO download_history ( history_id, file_data_id, download_date ) "
            + " VALUES ( #{historyId}, #{fileDataId}, #{downloadDate} )")
    void insert(DownloadHistory downloadHistory);
}
