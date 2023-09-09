<%-- 
    Document   : stats
    Created on : Sep 9, 2023, 1:20:18 PM
    Author     : ad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="container">
    <h1 class="mt-5">Thống kê số người dùng và số bài viết</h1>
    <div class="row mt-5">
        <div class="col-md-4">
            <h2>Biểu mẫu thống kê</h2>
            <form method="post" action="/processForm">
                <!-- Đặt các trường biểu mẫu ở đây -->
                <button type="submit" class="btn btn-primary">Xem thống kê</button>
            </form>
        </div>
        <div class="col-md-8">
            <h2>Biểu đồ thống kê</h2>
            <canvas id="myChart" width="400" height="400"></canvas>
            <script>
                var ctx = document.getElementById('myChart').getContext('2d');
                var myChart = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: ['Tháng 1', 'Tháng 2', 'Tháng 3', '...'],
                        datasets: [{
                                label: 'Số người dùng',
                                data: [10, 20, 30, '...'], // Số liệu thống kê người dùng theo tháng
                                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                                borderColor: 'rgba(75, 192, 192, 1)',
                                borderWidth: 1
                            },
                            {
                                label: 'Số bài viết',
                                data: [5, 15, 25, '...'], // Số liệu thống kê bài viết theo tháng
                                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                                borderColor: 'rgba(255, 99, 132, 1)',
                                borderWidth: 1
                            }]
                    },
                    options: {
                        scales: {
                            y: {
                                beginAtZero: true
                            }
                        }
                    }
                });
            </script>
        </div>
    </div>
</div>
