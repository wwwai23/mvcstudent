package app.models;
import java.util.List;

public class StudentWithDetails {
    private Student student;
    private List<Student1> details;
    private int studentID;
    private String DOB;
    private String studentName,NRC,phone,email,township,state,address;
    int ID,mark1,mark2,total;
	String year;

	
	
	
    public StudentWithDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

    
    
	public StudentWithDetails(int studentID, String dOB, String studentName, String nRC, String phone, String email,
			String township, String state, String address, int iD, int mark1, int mark2, int total, String year) {
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
		this.ID = iD;
		this.mark1 = mark1;
		this.mark2 = mark2;
		this.total = total;
		this.year = year;
	}


	public StudentWithDetails(Student student, List<Student1> details) {
        this.student = student;
        this.details = details;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Student1> getDetails() {
        return details;
    }

    public void setDetails(List<Student1> details) {
        this.details = details;
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



	public int getID() {
		return ID;
	}



	public void setID(int iD) {
		ID = iD;
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
