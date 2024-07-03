<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="app.models.Student"%>
<%@ page import="app.daos.StudentDao"%>
<%@ page import="java.util.logging.Logger"%>
<%@ page import="java.util.logging.Level"%>
<!DOCTYPE html>
<html>
<head>

<style type="text/css">
.table th,
        .table td  {
  text-align: center;
}

.mx {
    margin-right: 1.5rem !important;
    margin-left: 4.5rem !important;
}

</style>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<meta charset="ISO-8859-1">

<title>Student Form</title>

<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.13.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.13.1/jquery-ui.min.js"></script>
<script>

function sumTotal() {
	  const mark1 = parseFloat(document.getElementById('mark1').value) || 0;
	  const mark2 = parseFloat(document.getElementById('mark2').value) || 0;
	  const total = mark1 + mark2;
	  document.getElementById('total').value = total;
	}

	document.addEventListener('DOMContentLoaded', () => {
	  // Add event listeners to the initial row's input fields
	  document.getElementById('mark1').addEventListener('input', sumTotal);
	  document.getElementById('mark2').addEventListener('input', sumTotal);
	});
	
	function sumTotala() {
		  const mark1 = parseFloat(document.getElementById('mark1a').value) || 0;
		  const mark2 = parseFloat(document.getElementById('mark2a').value) || 0;
		  const total = mark1 + mark2;
		  document.getElementById('totala').value = total;
		}

		document.addEventListener('DOMContentLoaded', () => {
		  // Add event listeners to the initial row's input fields
		  document.getElementById('mark1a').addEventListener('input', sumTotal);
		  document.getElementById('mark2a').addEventListener('input', sumTotal);
		});
		
	
	function addRow() {
		var table = document.getElementById("detailTable");
		var tbody = table.getElementsByTagName("tbody")[0];
		var newRow = tbody.insertRow();

		var cell1 = newRow.insertCell(0);
		var cell2 = newRow.insertCell(1);
		var cell3 = newRow.insertCell(2);
		var cell4 = newRow.insertCell(3);
		var cell5 = newRow.insertCell(4);
		var cell6 = newRow.insertCell(5);

		cell1.innerHTML = '<div class="input-field col-6 mx"><input type="text" class="form-control" name="year" id="year" required></div>';
		cell2.innerHTML = '<div class="input-field col-6 mx"><input type="number" class="form-control" name="mark1" id="mark1a" onkeyup="sumTotala();" required></div>';
		cell3.innerHTML = '<div class="input-field col-6 mx"><input type="number" class="form-control" name="mark2" id="mark2a" onkeyup="sumTotala();" required></div>';
		cell4.innerHTML = '<div class="input-field col-6 mx"><input type="number" class="form-control" name="total" id="totala" readonly></div>';
		cell5.innerHTML = '<input type="button" class="btn btn-danger" value="Remove" onClick="deleteRow(this)">';
	}

	function deleteRow(btn) {
		var row = btn.parentNode.parentNode;
		row.parentNode.removeChild(row);
	}
	document.getElementById("addRowBtn").addEventListener("click", addRow);
	
	
</script>

</head>
<body>

	<form action="/mvcstudent/c" method="post" class="form-control">

		<div class="container">


			<%
			Logger logger = Logger.getLogger("MyLogger");
			logger.setLevel(Level.ALL);
			%>

			<%-- Retrieve list of students from the database --%>
			<%
			try {
				//String studentID = request.getParameter("studentID");	  
				int studentId = 0;
				//studentId = Integer.parseInt(studentID);
				StudentDao studentDao = new StudentDao();
				int students = studentDao.deleteStudent(studentId);
				request.setAttribute("students", students);

				// Log successful retrieval of student list

			} catch (Exception e) {
				// Log error if retrieval fails
				logger.log(Level.SEVERE, "Error occurred while retrieving student list", e);
			}
			%>




			<h3 style="text-align: center">Student Form</h3>

			<div class="field" style="width: 90%">

				<div class="d-flex justify-content-between">

					<div class="input-field col-4 mx-4">
						<label>Name</label> <input type="text"
							class="form-control" name="studentName" id="studentName"
							placeholder="Enter your name" required>
					</div>

					<div class="input-field col-4 mx-4">
						<label>DOB</label> <input type="text" class="form-control"
							name="dob" id="datepicker" placeholder="dd-mm-yyyy" required>
					</div>

					<div class="input-field col-4">
						<label>NRC</label> <input type="text" class="form-control"
							name="nrc" id="NRC" placeholder="NRC" required>
					</div>

				</div>

				<div class="d-flex justify-content-between my-5">

					<div class="input-field col-4 mx-4">
						<label>Phone</label> <input type="text" class="form-control"
							name="phone" id="phone" placeholder="Enter your phone number"
							required>
					</div>

					<div class="input-field col-4 mx-4">
						<label>Email</label> <input type="text" class="form-control"
							name="email" id="email" placeholder="Enter your email address"
							required>
					</div>

					<div class="input-field col-4">
						<label>Township</label> <input type="text" class="form-control"
							name="township" id="township" placeholder="Township" required>
					</div>
				</div>


				<div class="d-flex justify-content-between">

					<div class="input-field col-4 mx-4">
						<label>State</label> <input type="text" class="form-control"
							name="state" id="state" placeholder="State" required>
					</div>
					
					<div class="input-field col-4 mx-4">
						<label>Address</label>
						<textarea placeholder="Address" class="form-control" id="address"
							name="address" required></textarea>
					</div>
					 <div class="input-field col-4 mx-4"></div>
					
				</div>

			</div>
			<br>
			<table class="table" id="detailTable">

				<thead>
					<tr>
						<th class="d-none tc" scope="col">Detail ID</th>
						<th scope="col" class="tc">Year</th>
						<th scope="col" class="tc">Mark1</th>
						<th scope="col" class="tc">Mark2</th>
						<th scope="col" class="tc">Total</th>

						<th scope="col"><button id="addRowBtn" type="button"
								class="btn btn-primary" onclick="addRow()">Add</button></th>

					</tr>
				</thead>
				<tbody>

					<tr>

						<td class="d-none"><input type="number" name="detailID"
							value="0"></td>

						<td><div class="input-field col-6 mx">
								<input type="text" class="form-control" name="year" id="state"
									required>
							</div></td>

						<td><div class="input-field col-6 mx">
								<input type="number" class="form-control" name="mark1" id="mark1"
									onkeyup="sumTotal();" required>
							</div></td>

						<td><div class="input-field col-6 mx">
								<input type="number" class="form-control" name="mark2" id="mark2"
									onkeyup="sumTotal();" required>
							</div></td>
						<td><div class="input-field col-6 mx">
								<input type="number" class="form-control" name="total" id="total"
									readonly>
							</div></td>

						<td><input type="button" class="btn btn-danger"
							value="Remove" onClick="deleteRow(this)"></td>
					</tr>

				</tbody>


			</table>




			<div class="d-flex mt-5 w-50">
				<input type="submit" value="Save"
					class="btn btn-md btn-outline-primary me-4">
					
					 <input
					type="button" value="List" class="btn btn-md btn-outline-warning"
					onclick="location.href='/mvcstudent/list1'">

			</div>
		</div>
	</form>


	<script>
		$(document).ready(function() {
			$('#datepicker').datepicker({
				dateFormat : "dd-mm-yy"
			});
		})
	</script>

</body>
</html>