'use strict';

// フォーム名を指定して新しいウィンドウを開く
function openWin(parentForm){
    if(!parentForm){
        alert('指定したフォームが取得できませんでした');
        return;
    }
    const win = window.open('about:blank', parentForm.name);
    parentForm.target = parentForm.name;
    parentForm.submit();
    win.focus();
}

// HTML文書読み込み時の処理を指定
document.addEventListener('DOMContentLoaded', () => {

    // 「新規ウィンドウをオープン(GET)」ボタンのオブジェクトを取得する
    const openGetBtn = document.getElementById('openGetBtn');
    openGetBtn.addEventListener('click', () => {
        // 指定したボタンの親ノードのフォーム名を指定して新しいウィンドウを開く
        const parentForm = openGetBtn.parentNode;
        openWin(parentForm);
    });
    
    // 「新規ウィンドウをオープン(POST)」ボタンのオブジェクトを取得する
    const openPostBtn = document.getElementById('openPostBtn');
    openPostBtn.addEventListener('click', () => {
        // 指定したボタンの親ノードのフォーム名を指定して新しいウィンドウを開く
        const parentForm = openPostBtn.parentNode;
        openWin(parentForm);
    });

});
