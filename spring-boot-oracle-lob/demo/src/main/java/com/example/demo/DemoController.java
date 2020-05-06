package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.transaction.annotation.Transactional;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

@Controller
public class DemoController {

    @Autowired
    private FileDataMapper fileDataMapper;

    @RequestMapping("/")
    @Transactional(readOnly = false)
    public String index(){

        //全件削除する
        fileDataMapper.deleteAll();

        //1件追加する
        insertFileData("C:\\tmp\\テスト.txt");

        //追加したデータを確認する
        getFileData();

        //サンプルページへ移動する
        return "sample";
    }

    /**
     * 指定したファイルパスのデータを追加する
     * @param filePath
     */
    private void insertFileData(String filePath){
        FileData fileData = new FileData();
        fileData.setId(1);
        fileData.setFilePath(filePath);
        try{
            fileData.setFileObj(new FileInputStream(filePath));
        }catch(Exception e){
            System.err.println(e);
        }
        //1件追加する
        fileDataMapper.insert(fileData);
    }

    /**
     * ファイルデータ(file_data)テーブルのデータを確認する
     */
    private void getFileData(){
        //指定したIDのデータを取得する
        FileData fileData = fileDataMapper.findById(Long.valueOf(1));
        if(fileData != null){
            System.out.println("idの値 : " + fileData.getId());
            System.out.println("file_pathの値 : " + fileData.getFilePath());
            System.out.println("file_objの値 : "
                    + getFileObjData(fileData.getFileObj()));
        }else{
            System.out.println("fileDataはnull");
        }
    }

    /**
     * 入力ストリームを文字列に変換し返す
     * @param inputStream 入力ストリーム
     * @return ファイルオブジェクトの文字列
     */
    private String getFileObjData(InputStream inputStream){
        StringBuilder builder = new StringBuilder();
        if(inputStream != null){
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(inputStream))) {
                String line;
                while((line = br.readLine()) != null){
                    builder.append(line);
                }
            }catch(Exception e){
                System.err.println(e);
            }
        }
        return builder.toString();
    }
}