<%-- 
    Document   : addnew
    Created on : Sep 8, 2023, 4:26:09 PM
    Author     : ad
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="container">
    <div class="text-center mb-4">
        <h3>Add New User</h3>
    </div>
    <div class="container d-flex justify-content-center">
        <c:url value="/add" var="action" />
        <form:form method="post" action="${action}" onsubmit="return validateForm();" style="width:50vw; min-width:300px;" modelAttribute="user">
            <div class="mb-3">
                <div class="mb-3">
                    <label class="form-label">Display name</label>
                    <form:input type="text" class="form-control" 
                                path="displayName" id="displayName" placeholder="Enter display name here" required="required"/>
                </div>
                <div class="col">
                    <label class="form-label">Username</label>
                    <form:input type="text" class="form-control" 
                                path="username" id="username" placeholder="Enter username here" required="required"/>
                </div>
            </div>
            <div class="mb-3">
                <label class="form-label">Email</label>
                <input type="email" class="form-control" name="email" placeholder="name@example.com" required="required">
            </div>
            <div>
                <button type="submit" class="btn btn-success" name="submit">Save</button>
                <a href="<c:url value="/users"/>" class="btn btn-danger">Cancel</a>
            </div>
        </form:form>
    </div>
</div>
