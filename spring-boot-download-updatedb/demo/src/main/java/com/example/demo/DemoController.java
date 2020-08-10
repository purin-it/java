package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class DemoController {

    /**
     * ファイルデータテーブル(file_data)へアクセスするMapper
     */
    @Autowired
    private FileDataMapper fileDataMapper;

    /**
     * ダウンロード履歴テーブル(download_history)へアクセスするMapper
     */
    @Autowired
    private DownloadHistoryMapper downloadHistoryMapper;

    /**
     * ファイルデータ一覧表示処理
     * @param model Modelオブジェクト
     * @return 一覧画面
     */
    @RequestMapping("/")
    public String index(Model model){
        //ファイルデータテーブル(file_data)を全件取得
        List<FileData> list = fileDataMapper.findAll();
        model.addAttribute("fileDataList", list);

        //一覧画面へ移動
        return "list";
    }

    /**
     * ファイルデータ登録画面への遷移処理
     * @return ファイルデータ登録画面
     */
    @PostMapping("/to_add")
    public String to_add(){
        return "add";
    }

    /**
     * ファイルデータ登録処理
     * @param uploadFile アップロードファイル
     * @return ファイルデータ一覧表示処理
     */
    @PostMapping("/add")
    @Transactional(readOnly = false)
    public String add(@RequestParam("upload_file") MultipartFile uploadFile){
        //最大値IDを取得
        long maxId = fileDataMapper.getMaxId();

        //追加するデータを作成
        FileData fileData = new FileData();
        fileData.setId(maxId + 1);
        fileData.setFilePath(uploadFile.getOriginalFilename());
        try{
            fileData.setFileObj(uploadFile.getInputStream());
        }catch(Exception e){
            System.err.println(e);
        }
        //1件追加
        fileDataMapper.insert(fileData);

        //一覧画面へ遷移
        return "redirect:/to_index";
    }

    /**
     * 追加完了後に一覧画面に戻る
     * @param model Modelオブジェクト
     * @return 一覧画面
     */
    @GetMapping("/to_index")
    public String toIndex(Model model){
        return index(model);
    }

    /**
     * ファイルダウンロード処理
     * @param id ID
     * @param response HttpServletResponse
     * @return 画面遷移先
     */
    @Transactional(readOnly = false)
    @RequestMapping("/download")
    public String download(@RequestParam("id") String id, HttpServletResponse response){
        //ダウンロード対象のファイルデータを取得
        FileData data = fileDataMapper.findById(Long.parseLong(id));

        //ダウンロード対象のファイルデータがnullの場合はエラー画面に遷移
        if(data == null || data.getFileObj() == null){
            return "download_error";
        }

        //PDFの場合
        if(data.getFilePath().endsWith(".pdf")){
            //PDFプレビューの設定を実施
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline;");
        }else{
            //ファイルダウンロードの設定を実施
            response.setContentType("application/octet-stream");  //ファイルの種類は指定しない
            response.setHeader("Content-Disposition"
                    ,"attachment;filename=\"" + getFileName(data.getFilePath()) + "\"");
        }
        //その他の設定を実施
        response.setHeader("Cache-Control", "private");
        response.setHeader("Pragma", "");

        OutputStream out = null;
        InputStream in = null;
        try{
            //ダウンロード履歴への書き込み
            DownloadHistory downloadHistory = new DownloadHistory();
            downloadHistory.setHistoryId(downloadHistoryMapper.gen_history_sequence());
            downloadHistory.setFileDataId(data.getId());
            downloadHistory.setDownloadDate(Timestamp.valueOf(LocalDateTime.now()));
            downloadHistoryMapper.insert(downloadHistory);

            //ダウンロードファイルへ出力
            out = response.getOutputStream();
            in = data.getFileObj();
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = in.read(buff, 0, buff.length)) != -1) {
                out.write(buff, 0, len);
            }
            out.flush();
        }catch (Exception e){
            System.err.println(e);
        }finally {
            if(out != null){
                try{
                    out.close();
                }catch (IOException e){
                    System.err.println(e);
                }
            }
            if(in != null){
                try{
                    in.close();
                }catch (IOException e){
                    System.err.println(e);
                }
            }
        }

        //画面遷移先はnullを指定
        return null;
    }

    /**
     * ファイルパスからファイル名を取得する
     * @param filePath ファイルパス
     * @return ファイル名
     */
    private String getFileName(String filePath){
        String fileName = "";
        if(filePath != null && !"".equals(filePath)){
            try{
                //ファイル名をUTF-8でエンコードして指定
                fileName = URLEncoder.encode(new File(filePath).getName(), "UTF-8");
            }catch(Exception e){
                System.err.println(e);
                return "";
            }
        }
        return fileName;
    }

}
