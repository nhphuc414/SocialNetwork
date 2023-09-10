<%-- 
    Document   : header
    Created on : Jul 25, 2023, 10:31:51 PM
    Author     : ad
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav class="navbar navbar-expand-lg bg-dark py-4 px-4" data-bs-theme="dark">
    <div class="d-flex align-items-center primary-text">
        <i class="fas fa-align-left primary-text fs-4 me-3" ></i>
        <i class="bi bi-distribute-vertical fs-4 me-3"id="menu-toggle"></i>
        <h2>Dashboard</h2>
    </div>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
            data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
            aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <c:choose>
        <c:when test="${pageContext.request.userPrincipal.name != null}">
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle " href="#" role="button" id="navbarDropdown" 
                           data-bs-toggle="dropdown" aria-expanded="false">
                            <span class="primary-text">${pageContext.request.userPrincipal.name}</span>
                            <img class="rounded-circle img-thumbnail img-fluid" style="height: 40px;width: 40px;"alt="avatar" src="https://res.cloudinary.com/dm5nn54wh/image/upload/v1669658311/cld-sample-5.jpg"/>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                            <li><a class="dropdown-item" href="#">Profile</a></li>
                            <li><a class="dropdown-item" href="#">Settings</a></li>
                            <li><a class="dropdown-item" href="<c:url value="/logout" />">Logout</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </c:when>
    </c:choose>
</nav>
<script>
    var el = document.getElementById("wrapper");
    var toggleButton = document.getElementById("menu-toggle");

    toggleButton.onclick = function () {
        el.classList.toggle("toggled");
    };
</script>