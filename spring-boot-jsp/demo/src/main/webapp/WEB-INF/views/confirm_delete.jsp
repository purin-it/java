<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title>削除確認画面</title>
</head>
<body>
    <p>下記内容を削除してよろしいでしょうか？問題なければ「送信」ボタンを押下してください。</p>
    <form:form method="post" action="/delete" modelAttribute="demoForm">
        <table border="0">
           <tr>
               <td align="left" valign="top">名前：</td>
               <td>
                   <c:out value="${demoForm.name}" />
               </td>
           </tr>
           <tr>
               <td align="left" valign="top">生年月日：</td>
               <td>
                   <c:out value="${demoForm.birthYear}" />年
                   <c:forEach var="obj" items="${demoForm.getMonthItems()}">
                       <c:if test="${obj.key == demoForm.birthMonth}">
                           <c:out value="${obj.value}" />月
                       </c:if>
                   </c:forEach>
                   <c:forEach var="obj" items="${demoForm.getDayItems()}">
                       <c:if test="${obj.key == demoForm.birthDay}">
                           <c:out value="${obj.value}" />日
                       </c:if>
                   </c:forEach>
               </td>
           </tr>
           <tr>
               <td align="left" valign="top">性別：</td>
               <td>
                   <c:forEach var="obj" items="${demoForm.getSexItems()}">
                       <c:if test="${obj.key == demoForm.sex}">
                           <c:out value="${obj.value}" />
                       </c:if>
                   </c:forEach>
               </td>
           </tr>
           <tr>
               <td align="left" valign="top">メモ：</td>
               <td>
                   <% pageContext.setAttribute("newLineChar", "\n"); %>
                   <c:forEach var="memoStr" varStatus="memoStat" items="${fn:split(demoForm.memo, newLineChar)}">
                       <c:out value="${memoStr}" />
                       <c:if test="${memoStat.last != true}">
                           <br/>
                       </c:if>
                   </c:forEach>
               </td>
           </tr>
        </table>
        <br/><br/>
        <input type="submit" name="next" value="確認" />
        <input type="submit" name="back" value="戻る" />
    </form:form>
</body>
</html>
