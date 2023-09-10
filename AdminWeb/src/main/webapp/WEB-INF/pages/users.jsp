<%-- 
    Document   : user
    Created on : Sep 7, 2023, 6:49:06 PM
    Author     : ad
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="container">
    <a href="<c:url value="/add" />" class="btn btn-success mb-3">Add New</a>
    <c:if test="${counter > 1}">
        <ul class="pagination mt-1">
            <li class="page-item"><a class="page-link" href="<c:url value="/" />">All</a></li>
                <c:forEach begin="1" end="${counter}" var="i">
                    <c:url value="/users" var="pageUrl">
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
                <th scope="col">Display Name</th>
                <th scope="col">Username</th>
                <th scope="col">Email</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${users}" var="u">
                <tr>
                    <td>${u.id}</td>
                    <td>${u.displayName}</td>
                    <td>${u.username}</td>
                    <td>${u.email}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
