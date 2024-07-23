import com.thinking.machines.orm.*;

public class DataManagerConnectivityTest
{
public static void main(String []args)
{
// DataManager obj=new DataManager(); // won't compile
DataManager dataManager1=DataManager.getDataManager();
DataManager dataManager2=DataManager.getDataManager();
if(dataManager1==dataManager2) System.out.println("Both reffering to same Object");

// self reff.
if(dataManager1!=null)
{
// always check nullness of DataManager object otherwise null exception will be occured
}


}
}
