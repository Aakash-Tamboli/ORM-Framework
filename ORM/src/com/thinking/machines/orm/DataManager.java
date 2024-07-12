package com.thinking.machines.orm;
import com.thinking.machines.util.json.*;
import com.thinking.machines.util.table.*;
import com.thinking.machines.orm.exception.*;
import com.thinking.machines.orm.annotation.*;
import java.sql.*;
import java.io.*;
import java.util.*;


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

public void begin()
{
// not yet decided
}

public void generateDTOClasses(String directoryName) throws DataException
{
if(directoryName!=null) directoryName=directoryName.trim();
try
{
com.thinking.machines.util.table.Table.convertTablesIntoClasses(databaseMetaData,directoryName);
}catch(DataException dataException)
{
throw dataException;
}
}

public void generateDTOClasses() throws DataException
{
generateDTOClasses(null);
}

public int save(Object object) throws DataException
{
Class c=object.getClass();
System.out.println("Class Name: "+c.getSimpleName());
System.out.println("Class has @Table Annotation Applied: "+c.isAnnotationPresent(com.thinking.machines.orm.annotation.Table.class));
// get value against name
// compare name with DS tables name
// assume it is a valid table
/*
Phase-1 Validating object & class
if(class doesn't have Table annotation) then throw exception
if(Table annotation value is not valid) then throw exception
Traverse on class props/fields
start loop
- get each field
- if(field doesn't have Column annotation) then throw exception
- if(Column annotation props is not valid ) then throw exception
- if(field has foreign key annotation and that foreign key is not exists on that table) then throw exception
end loop
Phase-2 Adding object/record into table
*/

return -1;
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

} // class braces close
