'use strict';

$(document).ready(function(){
    // ボタン１～５が押下された場合のサブミット処理を定義
    $('#btn1').on('click', function(){
        $('#form1').attr('action', '/method2');
        $('#form1').submit();
    });
    $('#btn2').on('click', function(){
        $('#form2').attr('action', '/method2');
        $('#form2').submit();
    });
    $('#btn3').on('click', function(){
        $('#form3').attr('action', '/method2');
        $('#form3').submit();
    });
    $('#btn4').on('click', function(){
        $('#form4').attr('action', '/method2');
        $('#form4').submit();
    });
    $('#btn5').on('click', function(){
        $('#form5').attr('action', '/method2');
        $('#form5').submit();
    });
});

// ボタンが押下されたタイミングでサブミットする
function testClick(btn){
    btn.form.submit();
}
