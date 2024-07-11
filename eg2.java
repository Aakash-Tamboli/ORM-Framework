class DataManager
{
private static DataManager dataManager=null;
private static Connection connection=null;
private static DatabaseMetaData databaseMetaData=null;


private DataManager()
{
// So that nobody makes multiple object of this class
}

private static Connection getConnection(ConfigurationFile configurationFile)
{
Connection connection=null;
try
{
Class.forName(configurationFile.getJdbcDriver());
connection=DriverManager.getConnection(configurationFile.getConnectionUrl(),configurationFile.getUsername(),configurationFile.getPassword());
}catch(Exception exception)
{
System.out.println("Exception found [getConnection]:"+exception.getMessage());
}
return connection;
}


public static DataManager getDataManager()
{
if(dataManager!=null) return dataManager;
File file=new File("config.json");
ConfigurationFile configurationFile=JsonOperation.loadConfigFile();
DataManager.connection=DataManager.getConnection(configurationFile);
DataManager.databaseMetaData=DataManager.connection.getMetaData();
} // method braces close





} // class braces close
