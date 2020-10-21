'use strict';

// HTML文書読み込み時の処理を指定
document.addEventListener('DOMContentLoaded', () => {

   // 「検索」ボタンのオブジェクトを取得する
   const searchBtn = document.getElementById('searchBtn');
   searchBtn.addEventListener('click', () => {

      // Ajax通信を定義する
      function ajaxResult(){
         return new Promise((resolve, reject) => {
            // index.htmlの検索IDの値を取得
            const id = document.getElementById('searchId').value;

            // AjaxにてDemoControllerクラスのsearchメソッドを呼び出す
            const request = new XMLHttpRequest();
            request.open("get", "/search?id=" + id, true);
            request.send(null);
            request.onload = function (event) {
               // Ajaxが正常終了した場合
               if (request.readyState === 4 && request.status === 200) {
                  // 該当するデータが無かった場合
                  if(!request.responseText){
                     reject({message:'該当するデータはありませんでした'});
                  }
                  // 該当するデータがあった場合は、取得したUserDataオブジェクトの内容を画面に表示
                  // その際、名前・性別・メモはデコードする
                  const userData = JSON.parse(request.responseText);
                  document.getElementById('name').textContent = decodeURI(userData.name);
                  document.getElementById('birthDay').textContent
                     = userData.birthY + '年' + userData.birthM + '月' + userData.birthD + '日';
                  document.getElementById('sex').textContent = decodeURI(userData.sex);
                  document.getElementById('memo').textContent = decodeURI(userData.memo);
                  resolve({message:'正常終了'});

               // Ajaxが異常終了した場合
               }else{
                  reject({message:'エラーが発生し、データが取得できませんでした'});
               }
            };
            // Ajaxが異常終了した場合
            request.onerror = function (event) {
               reject({message:'エラーが発生し、データが取得できませんでした'});
            }
         });      
      };

      // Ajax通信を呼び出す
      ajaxResult().then(
           ok => console.log(ok.message)
         , error => alert(error.message)
      );
   
   });
});
