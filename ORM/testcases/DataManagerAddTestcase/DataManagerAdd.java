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
Student student=new Student();
student.firstName="Gautam";
student.lastName="Upadhaya";
student.aadharCardNumber="GAUTAM_AADHAR";
student.gender='M';
student.dataOfBirth=java.sql.Date.valueOf("2000-04-1");
student.courseCode=3;
int code=dm.save(student);
// dm.end();
}catch(DataException dataException)
{
System.out.println(dataException.getMessage());
}
}
}

