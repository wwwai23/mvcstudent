package app.controllers;
import org.springframework.web.bind.annotation.ResponseBody;
import java.sql.Connection;
import java.sql.Statement;
import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import org.apache.poi.ss.usermodel.Workbook;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.apache.poi.ss.usermodel.Sheet;
import app.daos.PaginationDao;
import app.daos.StudentDao;
import app.helper.DBHelper;
import app.models.Student;
import app.models.Student1;
import app.models.StudentWithDetails;

import org.apache.poi.ss.usermodel.*;


@Controller
@MultipartConfig
public class HomeController {
    
	@Autowired
	StudentDao st;

	@Autowired
	PaginationDao pt;

	

	@GetMapping("/display1")
	public String getPage1() {

		return "student";
	}


    @PostMapping("/importExcel")
    public String importExcel(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("No file uploaded");
        }

        try {
        	System.out.print("file" + file);
            importExcelData(file);
            return "redirect:/list1"; // Redirect to a success page or list view
        } catch (Exception e ) {
            e.printStackTrace();
            throw new RuntimeException("Failed to process the Excel file", e);
        }
    }
    //br phit nay tr ll

    
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
                String studentName = getCellValueAsString(row.getCell(0));
                String dob = getCellValueAsString(row.getCell(1));
                String nrc = getCellValueAsString(row.getCell(2));
                String phone = "0"+getCellValueAsString(row.getCell(3));
                // ak lo pyw tr lrr ?
                String email = getCellValueAsString(row.getCell(4));
                String township = getCellValueAsString(row.getCell(5));
                String state = getCellValueAsString(row.getCell(6));
                String address = getCellValueAsString(row.getCell(7));
                String year = getCellValueAsString(row.getCell(8)); // Assuming Year is in the 9th column
                int mark1 = getCellValueAsInt(row.getCell(9)); // Assuming Mark1 is in the 10th column
                int mark2 = getCellValueAsInt(row.getCell(10)); // Assuming Mark2 is in the 11th column
                int total = mark1 + mark2;

                // Insert into studentheader table
                int studentID = insertIntoStudentHeader(studentName, dob, nrc, phone, email, township, state, address);

                // Insert into studentdetail table
                insertIntoStudentDetail(studentID, year, mark1, mark2, total);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to process the Excel file", e);
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    // Handle date format if needed
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    return dateFormat.format(cell.getDateCellValue());
                } else {
                    // For phone numbers and other numeric values that should be strings
                	 return new BigDecimal(cell.getNumericCellValue()).toPlainString();
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    private int getCellValueAsInt(Cell cell) {
        if (cell == null) {
            return 0;
        }

        switch (cell.getCellType()) {
            case NUMERIC:
                return (int) cell.getNumericCellValue();
            case STRING:
                try {
                    return Integer.parseInt(cell.getStringCellValue());
                } catch (NumberFormatException e) {
                    return 0;
                }
            default:
                return 0;
        }
    }

    private int insertIntoStudentHeader(String studentName, String dob, String nrc, String phone, String email, String township, String state, String address) {
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

    private void insertIntoStudentDetail(int studentID, String year, int mark1, int mark2, int total) {
        String insertSql = "INSERT INTO studentdetail (studentID, year, mark1, mark2, total) VALUES (?, ?, ?, ?, ?)";

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


	// ********** controller for export
	@PostMapping("/exportExcel")
	protected void exportExcel(@RequestParam(required = false) Integer Id,
			@RequestParam(required = false) String search, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=students.xlsx");

		StudentDao studentDao = new StudentDao();

		try (OutputStream outputStream = response.getOutputStream()) {
			studentDao.exportStudentsToExcel(search, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    private List<StudentWithDetails> getStudentWithDetails(int id) {
        // Implementation
        return st.getStudentWithDetails(id); // Dummy return for illustration
    }

    private List<StudentWithDetails> getStudentWithField(String field, String value) {
        // Implementation
        return st.getStudentWithField(field, value); // Dummy return for illustration
    }

    private List<StudentWithDetails> getAllStudentExport() {
        // Implementation
        return st.getAllStudentExport(); // Dummy return for illustration
    }

	@PostMapping("/c")
	public String studentGet(@RequestParam String dob, @RequestParam String studentName, @RequestParam String nrc,
			@RequestParam String phone, @RequestParam String email, @RequestParam String township,
			@RequestParam String state, @RequestParam String address, @RequestParam String[] year,
			@RequestParam int[] mark1, @RequestParam int[] mark2,@RequestParam int[] total) {

		Student std = new Student(dob, studentName, nrc, phone, email, township, state, address);
		int generatedStudentID = st.createStudent(std);

		if (generatedStudentID > 0) {
			boolean anyDetailInserted = false;

			for (int i = 0; i < year.length; i++) {
				//int total = mark1[i] + mark2[i];
				Student1 st1 = new Student1();
				st1.setStudentID(generatedStudentID);
				st1 = new Student1(generatedStudentID, mark1[i], mark2[i], total[i], year[i]);
				System.out.print("st1 " + st1 + "------------------");
				int condition1 = st.insertDetail(st1);
				System.out.println("condition1-----------" + condition1);
				if (condition1 == 1) {
					anyDetailInserted = true;
				}
			}

			if (anyDetailInserted) {

				return "redirect:/list1";

			}
		}

		return "redirect:/detail?studentID=" + generatedStudentID;
	}

	@PostMapping("/cs")
	public String studentSave(@RequestParam int studentID, @RequestParam String dob, @RequestParam String studentName,
			@RequestParam String nrc, @RequestParam String phone, @RequestParam String email,
			@RequestParam String township, @RequestParam String state, @RequestParam String address,
			@RequestParam String[] year, @RequestParam int[] mark1, @RequestParam int[] mark2) {

		Student std = new Student(studentID, dob, studentName, nrc, phone, email, township, state, address);
		int condition = st.createStudent(std);
		boolean anyDetailInserted = false;

		for (int i = 0; i < year.length; i++) {
			int total = mark1[i] + mark2[i];
			Student1 st1 = new Student1(studentID, mark1[i], mark2[i], total, year[i]);
			int condition1 = st.createDetail(st1);
			if (condition1 == 1) {
				anyDetailInserted = true;
			}
		}

		if (condition == 1 || anyDetailInserted) {
			return "redirect:/detail?studentID=" + studentID;
		} else {
			return "redirect:/detail?studentID=" + studentID;
		}
	}

	@PostMapping("/delete/{id}")
	public String stuDeleteGet(@PathVariable int id) {
		int condition = st.deleteStudent(id);
		if (condition == 1) {
			return "redirect:/list1";
		} else {
			return "error";
		}
	}

//	
//	@PostMapping("/update/{id}")
//	public String studentUpdate(@RequestParam int studentID, @RequestParam String dob, @RequestParam String studentName,
//	        @RequestParam String nrc, @RequestParam String phone, @RequestParam String email,
//	        @RequestParam String township, @RequestParam String state, @RequestParam String address, @RequestParam int[] detailID,
//	        @RequestParam String[] year, @RequestParam int[] mark1, @RequestParam int[] mark2) {
//
//	    Student std = new Student(studentID, dob, studentName, nrc, phone, email, township, state, address);
//	    int condition = st.updateStudent(std);
//	    int condition1 = 1;
//	   
//	    for (int i = 0; i < detailID.length; i++) {
//	    	 System.out.print("detailID------------------" + detailID[i]);
//	        int total = mark1[i] + mark2[i];
//	        Student1 st1 = new Student1(detailID[i], mark1[i], mark2[i], year[i], total);
//	        condition1 = st.updateDetail(st1);
//	        
//	    }
//
//	    if (condition == 1 || condition1 != 0) {
//	        return "redirect:/detail?studentID=" + studentID;
//	    } else {
//	        return "error";
//	    }
//	}

	@PostMapping("/update/{id}")
	public String studentUpdate(@RequestParam int studentID, @RequestParam String dob, @RequestParam String studentName,
			@RequestParam String nrc, @RequestParam String phone, @RequestParam String email,
			@RequestParam String township, @RequestParam String state, @RequestParam String address,
			@RequestParam int[] detailID, @RequestParam String[] year, @RequestParam int[] mark1,
			@RequestParam int[] mark2, @RequestParam(required = false) int[] deleteDetailID, // Add this to handle
																					// deletions
			Model model) {

		// Check if arrays are non-null and have consistent lengths
		if (detailID == null || year == null || mark1 == null || mark2 == null || detailID.length != year.length
				|| year.length != mark1.length || mark1.length != mark2.length) {
			model.addAttribute("error", "Invalid input data. Please check your form.");
			System.out.print("Invalid input data. Please check your form.");
			return "error"; // Replace with your actual error view
		}

		Student std = new Student(studentID, dob, studentName, nrc, phone, email, township, state, address);
		int condition = st.updateStudent(std); // Assuming updateStudent handles both insert and update

		int condition1 = 1;
		List<Integer> existingDetailIDs = new ArrayList<>();

		// Handle updates and insertions
		for (int i = 0; i < year.length; i++) {
			int total = mark1[i] + mark2[i];
			Student1 st1 = new Student1(studentID, mark1[i], mark2[i],total,year[i]);
			st1.setStudentID(studentID);
			if (detailID[i] == 0) {
				// New record, insert
				condition1 = st.insertDetail(st1);
			} else {
				// Existing record, update
				boolean exists = st.detailExists(detailID[i]);
				if (exists) {
					st1.setID(detailID[i]); // Ensure the ID is set for updating
					condition1 = st.updateDetail(st1);
				} else {
					condition1 = st.insertDetail(st1);
				}
				existingDetailIDs.add(detailID[i]);
			}
		}

		// Handle deletions
		if (deleteDetailID != null) {
			for (int id : deleteDetailID) {
				st.deleteDetail(id);
			}
		}

		if (condition == 1 || condition1 != 0) {
			return "redirect:/list1";
		} else {
			model.addAttribute("error", "Failed to update student details.");
			return "error"; // Replace with your actual error view
		}
	}

//	
//	@PostMapping("/update/{id}")
//	public String studentUpdate(
//	        @RequestParam int studentID,
//	        @RequestParam String dob,
//	        @RequestParam String studentName,
//	        @RequestParam String nrc,
//	        @RequestParam String phone,
//	        @RequestParam String email,
//	        @RequestParam String township,
//	        @RequestParam String state,
//	        @RequestParam String address,
//	        @RequestParam int[] detailID,
//	        @RequestParam String[] year,
//	        @RequestParam int[] mark1,
//	        @RequestParam int[] mark2,
//	        Model model) {
//
//	    // Check if arrays are non-null and have consistent lengths
//	    if (detailID == null || year == null || mark1 == null || mark2 == null 
//	        || detailID.length != year.length 
//	        || year.length != mark1.length 
//	        || mark1.length != mark2.length) 
//	    {
//	        model.addAttribute("error", "Invalid input data. Please check your form.");
//	        System.out.print( "Invalid input data. Please check your form.");
//	        System.out.print("detailID" + detailID);
//	        return "error"; // Replace with your actual error view
//	    } 
//	   
//
//	    Student std = new Student(studentID, dob, studentName, nrc, phone, email, township, state, address);
//	  
//	    int condition = st.updateStudent(std);  // Assuming updateStudent handles both insert and update
//
//	    int condition1 = 1;
//	    List<Integer> existingDetailIDs = new ArrayList<>();
//
//	    // Then, handle updates and insertions
//	    for (int i = 0; i < year.length; i++) {
//	        System.out.print("detail id for update" + detailID[i]);
//
//	        int total = mark1[i] + mark2[i];
//	        Student1 st1 = new Student1(studentID, mark1[i], mark2[i], year[i], total);
//
//	        if (detailID[i] == 0) {
//	            // New record, insert
//	            System.out.print("new record----------");
//	            System.out.print("studentID for new" + studentID);
//	            st1.setStudentID(studentID);
//	            condition1 = st.insertDetail(st1);
//	        } else {
//	            // Existing record, update
//	            boolean exists = st.detailExists(detailID[i]);
//	            if (exists) {
//	                condition1 = st.updateDetail(st1);
//	            } else {
//	                // Handle the case where the detailID is given but does not exist in the database
//	                condition1 = st.insertDetail(st1);
//	            }
//	            existingDetailIDs.add(detailID[i]);
//	        }
//	    }
//
//	    // Delete details not included in the current request
////	    for (Student1 detail : st.getDetail(studentID)) {
////	        if (!existingDetailIDs.contains(detail.getID())) {
////	            st.deleteDetail(detail.getID());
////	        }
////	    }
//
//	    if (condition == 1 || condition1 != 0) {
//	        return "redirect:/detail?studentID=" + studentID;
//	    } else {
//	        model.addAttribute("error", "Failed to update student details.");
//	        return "error"; // Replace with your actual error view
//	    }
//	}
//
//	

	@GetMapping("/list1")
	public String listStudents(@RequestParam(required = false) String search, Model model, HttpSession session,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {
		Integer studentID = null;
		try {
			studentID = Integer.parseInt(search);
		} catch (NumberFormatException e) {
			// Ignore the exception; it means the input is not a valid integer
		}
		if (studentID != null) {
			// Perform search by ID
			List<Student> student = pt.getStudentByID(studentID, page, pageSize);
			int totalStudents = pt.getStudentByIDCount(studentID);
			int totalPages = (int) Math.ceil((double) totalStudents / pageSize);
			model.addAttribute("students", student);
			model.addAttribute("currentPage", page);
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("pageSize", pageSize);

		} else if (search != null && !search.isEmpty()) {
			// Perform search by Name
			List<Student> student = pt.searchByNameOrTownship(search, page, pageSize);
			int totalStudents = pt.searchByNameOrTownshipCount(search);
			int totalPages = (int) Math.ceil((double) totalStudents / pageSize);
			model.addAttribute("students", student);
			model.addAttribute("currentPage", page);
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("pageSize", pageSize);
		}

		else {
			List<Student> students = pt.getStudents(page, pageSize);
			int totalStudents = pt.getStudentCount();
			int totalPages = (int) Math.ceil((double) totalStudents / pageSize);

			model.addAttribute("students", students);
			model.addAttribute("currentPage", page);
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("pageSize", pageSize);
		}
		return "list"; // Return the view to display search results
	}

	@GetMapping("/retrieveById")
	public String retrieveGet(@RequestParam int studentID, Model model, HttpSession session) {
		List<Student> student = st.getStudentByID(studentID);
		session.setAttribute("student", student);
		return "detail";
	}

	@GetMapping("/detail")
	public String detail(@RequestParam int studentID, Model model, HttpSession session) {
		List<Student1> studentDetails = st.getDetail(studentID);
		session.setAttribute("studentDetails", studentDetails);

		if (studentDetails.size() != 0) {
			return "detail";
		} else
			return "error";
	}
}
