package app.models;

public class Student1{
	
	int ID,studentID,mark1,mark2,total;
	String year;
	
	
	public Student1() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public Student1(int studentID, int mark1, int mark2, int total, String year) {
		super();
		this.studentID = studentID;
		this.mark1 = mark1;
		this.mark2 = mark2;
		this.total = total;
		this.year = year;
	}



	public Student1(int iD, int mark1, int mark2,String year, int total) {
		super();
		this.ID = iD;
		this.mark1 = mark1;
		this.mark2 = mark2;
		this.total = total;
		this.year = year;
	}



	public Student1(int mark1, int mark2, int total, String year) {
		super();
		this.mark1 = mark1;
		this.mark2 = mark2;
		this.total = total;
		this.year = year;
	}



	public Student1(int iD, int studentID, int mark1, int mark2, int total, String year) {
		super();
		this.ID = iD;
		this.studentID = studentID;
		this.mark1 = mark1;
		this.mark2 = mark2;
		this.total = total;
		this.year = year;
	}

	public int getID() {
		return ID;
	}


	public void setID(int iD) {
		this.ID = iD;
	}


	public int getStudentID() {
		System.out.print("student dao" + studentID);
		return studentID;
	}


	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}


	public int getMark1() {
		return mark1;
	}


	public void setMark1(int mark1) {
		this.mark1 = mark1;
	}


	public int getMark2() {
		return mark2;
	}


	public void setMark2(int mark2) {
		this.mark2 = mark2;
	}


	public int getTotal() {
		return total;
	}


	public void setTotal(int total) {
		this.total = total;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}

}