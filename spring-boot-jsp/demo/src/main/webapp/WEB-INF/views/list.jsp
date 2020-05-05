<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>index page</title>
</head>
<body>
    ユーザーデータテーブル(user_data)の全データ<br/><br/>

    <table border="1" cellpadding="5">
        <tr>
            <th>ID</th>
            <th>名前</th>
            <th>生年月日</th>
            <th>性別</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach var="obj" items="${demoFormList}">
            <tr>
                <td><c:out value="${obj.id}" /></td>
                <td><c:out value="${obj.name}" /></td>
                <td><c:out value="${obj.birthYear}年 ${obj.birthMonth}月 ${obj.birthDay}日" /></td>
                <td><c:out value="${obj.sex_value}" /></td>
                <td><a href="/update?id=${obj.id}">更新</a></td>
                <td><a href="/delete_confirm?id=${obj.id}">削除</a></td>
            </tr>
        </c:forEach>
    </table>
    <br/><br/>
    <table border="0">
        <tr>
            <td width="70">
                <c:if test="${currentPageNum != 1}" >
                    <a href="/firstPage">先頭へ</a>
                </c:if>
            </td>
            <td width="50">
                <c:if test="${currentPageNum != 1}" >
                    <a href="/backPage">前へ</a>
                </c:if>
            </td>
            <td width="50">
                <c:out value="${currentPageNum}" />&nbsp;/&nbsp;<c:out value="${allPageNum}" />
            </td>
            <td width="50">
                <c:if test="${currentPageNum != allPageNum}" >
                    <a href="/nextPage">次へ</a>
                </c:if>
            </td>
            <td width="70">
                <c:if test="${currentPageNum != allPageNum}" >
                    <a href="/lastPage">最後へ</a>
                </c:if>
            </td>
        </tr>
    </table>
    <br/><br/>
    <form:form method="post" action="/add" modelAttribute="demoForm">
        <input type="submit" name="next" value="データ追加" /><br/><br/>
        <input type="submit" name="back" value="戻る" />
    </form:form>
</body>
</html>
