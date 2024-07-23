import com.thinking.machines.orm.*;
import com.thinking.machines.orm.exception.*;
import com.thinking.machines.orm.annotation.*;

public class DataManagerGeneratePOJOClasses
{
public static void main(String []args)
{
DataManager dm=DataManager.getDataManager();
try
{
dm.generateClasses();
}catch(DataException de)
{
System.out.println(de.getMessage());
}
}
}
