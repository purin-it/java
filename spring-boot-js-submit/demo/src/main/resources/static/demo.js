// 引数で指定されたパスでサブミットする
function formSubmit(path){
    var form = document.getElementsByTagName('form')[0];
    form.action=path;
    form.method="post";
    form.submit();
}