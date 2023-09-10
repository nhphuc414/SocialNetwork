<%-- 
    Document   : side
    Created on : Sep 4, 2023, 9:47:58 PM
    Author     : ad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="bg-dark" id="sidebar-wrapper">
    <div class="sidebar-heading text-center py-4 primary-text fs-2 fw-bold text-uppercase border-bottom">ADMIN</div>
    <div class="list-group list-group-flush my-3">
        <a href="<c:url value="/admin/" />" class="list-group-item list-group-item-action bg-transparent second-text active">Dashboard</a>
        <a href="<c:url value="/admin/users" />" class="list-group-item list-group-item-action bg-transparent second-text fw-bold">User</a>
        <a href="<c:url value="/admin/expire" />" class="list-group-item list-group-item-action bg-transparent second-text fw-bold">Reset Expire</a>
        <a href="<c:url value="/admin/request" />" class="list-group-item list-group-item-action bg-transparent second-text fw-bold">Request</a>
        <a href="<c:url value="/admin/notification" />" class="list-group-item list-group-item-action bg-transparent second-text fw-bold">Notification</a>
        <a href="<c:url value="/admin/stats" />" class="list-group-item list-group-item-action bg-transparent second-text fw-bold">Statistic</a>
    </div>
</div>

