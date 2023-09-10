<%-- 
    Document   : request
    Created on : Sep 10, 2023, 1:32:54 AM
    Author     : ad
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="container">
    <c:if test="${counter > 1}">
        <ul class="pagination mt-1">
            <li class="page-item"><a class="page-link" href="<c:url value="/" />">All</a></li>
            <c:forEach begin="1" end="${counter}" var="i">
                <c:url value="/request" var="pageUrl">
                    <c:param name="page" value="${i}"></c:param>
                </c:url>
                <li class="page-item"><a class="page-link" href="${pageUrl}">${i}</a></li>
            </c:forEach>
        </ul>
    </c:if>
    <table class="table table-hover text-center">
        <thead class="table-dark">
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Identity</th>
                <th scope="col">Display Name</th>
                <th scope="col">Username</th>
                <th scope="col">Email</th>
                <th scope="col">Action</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="u">
            <tr>
                <td>${u.id}</td>
                <td>${u.identity}</td>
                <td>${u.displayName}</td>
                <td>${u.username}</td>
                <td>${u.email}</td>
                <td>
                    <c:url value="/request/${u.id}" var="api" />
                    <button class="btn btn-success" onclick="accept('${api}')">Accept</button>
                    <button class="btn btn-danger" onclick="denied('${api}')">Denied</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<script>
    function denied(path) {
    if (confirm("DENIED?") === true) {
        fetch(path, {
            method: "delete"
        }).then(res => {
            if (res.status === 204)
                location.reload();
            else
                alert("Something wrong!!!");
        });
    }
}
function accept(path) {
    if (confirm("ACCEPT?") === true) {
        fetch(path, {
            method: "post"
        }).then(res => {
            if (res.status === 204)
                location.reload();
            else
                alert("Something wrong!!!");
        });
    }
}
</script>

