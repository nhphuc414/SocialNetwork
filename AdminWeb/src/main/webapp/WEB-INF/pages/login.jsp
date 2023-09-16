<%-- 
    Document   : login
    Created on : Sep 7, 2023, 6:49:47 PM
    Author     : ad
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:url value="/login" var="action" />
<c:choose>
    <c:when test="${pageContext.request.userPrincipal.name == null}">
        <div class="container mt-5 pt-5">
            <div class="row">
                <div class="col-12 col-sm-7 col-md-6 m-auto">
                    <div class="card border-0 shadow">
                        <div class="card-body">
                            <i class="bi bi-person-circle d-flex justify-content-center align-items-center"></i>
                            <form method="post" action="${action}">
                                <div class="form-floating mb-3 mt-3">
                                    <input type="text" class="form-control" id="username" placeholder="Nhập username..." name="username">
                                    <label for="username">Username</label>
                                </div>

                                <div class="form-floating mt-3 mb-3">
                                    <input type="password" class="form-control" id="pwd" placeholder="Nhập mật khẩu..." name="password">
                                    <label for="pwd">Password</label>
                                </div>

                                <div class="d-flex justify-content-center align-items-center">
                                    <input type="submit" value="Đăng nhập" class="btn btn-danger" />
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:when>
</c:choose>

