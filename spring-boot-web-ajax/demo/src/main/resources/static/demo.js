'use strict';

function getUserData(){
    // index.htmlの検索IDの値を取得
    const id = document.getElementById('searchId').value;

    // AjaxにてDemoControllerクラスのsearchメソッドを呼び出す
    let request = new XMLHttpRequest();
    request.open("get", "/search?id=" + id, true);
    request.send(null);
    request.onload = function (event) {
       // Ajaxが正常終了した場合
       if (request.readyState === 4 && request.status === 200) {
          // 該当するデータが無かった場合
          if(!request.responseText){
              alert("該当するデータはありませんでした");
              return;
          }
          // 該当するデータがあった場合は、取得したUserDataオブジェクトの内容を画面に表示
          // その際、名前・性別・メモはデコードする
          const userData = JSON.parse(request.responseText);
          document.getElementById('name').textContent = decodeURI(userData.name);
          document.getElementById('birthDay').textContent
            = userData.birthY + '年' + userData.birthM + '月' + userData.birthD + '日';
          document.getElementById('sex').textContent = decodeURI(userData.sex);
          document.getElementById('memo').textContent = decodeURI(userData.memo);
       // Ajaxが異常終了した場合
       }else{
          alert("エラーが発生し、データが取得できませんでした");
       }
    };
    // Ajaxが異常終了した場合
    request.onerror = function (event) {
       alert("エラーが発生し、データが取得できませんでした");
    }
}