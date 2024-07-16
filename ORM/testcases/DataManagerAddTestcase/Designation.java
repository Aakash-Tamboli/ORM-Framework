import com.thinking.machines.orm.annotation.*;

@Table(name="designation")
public class Designation
{
@PrimaryKey
@AutoIncrement
@Column(name="code")
public int code;
@Column(name="title")
public String title;
}