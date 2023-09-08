<%-- 
    Document   : addnew
    Created on : Sep 8, 2023, 4:26:09 PM
    Author     : ad
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<div class="container">
    <div class="text-center mb-4">
        <h3>Add New User</h3>
    </div>
    <div class="container d-flex justify-content-center">
        <form:form method="post" action="${action}" modelAttribute="product" enctype="multipart/form-data">
        <form action="" method="post" style="width:50vw; min-width:300px;">
            <div class="mb-3">
                <div class="mb-3">
                    <label class="form-label">Display name</label>
                    <input type="text" class="form-control" name="email" placeholder="Enter display name here">
                </div>

                <div class="col">
                    <label class="form-label">Username</label>
                    <input type="text" class="form-control" name="first_name" placeholder="Enter username here">
                </div>
                <div class="col">
                    <label class="form-label">Password</label>
                    <input type="password" class="form-control" name="last_name" placeholder="Enter password here">
                </div>
                <div class="col">
                    <label class="form-label">Confirm Password</label>
                    <input type="password" class="form-control" name="last_name" placeholder="Enter confirm password here">
                </div>
            </div>
            <div class="mb-3">
                <label class="form-label">Email</label>
                <input type="email" class="form-control" name="email" placeholder="name@example.com">
            </div>
            <div>
                <button type="submit" class="btn btn-success" name="submit">Save</button>
                <a href="#" class="btn btn-danger">Cancel</a>
            </div>
        </form>
    </div>
</div>
