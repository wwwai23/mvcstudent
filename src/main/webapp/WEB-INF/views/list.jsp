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
<style>
.mx {
	margin-right: 1.5rem !important;
	margin-left: 5.5rem !important;
}

.hidden-file-input {
	display: none;
}
</style>

 <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
	
</head>
<body>


	<nav class="navbar bg-body-tertiary">
		<div class="container-fluid mx">
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
						value="<%=request.getParameter("search") != null ? request.getParameter("search") : ""%>">
				</div>

				<button class="btn btn-outline-success" type="submit">Search</button>

			</form>


			<div class="ms-auto d-flex align-items-center">
				<input type="button" value="New"
					class="btn btn-md btn-outline-primary ms-2"
					onclick="location.href='/mvcstudent/display1'">


				<form action="/mvcstudent/exportExcel" method="post">
					<input type="hidden" name="search"
						value="<%=request.getParameter("search") != null ? request.getParameter("search") : ""%>">
					<button type="submit" class="btn btn-outline-primary ms-4">Export</button>
				</form>

				<button type="button" class="btn btn-outline-primary ms-2"
					id="importButton" data-toggle="modal" data-target="#importModal">Import</button>
			</div>
			</div>
	</nav>

	 <div class="modal fade" id="importModal" tabindex="-1" role="dialog" aria-labelledby="importModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="importModalLabel">Choose Excel File</h5>
                    
                </div>
                <form id="importForm" action="/mvcstudent/importExcel" method="post" enctype="multipart/form-data">
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="fileInput" class="col-form-label"></label>
                            <input id="fileInput" type="file" name="file" accept=".xlsx, .xls" class="form-control-file">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Upload</button>
                    </div>
                     <script>
        // jQuery is used here to ensure compatibility with Bootstrap's data-toggle behavior
        $(document).ready(function() {
            $('#importButton').on('click', function() {
                $('#importModal').modal('show');
            });

            $('#importForm').on('submit', function() {
                $('#importModal').modal('hide');
            });
        });
    </script>
                </form>
            </div>
        </div>
        
    </div>
    
      
	<div class="container">

		<%-- Retrieve list of students from the database --%>

		<%
		Integer studentID = null;
		String searchValue = request.getParameter("search");
		Integer page1 = 1; // Default page number
		Integer pageSize = 7; // Default page size

		// Retrieve page and pageSize from request parameters, if present
		if (request.getParameter("page") != null) {
			try {
				page1 = Integer.parseInt(request.getParameter("page"));
			} catch (NumberFormatException e) {
				page1 = 7; // Default to first page if parameter is invalid
			}
		}

		if (request.getParameter("pageSize") != null) {
			try {
				pageSize = Integer.parseInt(request.getParameter("pageSize"));
			} catch (NumberFormatException e) {
				pageSize = 5;
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
			students = pDao.getStudentByID(studentID, page1, pageSize);
			totalStudents = pDao.getStudentByIDCount(studentID);
		} else if (searchValue != null && !searchValue.isEmpty()) {
			// Perform search by Name or Township
			students = pDao.searchByNameOrTownship(searchValue, page1, pageSize);
			totalStudents = pDao.searchByNameOrTownshipCount(searchValue);
		} else {
			// Get all students
			students = pDao.getStudents(page1, pageSize);
			totalStudents = pDao.getStudentCount();
		}

		logger.info("student---------------" + students.size());

		int totalPages = (int) Math.ceil((double) totalStudents / pageSize);;

		request.setAttribute("students", students);
		request.setAttribute("currentPage", page1);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("search", searchValue);
		%>

		<%
		if (students.size() == 0) {
		%>

		<div class="d-flex justify-content-center mt-5 fs-6">No Student
			Found</div>
		<%
		} else {
		%>

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
					}
					%>
				</tbody>
			</table>

		</form>
		<%
		}
		%>

		<%
		int currentPage = (Integer) request.getAttribute("currentPage");
		String search = (String) request.getAttribute("search");

		if (search == null) {
			search = "";
		}
		%>


		<%
		if (totalPages >= 1) {
		%>
		<nav aria-label="Page navigation">
			<ul class="pagination justify-content-center">
				<li class="page-item <%=currentPage == 1 ? "disabled" : ""%>">
					<a class="page-link"
					href="?page=<%=currentPage - 1%>&pageSize=<%=pageSize%>&search=<%=search%>"
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a>
				</li>

				<%
				for (int i = 1; i <= totalPages; i++) {
				%>
				<li class="page-item <%=i == currentPage ? "active" : ""%>">
					<a class="page-link"
					href="?page=<%=i%>&pageSize=<%=pageSize%>&search=<%=search%>"><%=i%></a>
				</li>
				<%
				}
				%>

				<li
					class="page-item <%=currentPage == totalPages ? "disabled" : ""%>">
					<a class="page-link"
					href="?page=<%=currentPage + 1%>&pageSize=<%=pageSize%>&search=<%=search%>"
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a>
				</li>

				<li class="page-item disabled mx-4"><span class="page-link">Total:
						<%=totalStudents%></span></li>
			</ul>

		</nav>
		<%
		}
		%>

		<div class="d-flex justify-content-between mx-5 mt-5"></div>


	</div>

	<script>
		src = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity = "sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin = "anonymous">
		
		 <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
		   
	
</body>
</html>