'use strict';

function getUserData(){
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
        // Ajaxが異常終了した場合
        }).fail(function(jqXHR, textStatus, errorThrown ) {
            alert("エラーが発生し、データが取得できませんでした");
    });
}