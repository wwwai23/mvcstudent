package app.daos;

import app.helper.DBHelper;
import app.models.Student;
import app.models.Student1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
@Repository
public class PaginationDao {

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
                Student student = new Student(
                    rs.getInt("studentID"),
                    rs.getString("DOB"),
                    rs.getString("studentName"),
                    rs.getString("NRC"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("township"),
                    rs.getString("state"),
                    rs.getString("address")
                );
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    public int getStudentCount() {
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

    public List<Student> searchByNameOrTownship(String searchValue, int page, int pageSize) {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM studentheader WHERE studentName LIKE ? OR township LIKE ? OR DOB LIKE ? OR NRC LIKE ? LIMIT ? OFFSET ?";

        try {
        	Connection con = DBHelper.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, "%" + searchValue + "%");
            ps.setString(2, "%" + searchValue + "%");
            ps.setString(3, "%" + searchValue + "%");
            ps.setString(4, "%" + searchValue + "%");
            ps.setInt(5, pageSize);
            ps.setInt(6, (page - 1) * pageSize);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Student student = new Student(
                    rs.getInt("studentID"),
                    rs.getString("DOB"),
                    rs.getString("studentName"),
                    rs.getString("NRC"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("township"),
                    rs.getString("state"),
                    rs.getString("address")
                );
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    public int searchByNameOrTownshipCount(String searchValue) {
        String query = "SELECT COUNT(*) FROM studentheader WHERE studentName LIKE ? OR township LIKE ? OR DOB LIKE ? OR NRC LIKE ?";
        int count = 0;

        try {
        	Connection con = DBHelper.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, "%" + searchValue + "%");
            ps.setString(2, "%" + searchValue + "%");
            ps.setString(3, "%" + searchValue + "%");
            ps.setString(4, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public List<Student> getStudentByID(int studentID, int page, int pageSize) {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM studentheader WHERE studentID = ? LIMIT ? OFFSET ?";

        try {
        	Connection con = DBHelper.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, studentID);
            ps.setInt(2, pageSize);
            ps.setInt(3, (page - 1) * pageSize);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Student student = new Student(
                    rs.getInt("studentID"),
                    rs.getString("DOB"),
                    rs.getString("studentName"),
                    rs.getString("NRC"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("township"),
                    rs.getString("state"),
                    rs.getString("address")
                );
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    public int getStudentByIDCount(int studentID) {
        String query = "SELECT COUNT(*) FROM studentheader WHERE studentID = ?";
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
}
