<%@page import="app.daos.PaginationDao"%>
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
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
</head>
<body>


	<nav class="navbar bg-body-tertiary">
		<div class="container-fluid">
			<a class="navbar-brand">Student List</a>
			<form class="d-flex" role="search" action="/mvcstudent/list1"
				method="get">

				<%
				Logger logger = Logger.getLogger("MyLogger");
				logger.setLevel(Level.ALL);
				%>

				<%-- Retrieve list of students from the database --%>
				
				<div class="input-group">
					<input class="form-control me-2" name="search" type="search"
						placeholder="Search Here" aria-label="Search by ID or Name" 
						value="<%= request.getParameter("search") != null ? request.getParameter("search") : "" %>">
				</div>

				<button class="btn btn-outline-success" type="submit">Search</button>
			</form>
			
			<form action="/mvcstudent/exportExcel" method="post">
    <button type="submit" class="btn btn-outline-primary">Export to Excel</button>
</form>

			<!-- Import Button and Form -->
			<form action="/mvcstudent/importExcel" method="post" enctype="multipart/form-data" class="ms-2">
				<input type="file" name="file" accept=".xlsx, .xls" class="form-control-file d-inline">
				<button type="submit" class="btn btn-outline-primary">Import from Excel</button>
			</form>
			
			
		</div>
		</div>
	</nav>


	<div class="container">
	
		<%-- Retrieve list of students from the database --%>
		
<% 
    Integer studentID = null;
    String searchValue = request.getParameter("search");
    Integer page1 = 1; // Default page number
    Integer pageSize = 10; // Default page size

    // Retrieve page and pageSize from request parameters, if present
    if (request.getParameter("page") != null) {
        try {
            page1 = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {
            page1 = 1; // Default to first page if parameter is invalid
        }
    }
    
    if (request.getParameter("pageSize") != null) {
        try {
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
        } catch (NumberFormatException e) {
            pageSize = 10; // Default to 10 if parameter is invalid
        }
    }

    try {
        studentID = Integer.parseInt(searchValue);
    } catch (NumberFormatException e) {
        // Ignore the exception; it means the input is not a valid integer
    }

    StudentDao studentDao = new StudentDao();
    PaginationDao pDao = new PaginationDao();
    List<Student> students;
    int totalStudents;

    if (studentID != null) {
        // Perform search by ID
        students = studentDao.getStudentByID(studentID); // No pagination needed for search by ID
        totalStudents = pDao.getStudentByIDCount(studentID);
    } else if (searchValue != null && !searchValue.isEmpty()) {
        // Perform search by Name or Township
        students = studentDao.searchByNameOrTownship(searchValue); // No pagination needed for search by Name or Township
        totalStudents = pDao.searchByNameOrTownshipCount(searchValue);
    } else {
        // Get all students
        students = pDao.getStudents(page1, pageSize);
        totalStudents = pDao.getStudentCount();
    }
    
    logger.info("student---------------" + students.size());


    int totalPages =  (int) Math.ceil((double) totalStudents / pageSize);;

    request.setAttribute("students", students);
    request.setAttribute("currentPage", page1);
    request.setAttribute("totalPages", totalPages);
    request.setAttribute("pageSize", pageSize);
    request.setAttribute("search", searchValue);
%>

<%
	if (students.size() == 0) {
%>
<div class="d-flex justify-content-center mt-5 fs-6">No Student Found</div>
<% } else { %>
	<form action="/list1" method="get">
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
			<%
			if (students != null && students.size() != 0) {
				for (Student student : students) {
			%>
			<tr>
				<td><a class="text-decoration-none"
					href="detail?studentID=<c:out value="<%=student.getStudentID()%>"/>"><c:out
							value="<%=student.getStudentID()%>" /></a></td>
				<td><c:out value="<%=student.getStudentName()%>" /></td>
				<td><c:out value="<%=student.getDOB()%>" /></td>
				<td><c:out value="<%=student.getNRC()%>" /></td>
				<td><c:out value="<%=student.getTownship()%>" /></td>


			</tr>
			<%
			}
      	} %>
		</tbody>
	</table>
</form>
<% } %>
		<% int currentPage = (Integer) request.getAttribute("currentPage"); 
			 String search = (String) request.getAttribute("search");
		
		if (search == null) {
        search = "";
    }
    
    %>
		
		
<% if (totalPages > 1) { %>
    <nav aria-label="Page navigation">
        <ul class="pagination justify-content-center">
            <li class="page-item <%= currentPage == 1 ? "disabled" : "" %>">
                <a class="page-link" href="?page=<%= currentPage - 1 %>&pageSize=<%= pageSize %>&search=<%= search %>" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>

            <% for (int i = 1; i <= totalPages; i++) { %>
                <li class="page-item <%= i == currentPage ? "active" : "" %>">
                    <a class="page-link" href="?page=<%= i %>&pageSize=<%= pageSize %>&search=<%= search %>"><%= i %></a>
                </li>
            <% } %>

            <li class="page-item <%= currentPage == totalPages ? "disabled" : "" %>">
                <a class="page-link" href="?page=<%= currentPage + 1 %>&pageSize=<%= pageSize %>&search=<%= search %>" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
    </nav>
<% } %>

<input type="button" value="Create New Student"
						class="btn btn-md btn-outline-primary"
						onclick="location.href='/mvcstudent/display1'">
	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>
</body>
</html>