'use strict';

// 「パスワードを表示する」チェックボックスが選択された場合の処理
function changePwType(pwCheck){
    const pwd = document.getElementById('password');
    if(pwCheck){
        // パスワードのテキストボックスのタイプをテキストにする
        pwd.setAttribute('type', 'text');
    }else{
        // パスワードのテキストボックスのタイプをパスワードにする
        pwd.setAttribute('type', 'password');
    }
}