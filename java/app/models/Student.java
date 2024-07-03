package app.models;

import java.sql.Date;
import java.time.LocalDate;

public class Student {
     private int studentID;
     private String DOB;
     private String studentName,NRC,phone,email,township,state,address;
	public Student() {
		super();
		
	}
	
	
	public Student(int studentID, String dOB, String studentName, String nRC, String township) {
		super();
		this.studentID = studentID;
		this.DOB = dOB;
		this.studentName = studentName;
		this.NRC = nRC;
		this.township = township;
	}

	

	public Student(String dOB, String studentName, String nRC, String phone, String email, String township,
			String state, String address) {
		super();
		this.DOB = dOB;
		this.studentName = studentName;
		this.NRC = nRC;
		this.phone = phone;
		this.email = email;
		this.township = township;
		this.state = state;
		this.address = address;
	}


	public Student(int studentID, String dOB, String studentName, String nRC, String phone, String email,
			String township, String state, String address) {
		super();
		this.studentID = studentID;
		this.DOB = dOB;
		this.studentName = studentName;
		this.NRC = nRC;
		this.phone = phone;
		this.email = email;
		this.township = township;
		this.state = state;
		this.address = address;
	}
	public int getStudentID() {
		return studentID;
	}
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getNRC() {
		return NRC;
	}
	public void setNRC(String nRC) {
		NRC = nRC;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTownship() {
		return township;
	}
	public void setTownship(String township) {
		this.township = township;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
