// 引数で指定されたパスでサブミットする
function formSubmit(path){
    if(!path){
        alert('パスを指定してください');
        return;
    }
    var form = document.getElementsByTagName('form')[0];
    if(!form){
        alert('フォームが取得できませんでした');
        return;
    }
    form.action=path;
    form.method="post";
    form.submit();
}