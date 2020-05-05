function getUserData(){
    // index.htmlの検索IDの値を取得
    const id = $('#searchId').val();

    // jQueryのAjaxにてDemoControllerクラスのsearchメソッドを呼び出す
    $.ajax({
        url: "/search?id=" + id,
        dataType: "text",
        type: "GET"
        // Ajaxが正常終了した場合
        }).done(function(data, textStatus, jqXHR) {
            // 該当するデータが無かった場合
            if(!data){
                alert("該当するデータはありませんでした");
                return;
            }
            // 該当するデータがあった場合は、取得したUserDataオブジェクトの内容を画面に表示
            // その際、名前・性別・メモはデコードする
            const userData = JSON.parse(data);
            $('#name').text(decodeURI(userData.name));
            $('#birthDay').text(userData.birthY + '年'
                + userData.birthM + '月' + userData.birthD + '日');
            $('#sex').text(decodeURI(userData.sex));
            $('#memo').text(decodeURI(userData.memo));
        // Ajaxが異常終了した場合
        }).fail(function(jqXHR, textStatus, errorThrown ) {
            alert("エラーが発生し、データが取得できませんでした");
    });
}