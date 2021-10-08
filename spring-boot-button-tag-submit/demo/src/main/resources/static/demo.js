'use strict';

$(document).ready(function(){
    // ボタン３～４が押下された場合のサブミット処理を定義
    $('#btn3').on('click', function(){
        $('#form3').attr('action', '/method2');
        $('#form3').submit();
    });
    $('#btn4').on('click', function(){
        $('#form4').attr('action', '/method2');
        $('#form4').submit();
    });
});

// ボタンが押下されたタイミングでサブミットする
function testClick(btn){
    btn.form.submit();
}
