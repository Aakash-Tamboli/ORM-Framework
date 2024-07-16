import com.thinking.machines.orm.annotation.*;

@Table(name="employee")
public class Employee
{
@PrimaryKey
@AutoIncrement
@Column(name="id")
public int id;
@Column(name="name")
public String name;
@ForeignKey(parent="designation",column="code")
@Column(name="designation_code")
public int designationCode;
@Column(name="date_of_birth")
public java.sql.Date dateOfBirth;
@Column(name="gender")
public char gender;
@Column(name="is_indian")
public byte isIndian;
@Column(name="basic_salary")
public java.math.BigDecimal basicSalary;
@Column(name="pan_number")
public String panNumber;
@Column(name="aadhar_card_number")
public String aadharCardNumber;
}