<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="app.models.Student"%>
<%@ page import="app.models.Student1"%>
<%@ page import="app.daos.StudentDao"%>
<%@ page import="java.util.logging.Logger"%>
<%@ page import="java.util.logging.Level"%>
<!DOCTYPE html>
<html>
<head>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<meta charset="ISO-8859-1">

<title>Student Form</title>

<script type="text/javascript">
    function setFormAction(action, method='post') {
    	const form = document.getElementById("studentForm");
        form.action = action;
        form.method = method;
        form.submit();
    }
    
    function addRow() {
    	 var studentID = document.getElementById("studentID").value;
        var table = document.getElementById("detailTable");
        var tbody = table.getElementsByTagName("tbody")[0];
        var newRow = tbody.insertRow();
        
        var cell1 = newRow.insertCell(0);
        var cell2 = newRow.insertCell(1);
        var cell3 = newRow.insertCell(2);
        var cell4 = newRow.insertCell(3);
        var cell5 = newRow.insertCell(4);
        var cell6 = newRow.insertCell(5);
        
        
        cell1.innerHTML = '<input type="text" class="border-0 bg-transparent" style="outline: none;" value="' + studentID + '" readonly>';
        cell2.innerHTML = '<input type="text"  name="year">';
        cell3.innerHTML = '<input type="text" name="mark1">';
        cell4.innerHTML = '<input type="text"  name="mark2">';
        cell5.innerHTML = '<div name="total" value=""></div>';
        cell6.innerHTML = '<input type="button" class="btn btn-danger" value="Remove" onClick="deleteRow(this)">';
    }
    
    function deleteRow(btn) {
        var row = btn.parentNode.parentNode;
        row.parentNode.removeChild(row);
    }
    document.getElementById("addRowBtn").addEventListener("click", addRow);
    
</script>

</head>
<body>

	<form id="studentForm" class="form-control">

		<div class="container">


			<%
			Logger logger = Logger.getLogger("MyLogger");
			logger.setLevel(Level.ALL);
			%>

			<%-- Retrieve list of students from the database --%>
			<%
			try {
				String studentID = request.getParameter("studentID");
				int studentId = 0;

				//change parameter from string to integer
				studentId = Integer.parseInt(studentID);
				StudentDao studentDao = new StudentDao();
				List<Student> students = studentDao.getStudentByID(studentId);
				List<Student1> studentDetails = studentDao.getDetail(studentId);
				

				request.setAttribute("student", students);
				request.setAttribute("studentDetails", studentDetails);

				// Log successful retrieval of student list

			} catch (Exception e) {
				// Log error if retrieval fails
				logger.log(Level.SEVERE, "Error occurred while retrieving student list", e);
			}
			%>



			<%
			List<Student> students = (List<Student>) request.getAttribute("student");
			
			Student student = students.get(0);
			List<Student1> studentDetails = (List<Student1>) request.getAttribute("studentDetails");
			%>


			<h3 style="text-align: center">Student Form</h3>

			<div class="field" style="width: 85%">
				<div class="d-flex justify-content-between">

					<div class="input-field col-4">
						<label>Student ID</label> <input type="text" class="form-control"
							name="studentID" id="studentID"
							value="<%=student.getStudentID()%>">
					</div>

					<div class="input-field col-4 mx-4">
						<label>Student Name</label> <input type="text"
							class="form-control" name="studentName" id="studentName"
							value="<%=student.getStudentName()%>">
					</div>
					<div class="input-field col-4">
						<label>DOB</label> <input type="date" class="form-control"
							name="dob" id="dob" value="<%=student.getDOB()%>">
					</div>

				</div>

				<div class="d-flex justify-content-between my-5">
					<div class="input-field col-4">
						<label>NRC</label> <input type="text" class="form-control"
							name="nrc" id="NRC" value="<%=student.getNRC()%>">
					</div>

					<div class="input-field col-4 mx-4">
						<label>Phone</label> <input type="text" class="form-control"
							name="phone" id="phone" value="<%=student.getPhone()%>">
					</div>
					<div class="input-field col-4">
						<label>Email</label> <input type="text" class="form-control"
							name="email" id="email" value="<%=student.getEmail()%>">
					</div>
				</div>

				<div class="d-flex justify-content-between">
					<div class="input-field col-4">
						<label>Township</label> <input type="text" class="form-control"
							name="township" id="township" value="<%=student.getTownship()%>">
					</div>

					<div class="input-field col-4 mx-4">
						<label>State</label> <input type="text" class="form-control"
							name="state" id="state" value="<%=student.getState()%>">
					</div>
					<div class="input-field col-4">
						<label>Address</label>
						<textarea placeholder="Address" class="form-control" id="address"
							name="address"><%=student.getAddress()%></textarea>
					</div>
				</div>


				<table class="table" id="detailTable">
					<thead>
						<tr>
							<th class="d-none" scope="col">Detail ID</th>
							<th scope="col">StudentID</th>
							<th scope="col">Year</th>
							<th scope="col">Mark1</th>
							<th scope="col">Mark2</th>
							<th scope="col">Total</th>
							<th scope="col"><button id="addRowBtn" type="button" class="btn btn-primary" onclick="addRow()">Add</button></th>

						</tr>
					</thead>
					<tbody>
						<%
						if (studentDetails != null) {
							for (Student1 detail : studentDetails) {
						%>
						<tr>
						<td class="d-none"><input type="number" name="detailID" value="<%=detail.getID()%>"></td>
							<td><input type="text" class="border-0 bg-transparent"
								style="outline: none;" name="studentID" disabled
								value="<%=detail.getStudentID()%>"></td>
							<td><input type="text"  name="year" 
							value="<%=detail.getYear()%>"></td>
							<td><input type="number"  name="mark1"
								value="<%=detail.getMark1()%>"></td>
							<td><input type="number"  name="mark2"
								value="<%=detail.getMark2()%>"></td>
							<td><div   name="total" 
								value=""><%=detail.getMark1() + detail.getMark2()%></div></td>


							<td><input type="button" class="btn btn-danger"
								value="Remove" onClick="deleteRow(this)"></td>
						</tr>
						<%
						}
						}
						%>
					</tbody>
				</table>
			</div>
	</form>
	<div class="d-flex justify-content-between mt-5 w-50">
					<input type="submit" value="Save" onclick="setFormAction('/mvcstudent/cs')"
						class="btn btn-md btn-outline-primary"> 
						
						<input type="submit" value="Update" class="btn btn-md btn-outline-secondary" 
						onclick="setFormAction('/mvcstudent/update/<%= student.getStudentID() %>', 'post')">

					<input type="button" value="Delete" class="btn btn-md btn-outline-danger" 
					onclick="setFormAction('/mvcstudent/delete/<%= student.getStudentID() %>', 'post')">

					<input type="button" value="List"
						class="btn btn-md btn-outline-warning"
						onclick="location.href='/mvcstudent/list1'">

				</div>
				
				
			
				

</body>
</html>