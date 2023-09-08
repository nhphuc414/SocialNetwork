<%-- 
    Document   : user
    Created on : Sep 7, 2023, 6:49:06 PM
    Author     : ad
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script src="<c:url value="/js/main.js" />"></script>
<div class="container">
    <a href="<c:url value="/adduser" />" class="btn btn-dark mb-3">Add New</a>
    <c:if test="${counter > 1}">
        <ul class="pagination mt-1">
            <li class="page-item"><a class="page-link" href="<c:url value="/" />">All</a></li>
                <c:forEach begin="1" end="${counter}" var="i">
                    <c:url value="/" var="pageUrl">
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
            <c:forEach items="${user}" var="u">
                <tr>
                    <td>${u.id}></td>
                    <td>${u.displayName}</td>
                    <td>${u.username}</td>s
                    <td>${u.email}</td>
                    <td>
                        <c:url value="/api/user/${u.id}" var="apiDel" />
                        <a href="<c:url value="/user/${p.id}" />" class="btn btn-success">Cập nhật</a>
                        <button class="btn btn-danger" onclick="delPro('${apiDel}', ${u.id})">Xóa</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
