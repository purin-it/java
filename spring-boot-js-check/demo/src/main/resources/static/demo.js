'use strict';

// 引数で指定されたパスでサブミットする
function formSubmit(path){
    if(!path){
        alert('パスを指定してください');
        return;
    }
    let form = document.getElementsByTagName('form')[0];
    if(!form){
        alert('フォームが取得できませんでした');
        return;
    }
    form.action=path;
    form.method="post";
    form.submit();
}
// 入力画面の入力値のチェックを行った後で、
// 引数で指定されたパスでサブミットする
function checkInputAndSubmit(path){
    if(!checkInput()){
        return;
    }
    formSubmit(path);
}
// 入力画面の入力値のチェックを行う
function checkInput(){
    //名前の未入力チェック
    let nameElem = document.getElementById('name');
    if(nameElem.value === ''){
        alert('名前を入力してください。');
        nameElem.focus();
        return false;
    }
    //名前の全角入力チェック
    if(!nameElem.value.match(/^[^\x01-\x7E\xA1-\xDF]+$/)){
        alert('名前は全角で入力してください。')
        nameElem.focus();
        return false;
    }
    //生年月日の未入力チェック
    let birthYearElem = document.getElementById('birthYear');
    let birthMonthElem = document.getElementById('birthMonth');
    let birthDayElem = document.getElementById('birthDay');
    if(birthYearElem.value === ''
        && birthMonthElem.value === '' && birthDayElem.value === ''){
        alert('生年月日を入力してください。');
        birthYearElem.focus();
        return false;
    }
    //生年月日の妥当性チェック
    if(!isValidDate(birthYearElem.value, birthMonthElem.value, birthDayElem.value)){
        alert('生年月日が存在しない日付になっています。');
        birthYearElem.focus();
        return false;
    }
    //生年月日の未来日チェック
    if(isFutureDate(birthYearElem.value, birthMonthElem.value, birthDayElem.value)){
        alert('生年月日が未来の日付になっています。');
        birthYearElem.focus();
        return false;
    }
    //性別の未入力チェック
    let sexElem = document.getElementsByName('sex');
    let sexValue = '';
    for (let i = 0; i < sexElem.length; i++) {
    	if (sexElem[i].checked) {
    		sexValue = sexElem[i].value ;
    		break;
    	}
    }
    if(sexValue === ''){
        alert('性別を入力してください。');
        sexElem[0].focus();
        return false;
    }
    //確認チェックの未入力チェック
    let checkedElem = document.getElementsByName('checked');
    if(!checkedElem[0].checked){
        alert('確認チェックを入力してください。');
        checkedElem[0].focus();
        return false;
    }
    return true;
}
// 日付の妥当性チェックを行う
function isValidDate(year, month, day) {
    let thisDate = new Date(year, month-1, day);
    return (thisDate.getFullYear() == year
        && thisDate.getMonth() == month-1 && thisDate.getDate() == day);
}
// 日付の未来日チェックを行う
function isFutureDate(year, month, day){
    let thisDate = new Date(year, month-1, day);
    let nowDate = new Date();
    return (thisDate.getTime() > nowDate.getTime());
}