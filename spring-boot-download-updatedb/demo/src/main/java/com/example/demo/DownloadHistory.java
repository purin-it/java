package com.example.demo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * ダウンロード履歴テーブル(download_history)アクセス用エンティティ
 */
@Data
public class DownloadHistory implements Serializable {

    /** シリアルバージョンID */
    private static final long serialVersionUID = 1L;

    /** ダウンロード履歴ID */
    private long historyId;

    /** ファイルデータID */
    private long fileDataId;

    /** ダウンロード日付 */
    private Timestamp downloadDate;

}
