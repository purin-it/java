<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/static/demo.css" />
<title>入力画面</title>
</head>
<body>
    <p>下記必要事項を記載の上、「確認」ボタンを押下してください。</p><br/>
    <form:form method="post" action="/confirm" modelAttribute="demoForm">
        <table border="0">
           <tr>
               <td align="left" valign="top">名前：</td>
               <td>
                   <form:input path="name" cssErrorClass="fieldError" />
                   <form:errors path="name" cssClass="errorMessage" />
               </td>
           </tr>
           <tr>
               <td align="left" valign="top">生年月日：</td>
               <td>
                   <form:input path="birthYear" size="4" maxlength="4"
                       cssErrorClass="fieldError" />年
                   <form:select path="birthMonth" cssErrorClass="fieldError">
                       <form:option value="" label=""/>
                       <form:options items="${demoForm.getMonthItems()}" />
                   </form:select>月
                   <form:select path="birthDay" cssErrorClass="fieldError">
                       <form:option value="" label=""/>
                       <form:options items="${demoForm.getDayItems()}" />
                   </form:select>日
                   <form:errors path="birthYear" cssClass="errorMessage" />
                   <form:errors path="birthMonth" cssClass="errorMessage" />
                   <form:errors path="birthDay" cssClass="errorMessage" />
               </td>
           </tr>
           <tr>
               <td align="left" valign="top">性別：</td>
               <td>
                   <c:forEach var="item" items="${demoForm.getSexItems()}">
                       <form:radiobutton path="sex" value="${item.key}"
                           cssErrorClass="fieldError" />
                       ${item.value} &nbsp;&nbsp;
                   </c:forEach>
                   <form:errors path="sex" cssClass="errorMessage" />
               </td>
           </tr>
           <tr>
               <td align="left" valign="top">メモ：</td>
               <td>
                   <form:textarea path="memo" rows="6" cols="40" />
               </td>
           </tr>
           <tr>
               <td align="left" valign="top">入力確認：</td>
               <td>
                   <form:checkbox path="checked" value="確認済"
                       cssErrorClass="fieldError" />
                   <form:errors path="checked" cssClass="errorMessage" />
               </td>
           </tr>
        </table>
        <br/><br/>
        <input type="submit" name="next" value="確認" />
        <input type="submit" name="back" value="戻る" />
    </form:form>
</body>
</html>
