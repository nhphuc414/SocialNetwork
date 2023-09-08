<%-- 
    Document   : base
    Created on : Jul 25, 2023, 4:44:50 PM
    Author     : ad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>
            <tiles:insertAttribute name="title" />
        </title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
        <link href="<c:url value="/css/style.css"/>" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
        <script src="<c:url value="/js/script.js"/>"></script>    
    </head>
    <body>
        
        <div class="d-flex" id="wrapper">
            <tiles:insertAttribute name="side" />
            <div id="page-content-wrapper" >
                <div>
                    <tiles:insertAttribute name="header" />
                    <div class="container-fluid px-4 flex-grow">
                        <tiles:insertAttribute name="content" />
                    </div>
                    <tiles:insertAttribute name="footer" />
                </div>
            </div>
        </div>
        
    </body>
</html>
