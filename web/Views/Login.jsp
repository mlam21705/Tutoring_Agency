<%-- 
    Document   : Login
    Created on : Oct 24, 2026, 10:11:07 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
       
        <h1>Login form</h1>
        <% if (request.getAttribute("error") != null) { %>
        <p style="color:red">
            <%= request.getAttribute("error") %>
        </p>
    <% } %>
        <form action="${pageContext.request.contextPath}/Login" method ="post">
            Email:<input type ="text" name="email"/><br/>
            Password:<input type ="password" name="password"/><br/>
            <input type="submit" value="Login"/>
        </form>
    </body>
    </body>
</html>
