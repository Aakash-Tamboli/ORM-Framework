import com.thinking.machines.orm.annotation.*;

@Table(name="course")
public class Course
{
@PrimaryKey
@AutoIncrement
@Column(name="code")
public int code;
@Column(name="title")
public String title;
}