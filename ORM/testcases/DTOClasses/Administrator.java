import com.thinking.machines.orm.annotation.*;

@Table(name="administrator")
class Administrator
{
@PrimaryKey
@Column(name="uname")
public String uname;
@Column(name="pwd")
public String pwd;
}