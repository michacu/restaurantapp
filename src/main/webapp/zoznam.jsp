<%@ page import="java.util.ArrayList" %>
<%@ page import="sk.michacu.zmenaren.model.MenaObject" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="sk">
<head>
    <title>Currency Exchange App</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <div class="collapse navbar-collapse justify-content-center" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <a class="nav-link" href="${pageContext.request.contextPath}/">Home</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/zmenaren">Zmenaren</a>
                <a class="nav-link active" href="${pageContext.request.contextPath}/zoznam">Zoznam</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/pridaniemeny">Pridanie Meny</a>
            </div>
        </div>
    </div>
</nav>

<div class="container-fluid text-center">
    <div class="row content">
        <div class="col-sm-2 sidenav">
        </div>
        <div class="col-sm-8 text-left">
            <h3>Zoznam podporovanych mien:</h3>
            <table class="table table-striped table-hover">
                <%
                    ServletContext sc = request.getServletContext();
                    ArrayList<MenaObject> std = (ArrayList<MenaObject>) sc.getAttribute("currencyList");
                    for (MenaObject s : std) {
                        if (s.isActive()) {
                %>
                <tr>
                    <td><%=s.getCurrName()%></td>
                    <td><%=s.getIcon()%></td>
                </tr>
                <%}%>
                <%}%>
            </table>
        </div>
        <div class="col-sm-2 sidenav">
        </div>
    </div>
</div>

<footer class="container-fluid text-center fixed-bottom">
    <p>2021 Michacu</p>
</footer>

</body>
</html>
