import com.thinking.machines.orm.*;
import com.thinking.machines.orm.exception.*;
import com.thinking.machines.orm.annotation.*;

public class DataManagerGenerateDTOClasses
{
public static void main(String []args)
{
DataManager dm=DataManager.getDataManager();
try
{
dm.generateDTOClasses(); // it will generate all classes at same directory
// dm.generateDTOClasses("DTOClasses"); // for generating DTO Classes inside DTOClasses, it present in current folder teseted
// dm.generateDTOClasses("/home/aakash/DTOClasses/");
}catch(DataException de)
{
System.out.println(de.getMessage());
}
}
}
