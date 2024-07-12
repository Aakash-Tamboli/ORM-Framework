import com.thinking.machines.orm.*;
import com.thinking.machines.orm.exception.*;

public class DataManagerAdd
{
public static void main(String []args)
{
DataManager dm=DataManager.getDataManager();
try
{
dm.begin();
Course c=new Course();
c.title="JAVA";
int code=dm.save(c);
// dm.end();
}catch(DataException dataException)
{
System.out.println(dataException);
}
}
}
