package app.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import app.helper.DBHelper;

public class ExcelImportService {

	public void importExcelData(MultipartFile file) {
		// TODO Auto-generated method stub
		
		
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
		            String township = row.getCell(3).getStringCellValue();
		            String year = row.getCell(4).getStringCellValue(); // Assuming Year is in the 5th column
		            int mark1 = (int) row.getCell(5).getNumericCellValue(); // Assuming Mark1 is in the 6th column
		            int mark2 = (int) row.getCell(6).getNumericCellValue(); // Assuming Mark2 is in the 7th column

		            // Insert into studentheader table
		            int studentID = insertIntoStudentHeader(studentName, dob, nrc, township);

		            // Insert into studentdetail table
		            insertIntoStudentDetail(studentID, year, mark1, mark2);
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}

		private int insertIntoStudentHeader(String studentName, String dob, String nrc, String township) {
		    String insertSql = "INSERT INTO studentheader (studentName, DOB, NRC, township) VALUES (?, ?, ?, ?)";
		    int studentID = -1;

		    try (Connection con = DBHelper.getInstance().getConnection();
		         PreparedStatement ps = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
		        ps.setString(1, studentName);
		        ps.setString(2, dob);
		        ps.setString(3, nrc);
		        ps.setString(4, township);

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

		private void insertIntoStudentDetail(int studentID, String year, int mark1, int mark2) {
		    String insertSql = "INSERT INTO studentdetail (studentID, year, mark1, mark2) VALUES (?, ?, ?, ?)";

		    try (Connection con = DBHelper.getInstance().getConnection();
		         PreparedStatement ps = con.prepareStatement(insertSql)) {
		        ps.setInt(1, studentID);
		        ps.setString(2, year);
		        ps.setInt(3, mark1);
		        ps.setInt(4, mark2);

		        ps.executeUpdate();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}


		
	}


