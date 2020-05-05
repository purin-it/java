window.addEventListener("DOMContentLoaded", function(){
    // 画面がロードされたことを確認
    alert('画面がロードされました');
});

function getUserData(){
    // Ajax検索ボタンが押下されたことを確認
    alert('Ajax検索ボタンが押下されました')
    // jQueryのAjaxにてDemoControllerクラスのsearchメソッドを呼び出す
    $.ajax({
        url: "/search",
        dataType: "text",
        type: "GET"
        // Ajaxが正常終了した場合
        }).done(function(data, textStatus, jqXHR) {
            // 該当するデータが無かった場合
            if(!data){
                alert("該当するデータはありませんでした");
                return;
            }
            // 画面のtableタグの全てのtrタグを削除
            $('#userDataTbl').find("tr:gt(0)").remove();
            // 該当するデータがあった場合は、取得したUserDataオブジェクトのリストを
            // 画面のtableタグに表示
            // その際、名前・性別・メモはデコードする
            const userDataList = JSON.parse(data);
            let i = 0;
            for(i = 0; i < userDataList.length; i++){
                let trTag = $("<tr />");
                trTag.append($("<td></td>").text(decodeURI(userDataList[i].name)));
                trTag.append($("<td></td>").text(userDataList[i].birthY + '年'
                    + userDataList[i].birthM + '月' + userDataList[i].birthD + '日'));
                trTag.append($("<td></td>").text(decodeURI(userDataList[i].sex)));
                trTag.append($("<td></td>").text(decodeURI(userDataList[i].memo)));
                $('#userDataTbl').append(trTag);
            }
            // Ajax検索ボタンを非活性化
            $('#ajaxSearchBtn').prop("disabled", true);
        // Ajaxが異常終了した場合
        }).fail(function(jqXHR, textStatus, errorThrown ) {
            alert("エラーが発生し、データが取得できませんでした");
    });
}

function reload(){
    // 画面再表示ボタンが押下されたことを確認
    alert('画面再表示ボタンが押下されました');
    // 処理をサブミットし、初期表示画面を表示
    $('#reloadForm').submit();
    // 画面再表示ボタンを非活性化
    $('#reloadBtn').prop("disabled", true);
}