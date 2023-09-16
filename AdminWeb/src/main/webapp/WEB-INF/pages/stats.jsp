<%-- 
    Document   : stats
    Created on : Sep 9, 2023, 1:20:18 PM
    Author     : ad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<h1 class="text-center text-info">Statistic</h1>
<div class="row">
    <div class="col-md-4 col-sm-4 border border-primary">
        <div id="monthSelection" >
            <label for="month">Select Month</label>
            <select class="form-control"id="month" name="month">
                <c:forEach begin="1" end="12" var="monthNumber">
                    <option value="${monthNumber}">${monthNumber}</option>
                </c:forEach>
            </select>
        </div>
        <div id="quarterSelection" style="display: none;" >
            <label for="quarter">Select Quarter</label>
            <select id="quarter" name="quarter" class="form-control">
                <c:forEach begin="1" end="4" var="quarter">
                    <option value="${quarter}">${quarter}</option>
                </c:forEach>
            </select>
        </div>
        <div id="yearSelection">
            <label for="year">Select year</label>
            <select id="year" name="year" class="form-control ">
                <c:forEach begin="2022" end="${currentYear}" var="year">
                    <option value="${year}">${year}</option>
                </c:forEach>
            </select>
        </div>
        <div class="d-flex align-items-center justify-content-around">
            <div >
                <input type="radio" id="monthRadio" name="selection" value="month" onclick="showSelection()" checked> 
                <label for="monthRadio">Month</label>
            </div>
            <div >
                <input type="radio" id="quarterRadio" name="selection" value="quarter" onclick="showSelection()"> 
                <label for="quarterRadio">Quarter</label>
            </div>
        </div>
        <div class="d-flex align-items-center  justify-content-around mb-3">
            <button class="btn btn-warning" onclick="getData("user")">Stat by User</button>
            <button class="btn btn-warning" onclick="getData("post")">Stat by Post</button>
        </div>
    </div>
    <div class="col-md-7 col-sm-12">
        <canvas id="myChart"></canvas>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
                const showSelection = () => {
                    var selectedOption = document.querySelector('input[name="selection"]:checked').value;
                    var monthSelection = document.getElementById("monthSelection");
                    var quarterSelection = document.getElementById("quarterSelection");
                    monthSelection.style.display = "none";
                    quarterSelection.style.display = "none";
                    if (selectedOption === "month")
                        monthSelection.style.display = "block";
                    else if (selectedOption === "quarter")
                        quarterSelection.style.display = "block";
                };
                const formatDate = (originalDateString) => {
                    const originalDate = new Date(originalDateString);
                    const day = originalDate.getDate();
                    const month = originalDate.getMonth() + 1;
                    const year = originalDate.getFullYear();
                    const hours = originalDate.getHours();
                    const minutes = originalDate.getMinutes();
                    const seconds = originalDate.getSeconds();
                    const formattedDate = `\${day.toString().padStart(2, '0')}-\${month.toString().padStart(2, '0')}-\${year}`;
                    const formattedTime = `\${hours.toString().padStart(2, '0')}:\${minutes.toString().padStart(2, '0')}:\${seconds.toString().padStart(2, '0')}`;
                    return formattedDate + " " + formattedTime;
                };
                const getData = (t) => {
                    const selectedOption = document.querySelector('input[name="selection"]:checked').value;
                    const selectedMonth = document.getElementById("month").value;
                    const selectedQuarter = document.getElementById("quarter").value;
                    const selectedYear = document.getElementById("year").value;
                    const params = {
                        fromDate '',
                        toDate '',
                        type: ''
                    };
                    param["type"]=selectedOption
                    const formatter = new Intl.DateTimeFormat('en-US', {
                        year: 'numeric',
                        month: '2-digit',
                        day: '2-digit',
                        hour: 'numeric',
                        minute: 'numeric',
                        second: 'numeric',
                        hour12: true
                    });
                    if (selectedOption === 'quarter') {
                        const quarterToMonth = {1: 1, 2: 4, 3: 7, 4: 10};
                        const startMonth = quarterToMonth[selectedQuarter];
                        const endMonth = startMonth + 2;
                        fromDate = new Date(selectedYear, startMonth - 1, 1, 0, 0, 0); // -1 since months are zero-based
                        toDate = new Date(selectedYear, endMonth - 1, 31, 23, 59, 59); // Assuming end of month and day
                        param["fromDate"] = formatter.format(fromDate);
                        param["toDate"] = formatter.format(toDate);
                    } else if (selectedOption === 'month') {
                        const monthNumber = parseInt(selectedMonth);
                        fromDate = new Date(selectedYear, monthNumber - 1, 1, 0, 0, 0); // -1 since months are zero-based
                        const lastDayOfMonth = new Date(selectedYear, monthNumber, 0); // This gives the last day of the month
                        toDate = new Date(selectedYear, monthNumber - 1, lastDayOfMonth.getDate(), 23, 59, 59);
                        param["fromDate"] = formatter.format(fromDate);
                        param["toDate"] = formatter.format(toDate);
                    }
                    const baseUrl = t==="user"?'<c:url value="/admin/stats-user" />':'<c:url value="/admin/stats-post" />'; // Replace with your actual base URL
                    const queryString = Object.keys(params)
                            .map(key => encodeURIComponent(key) + '=' + encodeURIComponent(params[key]))
                            .join('&');
                    const fullUrl = baseUrl + queryString;
                };
</script>