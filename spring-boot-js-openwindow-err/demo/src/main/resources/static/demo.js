//フォーム名を指定して新しいウィンドウを開く
function openWin(formName){
    let form = document.forms[formName];
    if(!form){
        alert('指定したフォームが取得できませんでした');
        return;
    }
    let win = window.open('about:blank', formName);
    form.target = formName;
    form.submit();
    win.focus();
}
//子画面のエラーメッセージを親画面に転記
function printOpenErrMsg(){
    let errList = document.getElementById('errList');
    if(errList){
        let errMessages = errList.getElementsByTagName('li');
        //子画面にエラーメッセージがある場合
        if(errMessages && errMessages.length > 0){
            //親画面のエラーメッセージリストを取得し初期化後、メッセージを追加
            let errListP = window.opener.document.getElementById('errList');
            if(errListP){
                errListP.innerHTML = "";
                let errListUlP = document.createElement("ul");
                errListP.appendChild(errListUlP)
                for(let i = 0; i < errMessages.length; i++){
                    let msg = errMessages[i].textContent;
                    let errListLiP = document.createElement("li");
                    errListLiP.innerHTML = msg;
                    errListLiP.classList.add('errorMessage');
                    errListUlP.appendChild(errListLiP);
                }
            }
            //子画面を閉じる
            window.close();
        }
    }
}