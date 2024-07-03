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
<style type="text/css">
.table th,
        .table td  {
  text-align: center;
}

.mx {
    margin-right: 1.5rem !important;
    margin-left: 3.5rem !important;
}

</style>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.13.1/themes/base/jquery-ui.css">

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.13.1/jquery-ui.min.js"></script>

<script type="text/javascript">

function sumTotal(row) {
    const mark1 = parseFloat(row.querySelector('input[name="mark1"]').value) || 0;
    const mark2 = parseFloat(row.querySelector('input[name="mark2"]').value) || 0;
    const total = mark1 + mark2;
    row.querySelector('div[name="total"]').textContent = total;
}

document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('input[name="mark1"], input[name="mark2"]').forEach(input => {
        input.addEventListener('input', (event) => {
            const row = event.target.closest('tr');
            sumTotal(row);
        });
    });
});

	
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
        var cell7 = newRow.insertCell(6);

        cell1.innerHTML = '<div class="input-field col-6 mx"><input type="number" class="d-none form-control" name="detailID" value="0"></div>';
        cell2.innerHTML = '<div class="input-field col-6 mx"><input type="text" class="border-0 bg-transparent form-control" style="outline: none;" value="' + studentID + '" readonly></div>';
        cell3.innerHTML = '<div class="input-field col-6 mx"><input type="text" class="form-control" name="year" required></div>';
        cell4.innerHTML = '<div class="input-field col-6 mx"><input type="number" class="form-control" name="mark1" oninput="sumTotal(this.closest(\'tr\'));" required></div>';
        cell5.innerHTML = '<div class="input-field col-6 mx"><input type="number" class="form-control" name="mark2" oninput="sumTotal(this.closest(\'tr\'));" required></div>';
        cell6.innerHTML = '<div class="input-field col-6 mx" name="total"></div>';
        cell7.innerHTML = '<input type="button" class="btn btn-danger" value="Remove" onClick="deleteRow(this)">';

        document.querySelectorAll('input[name="mark1"], input[name="mark2"]').forEach(input => {
            input.addEventListener('input', (event) => {
                const row = event.target.closest('tr');
                sumTotal(row);
            });
        });
    }

    
    
    function deleteRow(btn) {
    	 var row = btn.parentNode.parentNode;
    	    row.style.display = 'none'; // Hide the row
    	    var deleteInput = document.createElement('input');
    	    deleteInput.type = 'hidden';
    	    deleteInput.name = 'deleteDetailID';
    	    deleteInput.value = row.querySelector('input[name="detailID"]').value;
    	    row.appendChild(deleteInput); // Mark the row for deletion
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


			<h3 style="text-align: center">Student Detail</h3>

			<div class="field" style="width: 85%">
				<div class="d-flex justify-content-between">

					<div class="input-field col-4">
						<label>Student ID</label> <input type="text" class="form-control"
							name="studentID" id="studentID"
							value="<%=student.getStudentID()%>">
					</div>

					<div class="input-field col-4 mx-4">
						<label>Name</label> <input type="text"
							class="form-control" name="studentName" id="studentName"
							value="<%=student.getStudentName()%>">
					</div>
					<div class="input-field col-4">
						<label>DOB</label> 
						<input type="text" class="form-control" 
							name="dob" id="datepicker" placeholder="dd-mm-yy" value=<%=student.getDOB() %>>
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
						 <th scope="col" class="tc"></th>
							<th scope="col" class="tc">StudentID</th>
							<th scope="col" class="tc">Year</th>
							<th scope="col" class="tc">Mark1</th>
							<th scope="col" class="tc">Mark2</th>
							<th scope="col" class="tc">Total</th>
							<th scope="col" class="tc"><button id="addRowBtn" type="button" class="btn btn-primary" onclick="addRow()">Add</button></th>

						</tr>
					</thead>
					<tbody>
						<%
						if (studentDetails != null) {
							for (Student1 detail : studentDetails) {
						%>
						<tr>
					<td class="d-none"><div class="input-field col-6 mx"><input type="number" name="detailID" class="form-control" value="<%=detail.getID()%>"></div></td>
					<td><div class="input-field col-6 mx-4"></div></td>
					
							<td><div class="input-field col-6 mx"><input  type="text" class="form-control border-0 bg-transparent"
								style="outline: none;" name="studentID" disabled
								value="<%=detail.getStudentID()%>"></div>
							</td>
							
							<td><div class="input-field col-6 mx"><input class="form-control" type="text"  name="year" 
							value="<%=detail.getYear()%>"></div>
							</td>
							
							<td><div class="input-field col-6 mx"><input type="number" class="form-control" name="mark1"
                            oninput="sumTotal(this.closest('tr'));" value="<%=detail.getMark1()%>"></div></td>
                            <td><div class="input-field col-6 mx"><input type="number" class="form-control" name="mark2"
                            oninput="sumTotal(this.closest('tr'));" value="<%=detail.getMark2()%>"></div></td>
						
								
							<td><div class="input-field col-6 mx-4" id="total" name="total"><%=detail.getMark1() + detail.getMark2()%></div></td>


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
				
				
					<div class="d-flex mt-5 w-50">
		<input type="submit" value="Update" class="btn btn-md btn-outline-secondary me-4" 
						onclick="setFormAction('/mvcstudent/update/<%=student.getStudentID() %>','post')">

					<input type="button" value="Delete" class="btn btn-md btn-outline-danger me-4" 
					onclick="setFormAction('/mvcstudent/delete/<%= student.getStudentID() %>', 'post')">

					<input type="button" value="List"
						class="btn btn-md btn-outline-warning"
						onclick="location.href='/mvcstudent/list1'">

				</div>
			</div>
			
			
			
		
	</form>
	
				
				
			
		<script>
$(document).ready(function(){
	$('#datepicker').datepicker({
		dateFormat: "dd-mm-yy"
	});
})

</script>		

</body>
</html>