package com.example.demo;

import lombok.Data;
import java.io.InputStream;
import java.io.Serializable;

/**
 * ファイルデータテーブル(file_data)アクセス用エンティティ
 */
@Data
public class FileData implements Serializable{

    /** シリアルバージョンID */
    private static final long serialVersionUID = 1L;

    /** ID */
    private long id;

    /** ファイルパス */
    private String filePath;

    /** ファイルオブジェクト */
    private InputStream fileObj;

}