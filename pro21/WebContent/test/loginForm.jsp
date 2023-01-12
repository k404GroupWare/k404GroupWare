<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<%
   request.setCharacterEncoding("UTF-8");
%>     
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
<title>로그인창</title>
</head>

<body>
<%-- ${contextPath} -> EL 표기법 해당 변수를 가져와 사용하는 방식. 
위에 설정에 보면 JSTL 이라는 기법으로 해당 변수를 등록 해놓았음.  --%>
<form name="frmLogin" method="post"  action="${contextPath}/test/login.do">
   <table border="1"  width="80%" align="center" >
      <tr align="center">
         <td>아이디</td>
         <td>비밀번호</td>
         <td>이름</td>
         <td>점심메뉴</td>
         <td>가격</td>
      </tr>
      <tr align="center">
         <td>
	    <input type="text" name="userID" value="" size="20">
	 </td>
         <td>
	    <input type="password" name="passwd" value="" size="20">
	 </td>
	    <td>
	    <input type="text" name="name" value="" size="20">
	 </td>
	    <td>
	    <input type="text" name="lunch" value="" size="20">
	 </td>
	    <td>
	    <input type="text" name="price" value="" size="20">
	 </td>
      </tr>
      <tr align="center">
         <td colspan="2">
            <input type="submit" value="로그인" > 
            <input type="reset"  value="다시입력" > 
         </td>
      </tr>
   </table>
</form>
</body>
</html>