<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/static/demo.css" />
<title>index page</title>
</head>
　　　<p>検索条件を指定し、「検索」ボタンを押下してください。</p><br/>
    <form:form method="post" action="/search" modelAttribute="searchForm">
        <form:errors path="fromBirthYear" cssClass="errorMessage" />
        <form:errors path="toBirthYear" cssClass="errorMessage" />
        <table border="1" cellpadding="5">
           <tr>
              <th>名前</th>
              <td><form:input path="searchName" /></td>
           </tr>
           <tr>
              <th>生年月日</th>
              <td>
                  <form:input path="fromBirthYear" size="4" maxlength="4"
                      cssErrorClass="fieldError" />年
                  <form:select path="fromBirthMonth" cssErrorClass="fieldError">
                     　<form:option value="" label=""/>
                     　<form:options items="${searchForm.getMonthItems()}" />
                  </form:select>月
                  <form:select path="fromBirthDay" cssErrorClass="fieldError">
                  　　　<form:option value="" label=""/>
                      <form:options items="${searchForm.getDayItems()}" />
                  </form:select>日～
                  <form:input path="toBirthYear" size="4" maxlength="4"
                      cssErrorClass="fieldError" />年
                  <form:select path="toBirthMonth" cssErrorClass="fieldError">
                      <form:option value="" label=""/>
                      <form:options items="${searchForm.getMonthItems()}" />
                  </form:select>月
                  <form:select path="toBirthDay" cssErrorClass="fieldError">
                      <form:option value="" label=""/>
                      <form:options items="${searchForm.getDayItems()}" />
                  </form:select>日
              </td>
           </tr>
           <tr>
              <th>性別</th>
              <td>
                  <form:select path="searchSex">
                      <form:option value="" label=""/>
                      <form:options items="${searchForm.getSexItems()}" />
                  </form:select>
              </td>
           </tr>
        </table>
        <br/><br/>
        <input type="submit" value="検索" />
        <input type="button" value="閉じる" onclick="window.close();" />
    </form:form>
</body>
</html>
