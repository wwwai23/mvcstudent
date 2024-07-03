package app.daos;
import java.util.HashMap;
import app.helper.DBHelper;
import app.models.Student;
import app.models.Student1;
import app.models.StudentWithDetails;

import java.util.logging.Logger;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
//import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class StudentDao {

//	 private JdbcTemplate jdbcTemplate;
//
//	    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
//	        this.jdbcTemplate = jdbcTemplate;
//	    }

	public int createStudent(Student student) {
	    int generatedStudentID = 0;
	    String query = "INSERT INTO studentheader (studentName, DOB, NRC, phone, email, township, state, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	    try (Connection con = DBHelper.getInstance().getConnection();
	         PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
	        ps.setString(1, student.getStudentName());
	        ps.setString(2, student.getDOB());
	        ps.setString(3, student.getNRC());
	        ps.setString(4, student.getPhone());
	        ps.setString(5, student.getEmail());
	        ps.setString(6, student.getTownship());
	        ps.setString(7, student.getState());
	        ps.setString(8, student.getAddress());
	        ps.executeUpdate();

	        ResultSet rs = ps.getGeneratedKeys();
	        if (rs.next()) {
	        	System.out.print("getINt" + rs.getInt(1));
	            generatedStudentID = rs.getInt(1);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return generatedStudentID;
	}

	public int createDetail1(Student1 student1) {
	    int rowsAffected = 0;
	    Connection con = null;
	    PreparedStatement ps = null;

	    try {
	        con = DBHelper.getInstance().getConnection();
	        String query = "INSERT INTO studentdetail(studentID, year, mark1, mark2, total) VALUES (?, ?, ?, ?, ?)";
	        
	        // Insert record without specifying ID, assuming it's auto-incremented
	        
	        ps = con.prepareStatement(query);
	        ps.setInt(1, student1.getStudentID());
	        ps.setString(2, student1.getYear());
	        ps.setInt(3, student1.getMark1());
	        ps.setInt(4, student1.getMark2());
	        ps.setInt(5, student1.getTotal());
	        
	        rowsAffected = ps.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            System.out.println("Detail record inserted successfully.");
	        } else {
	            System.out.println("Failed to insert detail record.");
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // Close resources in finally block
	        try {
	            if (ps != null) {
	                ps.close();
	            }
	            if (con != null) {
	                con.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    return rowsAffected;
	}

	
//	public int createDetail(Student1 student1) {
//	    int condition = 0;
//	    String query = "INSERT INTO studentdetail studentID, year, mark1, mark2, total VALUES (?, ?, ?, ?, ?)";
//
//	    try (Connection con = DBHelper.getInstance().getConnection();
//	         PreparedStatement ps = con.prepareStatement(query)) {
//	        if (!isRecordExists(student1)) {
//	            ps.setInt(1, student1.getStudentID());
//	            ps.setString(2, student1.getYear());
//	            ps.setInt(3, student1.getMark1());
//	            ps.setInt(4, student1.getMark2());
//	            ps.setInt(5, student1.getTotal());
//	            condition = ps.executeUpdate();
//	            System.out.println("Detail inserted: " + student1);
//	        } else {
//	            System.out.println("Record already exists for StudentID: " + student1.getStudentID() + ", Year: " + student1.getYear());
//	        }
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	    }
//	    return condition;
//	}

	public int createDetail(Student1 student1) {
	    int condition = 0;
	    System.out.print("---student1-------" + student1.getStudentID());
	    
	    String query = "INSERT INTO studentdetail (studentID, year, mark1, mark2, total) VALUES (?, ?, ?, ?, ?)";
	 
		try{
			Connection con = DBHelper.getConnection();
			PreparedStatement ps = con.prepareStatement(query);
	        // Check if the record already exists
	    	 System.out.print("Connection established: " + con);
	         System.out.print("PreparedStatement created: " + ps);
            ps.setInt(1, student1.getStudentID());
            ps.setString(2, student1.getYear());
            ps.setInt(3, student1.getMark1());
            ps.setInt(4, student1.getMark2());
            ps.setInt(5, student1.getTotal());

            condition = ps.executeUpdate();
            
            System.out.print("--------------" + condition);

            // Retrieve the generated ID if needed
//            ResultSet generatedKeys = ps.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                int generatedID = generatedKeys.getInt(1);
//                student1.setStudentID(generatedID); // Set the generated ID in the Student1 object
//                System.out.println("Generated ID for studentdetail: " + generatedID);
//            }

	    } catch (SQLException e) {
	    	System.out.print("eeeeeeeeeeeeeeeeeeeeeeee" + e);
	        e.printStackTrace();
	    }
	    
	    System.out.print("condition---------------" + condition);

	    return condition;
	}


//	public int insertDetail(Student1 student) {
//	    int condition = 0;
//	    String query = "INSERT INTO studentdetail (studentID, year, mark1, mark2, total) VALUES (?, ?, ?, ?, ?)";
//
//	    try {
//	        Connection con = DBHelper.getInstance().getConnection();
//	        PreparedStatement ps = con.prepareStatement(query);
//	        
//	        ps.setInt(1, student.getStudentID());
//	        ps.setString(2, student.getYear());
//	        ps.setInt(3, student.getMark1());
//	        ps.setInt(4, student.getMark2());
//	        ps.setInt(5, student.getTotal());
//
//	        condition = ps.executeUpdate();
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	    }
//	    return condition;
//	}

	
	
	public int insertDetail(Student1 student1) {
		int condition = 0;

	String query = "INSERT INTO studentdetail(studentID, year, mark1, mark2, total) VALUES (?, ?, ?, ?, ?)";
	System.out.print("student1 ID" + student1.getStudentID());
		try {
			// Check if the record already exists
			if (!isRecordExists(student1)) {
				Connection con = DBHelper.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement(query);
				ps.setInt(1, student1.getStudentID());
				ps.setString(2, student1.getYear());
				ps.setInt(3, student1.getMark1());
				ps.setInt(4, student1.getMark2());
				ps.setInt(5, student1.getTotal());
				condition = ps.executeUpdate();
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.print("condition----------" + condition);
		return condition;
	}
	

	// Method to check if the record already exists in the database
	private boolean isRecordExists(Student1 student1) {
		
		String query = "SELECT COUNT(*) FROM studentdetail WHERE studentID = ? AND year = ?";
		boolean exists = false;

		try {
			Connection con = DBHelper.getInstance().getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, student1.getStudentID());
			ps.setString(2, student1.getYear());
			ResultSet rs = ps.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			exists = count > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return exists;
	}

	
	
	public int createDetailS(Student1 student) {
		String CHECK_EXISTING_RECORD_QUERY = "SELECT COUNT(*) FROM studentdetail WHERE studentID = ? AND year = ?";
		String INSERT_QUERY = "INSERT INTO studentdetail (studentID, year, mark1, mark2, total) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int condition = 0;

		try {
			Connection con = DBHelper.getInstance().getConnection();
			// Check if the record already exists
			ps = con.prepareStatement(CHECK_EXISTING_RECORD_QUERY);
			ps.setInt(1, student.getStudentID());
			ps.setString(2, student.getYear());
			rs = ps.executeQuery();
			rs.next();
			int count = rs.getInt(1);

			if (count == 0) {
				// Record does not exist, insert new record
				ps = con.prepareStatement(INSERT_QUERY);
				ps.setInt(1, student.getStudentID());
				ps.setString(2, student.getYear());
				ps.setInt(3, student.getMark1());
				ps.setInt(4, student.getMark2());
				ps.setInt(5, student.getTotal());

				condition = ps.executeUpdate();
			} else {
				// Record already exists, log and skip
				System.out.println("Record already exists for studentID: " + student.getStudentID() + ", year: "
						+ student.getYear());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		
		}
		return condition;
	}

	public int deleteStudent(int id) {
		int condition = 0;
		int condition1 = 0;
		String query = "DELETE FROM studentheader WHERE studentID=?";
		String queryDetail = "DELETE FROM studentdetail WHERE studentID=?";
		try {
			Connection con = DBHelper.getInstance().getConnection();
			PreparedStatement psDetail = con.prepareStatement(queryDetail);
			psDetail.setInt(1, id);
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id);
			condition1 = psDetail.executeUpdate();
			condition = ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return condition;
	}

	public int deleteDetail(int detailID) {
	    int condition = 0;
	    String query = "DELETE FROM studentdetail WHERE ID = ?";

	    try {
	        Connection con = DBHelper.getInstance().getConnection();
	        PreparedStatement ps = con.prepareStatement(query);
	        ps.setInt(1, detailID);
	        condition = ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return condition;
	}

	public boolean detailExists(int detailID) {
	    boolean exists = false;
	    String query = "SELECT COUNT(*) FROM studentdetail WHERE ID = ?";

	    try {
	        Connection con = DBHelper.getInstance().getConnection();
	        PreparedStatement ps = con.prepareStatement(query);
	        ps.setInt(1, detailID);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next() && rs.getInt(1) > 0) {
	            exists = true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return exists;
	}

	public int updateStudent(Student student) {
		int condition = 0;
		String query = "UPDATE studentheader SET studentName=?,DOB=?,NRC=?,phone=?,email=?,township=?,state=?,address=? WHERE studentID=?";

		try {
			Connection con = DBHelper.getInstance().getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, student.getStudentName());
			ps.setString(2, student.getDOB());
			ps.setString(3, student.getNRC());
			ps.setString(4, student.getPhone());
			ps.setString(5, student.getEmail());
			ps.setString(6, student.getTownship());
			ps.setString(7, student.getState());
			ps.setString(8, student.getAddress());
			ps.setInt(9, student.getStudentID());

			condition = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return condition;
	}

	public int updateDetail(Student1 student) {
		int condition = 0;
		String query = "UPDATE studentdetail SET year=?,mark1=?,mark2=?,total=? WHERE ID=?";

		try {
			Connection con = DBHelper.getInstance().getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, student.getYear());
			ps.setInt(2, student.getMark1());
			ps.setInt(3, student.getMark2());
			ps.setInt(4, student.getTotal());
			ps.setInt(5, student.getID());

			condition = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return condition;
	}

	public int updateDetails(List<Student1> details) {
		int totalUpdated = 0;
		String queryCheck = "SELECT COUNT(*) FROM studentdetail WHERE studentID=?";
		String queryUpdate = "UPDATE studentdetail SET year=?,mark1=?,mark2=?,total=? WHERE ID=?";

		try {
			Connection con = DBHelper.getInstance().getConnection();
			PreparedStatement psCheck = con.prepareStatement(queryCheck);
			PreparedStatement psUpdate = con.prepareStatement(queryUpdate);

			for (Student1 detail : details) {
				psCheck.setInt(1, detail.getStudentID());
				ResultSet rs = psCheck.executeQuery();
				rs.next();
				int count = rs.getInt(1);

				if (count > 0) {

					psUpdate.setString(1, detail.getYear());
					psUpdate.setInt(2, detail.getMark1());
					psUpdate.setInt(3, detail.getMark2());
					psUpdate.setInt(4, detail.getTotal());
					psUpdate.setInt(5, detail.getID());

					totalUpdated += psUpdate.executeUpdate();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Close resources
			// Handle closing of PreparedStatement and ResultSet if necessary
		}
		return totalUpdated;
	}

	public List<Student> getAllStudent() {

		List<Student> student = new ArrayList<>();
		String query = "SELECT studentID,studentName,DOB,NRC,township FROM studentheader ORDER BY DESC";
		ResultSet rs;

		try {
			Connection con = DBHelper.getInstance().getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				Student st = new Student();
				st.setStudentID(rs.getInt("studentID"));
				st.setStudentName(rs.getString("studentName"));
				st.setDOB(rs.getString("DOB"));
				st.setNRC(rs.getString("NRC"));
				st.setTownship(rs.getString("township"));
				student.add(st);
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		
		}
		
		return student;
	}

	
	public List<Student> searchByNameOrTownship(String value) {
		List<Student> students = new ArrayList<>();
		ResultSet rs;

		String query = "SELECT studentID, studentName, DOB, NRC, township FROM studentheader WHERE studentName LIKE ? OR township LIKE ? OR NRC LIKE ? OR DOB LIKE ?";

		
		try {
			Connection con = DBHelper.getInstance().getConnection();
			PreparedStatement ps = con.prepareStatement(query);
                    

			String wildcardValue = value + "%";
			ps.setString(1, wildcardValue);
			ps.setString(2, wildcardValue);
			ps.setString(3, wildcardValue);
			ps.setString(4, wildcardValue);
			rs = ps.executeQuery();

			while (rs.next()) {
				Student student = new Student();
				student.setStudentID(rs.getInt("studentID"));
				student.setStudentName(rs.getString("studentName"));
				student.setDOB(rs.getString("DOB"));
				student.setNRC(rs.getString("NRC"));
				student.setTownship(rs.getString("township"));
				students.add(student);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}

	public List<Student> getStudentByID(int id) {

		List<Student> students = new ArrayList();
		ResultSet rs;
		String query = "SELECT studentID, studentName, DOB, NRC, phone, email, township, state, address FROM studentheader WHERE studentID=?";

		try {
			Connection con = DBHelper.getInstance().getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			while (rs.next()) {
				Student student = new Student();
				student.setStudentID(rs.getInt("studentID"));
				student.setStudentName(rs.getString("studentName"));
				student.setDOB(rs.getString("DOB"));
				student.setNRC(rs.getString("NRC"));
				student.setPhone(rs.getString("phone"));
				student.setEmail(rs.getString("email"));
				student.setTownship(rs.getString("township"));
				student.setState(rs.getString("state"));
				student.setAddress(rs.getString("address"));
				students.add(student);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return students;
	}

	public List<Student1> getDetail(int studentID) {
		List<Student1> students = new ArrayList<>();
		String query = "SELECT ID,studentID, year, mark1, mark2, total FROM studentdetail WHERE studentID=?";
		try {
			Connection con = DBHelper.getInstance().getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, studentID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Student1 student = new Student1();
				student.setID(rs.getInt("ID"));
				student.setStudentID(rs.getInt("studentID"));
				student.setYear(rs.getString("year"));
				student.setMark1(rs.getInt("mark1"));
				student.setMark2(rs.getInt("mark2"));
				student.setTotal(rs.getInt("total"));
				students.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}

	public int updateStudentAndDetails(Student student, List<Student1> details) {
		int totalUpdated = 0;
		String queryUpdateStudent = "UPDATE studentheader SET studentName=?, DOB=?, NRC=?, phone=?, email=?, township=?, state=?, address=? WHERE studentID=?";
		String queryCheck = "SELECT COUNT(*) FROM studentdetail WHERE studentID=?";
		String queryUpdateDetails = "UPDATE studentdetail SET year=?, mark1=?, mark2=?, total=? WHERE studentID=?";
		String queryInsertDetails = "INSERT INTO studentdetail (studentID, year, mark1, mark2, total) VALUES (?, ?, ?, ?, ?)";

		try {
			Connection con = DBHelper.getInstance().getConnection();
			// Start transaction
			con.setAutoCommit(false);

			// Update student header
			try (PreparedStatement psUpdateStudent = con.prepareStatement(queryUpdateStudent)) {
				psUpdateStudent.setString(1, student.getStudentName());
				psUpdateStudent.setString(2, student.getDOB());
				psUpdateStudent.setString(3, student.getNRC());
				psUpdateStudent.setString(4, student.getPhone());
				psUpdateStudent.setString(5, student.getEmail());
				psUpdateStudent.setString(6, student.getTownship());
				psUpdateStudent.setString(7, student.getState());
				psUpdateStudent.setString(8, student.getAddress());
				psUpdateStudent.setInt(9, student.getStudentID());

				totalUpdated += psUpdateStudent.executeUpdate();
			}

			// Update student details
			try (PreparedStatement psCheck = con.prepareStatement(queryCheck);
					PreparedStatement psUpdateDetails = con.prepareStatement(queryUpdateDetails);
					PreparedStatement psInsertDetails = con.prepareStatement(queryInsertDetails)) {

				for (Student1 detail : details) {
					psCheck.setInt(1, detail.getStudentID());
					ResultSet rs = psCheck.executeQuery();
					rs.next();
					int count = rs.getInt(1);

					if (count > 0) {
						// Update existing details
						psUpdateDetails.setString(1, detail.getYear());
						psUpdateDetails.setInt(2, detail.getMark1());
						psUpdateDetails.setInt(3, detail.getMark2());
						psUpdateDetails.setInt(4, detail.getTotal());
						psUpdateDetails.setInt(5, detail.getStudentID());
						totalUpdated += psUpdateDetails.executeUpdate();
					} else {
						// Insert new details
						psInsertDetails.setInt(1, detail.getStudentID());
						psInsertDetails.setString(2, detail.getYear());
						psInsertDetails.setInt(3, detail.getMark1());
						psInsertDetails.setInt(4, detail.getMark2());
						psInsertDetails.setInt(5, detail.getTotal());
						totalUpdated += psInsertDetails.executeUpdate();
					}
				}
			}

			// Commit transaction
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				Connection con = DBHelper.getInstance().getConnection();
				// Rollback transaction on error
				con.rollback();
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
			}
		} finally {
			try {
				Connection con = DBHelper.getInstance().getConnection();
				// Reset auto-commit to true
				con.setAutoCommit(true);
			} catch (SQLException autoCommitEx) {
				autoCommitEx.printStackTrace();
			}
		}
		return totalUpdated;
	}

	public List<Student> getStudents(int page, int pageSize) {
		List<Student> students = new ArrayList<>();
		String query = "SELECT * FROM studentheader ORDER BY studentID DESC LIMIT ? OFFSET ?";

		try { 
			Connection con = DBHelper.getInstance().getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, pageSize);
			ps.setInt(2, (page - 1) * pageSize);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Student student = new Student(rs.getInt("studentID"), rs.getString("DOB"), rs.getString("studentName"),
						rs.getString("NRC"), rs.getString("phone"), rs.getString("email"), rs.getString("township"),
						rs.getString("state"), rs.getString("address"));
				students.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return students;
	}

	public int getStudentCount(int id) {
		String query = "SELECT COUNT(*) FROM studentheader";
		int count = 0;

		try {
			Connection con = DBHelper.getInstance().getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}

	public List<Student1> getStudentDetails(int studentID, int page, int pageSize) {
		List<Student1> details = new ArrayList<>();
		String query = "SELECT * FROM studentdetail WHERE studentID = ? LIMIT ? OFFSET ?";

		try {
			Connection con = DBHelper.getInstance().getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, studentID);
			ps.setInt(2, pageSize);
			ps.setInt(3, (page - 1) * pageSize);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Student1 st = new Student1();
				st.setID(rs.getInt("ID"));
				st.setStudentID(rs.getInt("studentID"));
				st.setYear(rs.getString("year"));
				st.setMark1(rs.getInt("mark1"));
				st.setMark2(rs.getInt("mark2"));
				st.setTotal(rs.getInt("total"));
				details.add(st);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return details;
	}

	public int getStudentDetailCount(int studentID) {
		String query = "SELECT COUNT(*) FROM studentdetail WHERE studentID = ?";
		int count = 0;

		try {
			Connection con = DBHelper.getInstance().getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, studentID);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}

	//method for export
	
	public void exportStudentsToExcel(String name, OutputStream outputStream) {
	    XSSFWorkbook workbook = new XSSFWorkbook();
	    XSSFSheet sheet = workbook.createSheet("Students");

	    List<StudentWithDetails> students = null;
	    Integer id = null;

	    try {
	        id = Integer.parseInt(name);
	    } catch (NumberFormatException e) {
	        // Ignore the exception; it means the input is not a valid integer
	    }

	    if (id != null) {
	        students = getStudentWithDetails(id);
	        
	    } else if (name != null && !name.isEmpty()) {
	        // Fetch student details by name, township, NRC, or DOB
	    	students = searchStudentByNameOrOtherFields(name);
	    } else {
	    	 students= getAllStudentExport();
	    }
	    
	    System.out.print("students-----------" + name);

	    // Create header row
	    Row headerRow = sheet.createRow(0);
	    headerRow.createCell(0).setCellValue("Student ID");
	    headerRow.createCell(1).setCellValue("Student Name");
	    headerRow.createCell(2).setCellValue("DOB");
	    headerRow.createCell(3).setCellValue("NRC");
	    headerRow.createCell(4).setCellValue("Township");
	    headerRow.createCell(5).setCellValue("Year");
	    headerRow.createCell(6).setCellValue("Mark1");
	    headerRow.createCell(7).setCellValue("Mark2");
	    headerRow.createCell(8).setCellValue("Total");

	    // Fill data rows
	    int rowNum = 1;
	    
	    for (StudentWithDetails student : students) {
	        Student studentHeader = student.getStudent();
	        List<Student1> details = student.getDetails();

	        // Iterate through details and create a new row for each detail
	        for (Student1 detail : details) {
	            Row row = sheet.createRow(rowNum++);
	            row.createCell(0).setCellValue(studentHeader.getStudentID());
	            row.createCell(1).setCellValue(studentHeader.getStudentName());
	            row.createCell(2).setCellValue(studentHeader.getDOB());
	            row.createCell(3).setCellValue(studentHeader.getNRC());
	            row.createCell(4).setCellValue(studentHeader.getTownship());
	            row.createCell(5).setCellValue(detail.getYear());
	            row.createCell(6).setCellValue(detail.getMark1());
	            row.createCell(7).setCellValue(detail.getMark2());
	            row.createCell(8).setCellValue(detail.getTotal());
	        }
	    }
	    // Write workbook to output stream
	    try {
	        workbook.write(outputStream);
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            workbook.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}

	
	public void importExcelData(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("No file uploaded");
        }

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming first sheet

            for (Row row : sheet) {
                // Skip header row
                if (row.getRowNum() == 0) {
                    continue;
                }

                // Extract data from the Excel row
                String studentName = row.getCell(0).getStringCellValue();
                String dob = row.getCell(1).getStringCellValue();
                String nrc = row.getCell(2).getStringCellValue();
                String phone = row.getCell(3).getStringCellValue();
                String email = row.getCell(4).getStringCellValue();
                String township = row.getCell(5).getStringCellValue();
                String state = row.getCell(6).getStringCellValue();
                String address = row.getCell(7).getStringCellValue();
               // String township = row.getCell(3).getStringCellValue();
                String year = row.getCell(8).getStringCellValue(); // Assuming Year is in the 5th column
                int mark1 = (int) row.getCell(9).getNumericCellValue(); // Assuming Mark1 is in the 6th column
                int mark2 = (int) row.getCell(10).getNumericCellValue(); // Assuming Mark2 is in the 7th column
                int total = mark1+ mark2;
                // Insert into studentheader table
                int studentID = insertIntoStudentHeader(studentName, dob, nrc,phone,email, township,state,address);

                // Insert into studentdetail table
                insertIntoStudentDetail(studentID, year, mark1, mark2,total);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to process the Excel file", e);
        }
    }
	
	
	private int insertIntoStudentHeader(String studentName, String dob, String nrc, String township,String email,String phone,String state,String address) {
	    String insertSql = "INSERT INTO studentheader (studentName, DOB, NRC, phone, email, township, state, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	    int studentID = -1;

	    try (Connection con = DBHelper.getInstance().getConnection();
	         PreparedStatement ps = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
	        ps.setString(1, studentName);
	        ps.setString(2, dob);
	        ps.setString(3, nrc);
	        ps.setString(4, phone);
	        ps.setString(5, email);
	        ps.setString(6, township);
	        ps.setString(7, state);
	        ps.setString(8, address);
	        int affectedRows = ps.executeUpdate();

	        if (affectedRows == 0) {
	            throw new SQLException("Creating studentheader failed, no rows affected.");
	        }

	        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                studentID = generatedKeys.getInt(1);
	            } else {
	                throw new SQLException("Creating studentheader failed, no ID obtained.");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return studentID;
	}

	private void insertIntoStudentDetail(int studentID, String year, int mark1, int mark2,int total) {
	    String insertSql = "INSERT INTO studentdetail (studentID, year, mark1, mark2,total) VALUES (?, ?, ?, ?, ?)";

	    try (Connection con = DBHelper.getInstance().getConnection();
	         PreparedStatement ps = con.prepareStatement(insertSql)) {
	        ps.setInt(1, studentID);
	        ps.setString(2, year);
	        ps.setInt(3, mark1);
	        ps.setInt(4, mark2);
	        ps.setInt(5, total);

	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


	
	public List<StudentWithDetails> getStudentWithDetails(int studentID) {
	    List<StudentWithDetails> studentsWithDetails = new ArrayList<>();
	    String query = "SELECT sh.studentID, sh.studentName, sh.DOB, sh.NRC, sh.township, " +
	                   "sd.year, sd.mark1, sd.mark2, (sd.mark1 + sd.mark2) AS total " +
	                   "FROM studentheader sh " +
	                   "INNER JOIN studentdetail sd ON sh.studentID = sd.studentID " +
	                   "WHERE sh.studentID = ? " +  // Specify the studentID
	                   "ORDER BY sh.studentID DESC";
	    ResultSet rs;

	    try (Connection con = DBHelper.getInstance().getConnection();
	         PreparedStatement ps = con.prepareStatement(query)) {

	        ps.setInt(1, studentID);
	        rs = ps.executeQuery();

	        Map<Integer, StudentWithDetails> studentMap = new HashMap<>();

	        while (rs.next()) {
	            int retrievedStudentID = rs.getInt("studentID");
	            StudentWithDetails studentWithDetails = studentMap.get(retrievedStudentID);

	            if (studentWithDetails == null) {
	                Student student = new Student();
	                student.setStudentID(retrievedStudentID);
	                student.setStudentName(rs.getString("studentName"));
	                student.setDOB(rs.getString("DOB"));
	                student.setNRC(rs.getString("NRC"));
	                student.setTownship(rs.getString("township"));
	                studentWithDetails = new StudentWithDetails(student, new ArrayList<>());
	                studentsWithDetails.add(studentWithDetails);
	                studentMap.put(retrievedStudentID, studentWithDetails);
	            }

	            // Create a new Student1 object for each row
	            Student1 detail = new Student1();
	            detail.setYear(rs.getString("year"));
	            detail.setMark1(rs.getInt("mark1"));
	            detail.setMark2(rs.getInt("mark2"));
	            detail.setTotal(rs.getInt("total"));

	            // Add detail to the student's list of details
	            studentWithDetails.getDetails().add(detail);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return studentsWithDetails;
	}


	public List<StudentWithDetails> getStudentWithField(String searchCriteria, String value) {
	    List<StudentWithDetails> studentsWithDetails = new ArrayList<>();
	    String query = "SELECT sh.studentID, sh.studentName, sh.DOB, sh.NRC, sh.township, " +
	                   "sd.year, sd.mark1, sd.mark2, (sd.mark1 + sd.mark2) AS total " +
	                   "FROM studentheader sh " +
	                   "INNER JOIN studentdetail sd ON sh.studentID = sd.studentID " +
	                   "WHERE ";

	    switch (searchCriteria) {
	    	case "studentName":
	    	    query += "sh.studentName LIKE ?";
	    	    break;
	        case "DOB":
	            query += "sh.DOB LIKE ?";
	            break;
	        case "NRC":
	            query += "sh.NRC LIKE ?";
	            break;
	        case "township":
	            query += "sh.township LIKE ?";
	            break;
	        
	        
	        default:
	            throw new IllegalArgumentException("Invalid search criteria: " + searchCriteria);
	    }
	    
	    

	    query += " ORDER BY sh.studentID DESC";
	    
	    
	    System.out.print("query---------" + query);

	    try (Connection con = DBHelper.getInstance().getConnection();
	         PreparedStatement ps = con.prepareStatement(query)) {

	        ps.setString(1, "%" + value + "%");
	        
	        
	       System.out.println("the value is ................"+value);
	        ResultSet rs = ps.executeQuery();

	        Map<Integer, StudentWithDetails> studentMap = new HashMap<>();

	        while (rs.next()) {
	            int retrievedStudentID = rs.getInt("studentID");
	            StudentWithDetails studentWithDetails = studentMap.get(retrievedStudentID);

	            if (studentWithDetails == null) {
	                Student student = new Student();
	                student.setStudentID(retrievedStudentID);
	                student.setStudentName(rs.getString("studentName"));
	                student.setDOB(rs.getString("DOB"));
	                student.setNRC(rs.getString("NRC"));
	                student.setTownship(rs.getString("township"));
	                studentWithDetails = new StudentWithDetails(student, new ArrayList<>());
	                studentsWithDetails.add(studentWithDetails);
	                studentMap.put(retrievedStudentID, studentWithDetails);
	            }

	            // Create a new Student1 object for each row
	            Student1 detail = new Student1();
	            detail.setYear(rs.getString("year"));
	            detail.setMark1(rs.getInt("mark1"));
	            detail.setMark2(rs.getInt("mark2"));
	            detail.setTotal(rs.getInt("total"));

	            // Add detail to the student's list of details
	            studentWithDetails.getDetails().add(detail);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    System.out.print("studentsWithDetails........."+studentsWithDetails);

	    return studentsWithDetails;
	}

	public List<StudentWithDetails> getAllStudentExport() {
	    List<StudentWithDetails> studentsWithDetails = new ArrayList<>();
	    String query = "SELECT sh.studentID, sh.studentName, sh.DOB, sh.NRC, sh.township, " +
	                   "sd.year, sd.mark1, sd.mark2, (sd.mark1 + sd.mark2) AS total " +
	                   "FROM studentheader sh " +
	                   "INNER JOIN studentdetail sd ON sh.studentID = sd.studentID " +
	                   "ORDER BY sh.studentID DESC";
	    ResultSet rs;

	    try (Connection con = DBHelper.getInstance().getConnection();
	         PreparedStatement ps = con.prepareStatement(query)) {

	        rs = ps.executeQuery();

	        Map<Integer, StudentWithDetails> studentMap = new HashMap<>();

	        while (rs.next()) {
	            int studentID = rs.getInt("studentID");
	            StudentWithDetails studentWithDetails = studentMap.get(studentID);

	            if (studentWithDetails == null) {
	                Student student = new Student();
	                student.setStudentID(studentID);
	                student.setStudentName(rs.getString("studentName"));
	                student.setDOB(rs.getString("DOB"));
	                student.setNRC(rs.getString("NRC"));
	                student.setTownship(rs.getString("township"));
	                studentWithDetails = new StudentWithDetails(student, new ArrayList<>());
	                studentsWithDetails.add(studentWithDetails);
	                studentMap.put(studentID, studentWithDetails);
	            }

	            Student1 detail = new Student1();
	            detail.setYear(rs.getString("year"));
	            detail.setMark1(rs.getInt("mark1"));
	            detail.setMark2(rs.getInt("mark2"));
	            detail.setTotal(rs.getInt("total"));

	            studentWithDetails.getDetails().add(detail);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return studentsWithDetails;
	}

    private List<StudentWithDetails> searchStudentByNameOrOtherFields(String name) {
        String[] fields = {"studentName", "DOB", "NRC", "township"};
        for (String field : fields) {
            List<StudentWithDetails> students = getStudentWithField(field, name);
            if (!students.isEmpty()) {
                return students;
            } // Replace with actual implementation
        }
        return getStudentWithField(name, name);
    }
}

