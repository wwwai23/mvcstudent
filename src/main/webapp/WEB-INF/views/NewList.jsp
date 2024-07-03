<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.List"%>
<%@ page import="app.models.Student"%>
<%@ page import="app.daos.StudentDao"%>
<%@ page import="java.util.logging.Logger"%>
<%@ page import="java.util.logging.Level"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>StudentList</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
      crossorigin="anonymous">
</head>
<body>

<nav class="navbar bg-body-tertiary">
    <div class="container-fluid">
        <a class="navbar-brand">Student List</a>
        <form class="d-flex" role="search" action="/mvcstudent/list1" method="get">
            <div class="input-group">
                <input class="form-control me-2" name="search" type="search"
                       placeholder="Search Here" aria-label="Search by ID or Name" 
                       value="${param.search != null ? param.search : ''}">
            </div>
            <button class="btn btn-outline-success" type="submit">Search</button>
        </form>
    </div>
</nav>

<div class="container">
    <c:choose>
        <c:when test="${students.size() == 0}">
            <div class="d-flex justify-content-center mt-5 fs-6">No Student Found</div>
        </c:when>
        <c:otherwise>
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">StudentID</th>
                        <th scope="col">Name</th>
                        <th scope="col">DOB</th>
                        <th scope="col">NRC</th>
                        <th scope="col">Township</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="student" items="${students}">
                        <tr>
                            <td><a class="text-decoration-none" href="detail?studentID=${student.studentID}">${student.studentID}</a></td>
                            <td>${student.studentName}</td>
                            <td>${student.DOB}</td>
                            <td>${student.NRC}</td>
                            <td>${student.township}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            
            <div>
                <c:if test="${currentPage > 1}">
                    <a href="/mvcstudent/list1?page=${currentPage - 1}&pageSize=${pageSize}">Previous</a>
                </c:if>
                <c:forEach var="i" begin="1" end="${totalPages}">
                    <c:choose>
                        <c:when test="${i == currentPage}">
                            <strong>${i}</strong>
                        </c:when>
                        <c:otherwise>
                            <a href="/mvcstudent/list1?page=${i}&pageSize=${pageSize}">${i}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:if test="${currentPage < totalPages}">
                    <a href="/mvcstudent/list1?page=${currentPage + 1}&pageSize=${pageSize}">Next</a>
                </c:if>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>
