import com.thinking.machines.orm.annotation.*;

@Table(name="student")
class Student
{
@PrimaryKey
@Column(name="roll_number")
public int rollNumber;
@Column(name="first_name")
public String firstName;
@Column(name="last_name")
public String lastName;
@Column(name="aadhar_card_number")
public String aadharCardNumber;
@Column(name="gender")
public char gender;
@Column(name="data_of_birth")
public java.sql.Date dataOfBirth;
@ForeignKey(parent="course",column="code")
@Column(name="course_code")
public int courseCode;
}