package com.thinking.machines.orm;
import com.thinking.machines.util.json.*;
import java.sql.*;

public class DataManager
{
private static DataManager dataManager=null;
private static Connection connection=null;
private static DatabaseMetaData databaseMetaData=null;

private DataManager()
{
// So that nobody makes multiple object of this class
}

private static Connection getConnection(ConfigurationFile configurationFile) throws Exception
{
Connection connection=null;
try
{
Class.forName(configurationFile.getJdbcDriver());
connection=DriverManager.getConnection(configurationFile.getConnectionUrl(),configurationFile.getUsername(),configurationFile.getPassword());
}catch(Exception exception)
{
throw exception;
}
return connection;
}

public static DataManager getDataManager()
{
if(dataManager!=null) return dataManager;
try
{
ConfigurationFile configurationFile=JsonOperation.loadConfigFile();
DataManager.connection=DataManager.getConnection(configurationFile);
DataManager.databaseMetaData=DataManager.connection.getMetaData();
DataManager.dataManager=new DataManager();
}catch(Exception exception)
{
System.out.println(exception);
}
return DataManager.dataManager;
} // method braces close

public void begin()
{
// not yet decided
}
} // class braces close
