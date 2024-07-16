import com.thinking.machines.orm.annotation.*;

@Table(name="administrator")
public class Administrator
{
@PrimaryKey
@Column(name="uname")
public String uname;
@Column(name="pwd")
public String pwd;
}