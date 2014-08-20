<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Form Processor</title>
</head>
<body>

<jsp:useBean id="queryData" class="beans.QueryData" scope="session"/> 
<!-- Set all of these false by default, will be overwritten later if they're suppose to be true -->
<jsp:setProperty name="queryData" property="bayLakes" value="false" />
<jsp:setProperty name="queryData" property="daneCounty" value="false" />
<jsp:setProperty name="queryData" property="eauClaire" value="false" />
<jsp:setProperty name="queryData" property="ecwrpc" value="false" />
<jsp:setProperty name="queryData" property="madison" value="false" />
<jsp:setProperty name="queryData" property="ncwrpc" value="false" />
<jsp:setProperty name="queryData" property="sewrpc" value="false" />
<jsp:setProperty name="queryData" property="*"/>
<%
	//check security of inputs, and then redirect back
	//index to run query and display results
	response.sendRedirect( "index.jsp" );
%>
</body>
</html>