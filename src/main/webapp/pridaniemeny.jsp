<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Currency" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
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
                <a class="nav-link active" href="${pageContext.request.contextPath}/zmenaren">Zmenaren</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/zoznam">Zoznam</a>
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
            <h2>Pridanie Meny</h2>
            <form class="row g-3" method="post">
                <div class="input-group col-md-6">
                    <select id="menaSelectorForm" name="menaSelectorForm" class="form-select" aria-label="Mena Pridanie From">
                        <option value="" selected>Pridanie Meny From</option>
                        <%
                            ServletContext sc = request.getServletContext();
                            ArrayList<Currency> std = (ArrayList<Currency>) sc.getAttribute("zoznamMienList");
                            for (Currency s : std) {
                        %>
                        <option value="<%=s.getCurrencyCode()%>"><%=s.getCurrencyCode()%></option>
                        <%}%>
                    </select>
                </div>
                <div class="form-group">
                    <input id="iconValueForm" type="text" name="iconValueForm" class="form-control" aria-label="IconFormValue">
                </div>
                <div class="form-group">
                    <input id="descriptionValueForm" type="text" name="descriptionValueForm" class="form-control" aria-label="descriptionValueForm">
                </div>
                <div class="form-check">
                    <input class="form-check-input" id="activeValueForm" type="checkbox" name="activeValueForm">
                    <label class="form-check-label" for="activeValueForm">
                        Is Currency Active ?
                    </label>
                </div>

                <button class="btn btn-primary" type="submit">Add Mena</button>
            </form>
            <c:if test="${error != null}">
                <div class="col-12 alert alert-danger" role="alert" style="margin-top: 10px">
                        ${error}
                </div>
            </c:if>
            <c:if test="${pridanieMenaResponse != null}">
                <div class="alert alert-success" role="alert" style="margin-top: 10px">
                        ${pridanieMenaResponse}
                </div>
            </c:if>
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