package com.thinking.machines.orm;
import com.thinking.machines.orm.util.json.*;
import com.thinking.machines.orm.util.table.*;
import com.thinking.machines.orm.util.column.*;
import com.thinking.machines.orm.util.validator.*;
import com.thinking.machines.orm.util.sql.*;
import com.thinking.machines.orm.exception.*;
import com.thinking.machines.orm.annotation.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;


public class DataManager
{
private static DataManager dataManager=null;
private static Connection connection=null;
private static DatabaseMetaData databaseMetaData=null;

// brute force private List<String> listOfTables=null;

private Set<String> setOfTables=null;
private Map<String,Set<String>> mapOfTablesWithColumns=null;

private DataManager()
{
this.setOfTables=null;
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
com.thinking.machines.orm.util.table.Table.convertTablesIntoClasses(databaseMetaData,directoryName);
}catch(DataException dataException)
{
throw dataException;
}
}

public void generateDTOClasses() throws DataException
{
generateDTOClasses(null);
}

private boolean isTableExists(String tableName)
{
for(String tbName: dataManager.setOfTables)
{
if(tableName.equals(tbName)) return true;
}
return false; 
}

public int save(Object object) throws DataException
{
Class c=object.getClass();
String tableName=null;
com.thinking.machines.orm.annotation.Table tableAnnotation=null;
com.thinking.machines.orm.annotation.Column columnAnnotation=null;
Field []fields=null;
String columnNameOnField=null;
Set<String> setOfColumns=null;
boolean isPrimaryKey=false;

ColumnDataWithAdditionalInformation columnDataWithAdditionalInformation=null;
List<ColumnDataWithAdditionalInformation> listOfColumnsDataWithAdditionalInformation=null;
Object value=null;

if(c.isAnnotationPresent(com.thinking.machines.orm.annotation.Table.class)==false)
{
throw new DataException("@Table Annotation is applied on class");
}
tableAnnotation=(com.thinking.machines.orm.annotation.Table)c.getAnnotation(com.thinking.machines.orm.annotation.Table.class);
tableName=tableAnnotation.name();
if(!isTableExists(tableName))
{
throw new DataException(tableName+" not exitst in database");
}

setOfColumns=mapOfTablesWithColumns.get(tableName);

fields=c.getFields();

listOfColumnsDataWithAdditionalInformation=new ArrayList<>();

for(Field field: fields)
{
// reseting varaibles for next cycle.
isPrimaryKey=false;
if(!field.isAnnotationPresent(com.thinking.machines.orm.annotation.Column.class)) throw new DataException("@Column annotation is not applied on "+field.getName()+" props of class "+c.getSimpleName());
columnAnnotation=(com.thinking.machines.orm.annotation.Column)field.getAnnotation(com.thinking.machines.orm.annotation.Column.class);
columnNameOnField=columnAnnotation.name();
if(!setOfColumns.contains(columnNameOnField)) throw new DataException(columnNameOnField+" not exists in "+tableName+" table as column");
// foreign key wala jahan jhart baad me dekh te hai
if(field.isAnnotationPresent(com.thinking.machines.orm.annotation.PrimaryKey.class))
{
isPrimaryKey=true;
}

try
{
value=field.get(object);
if(!Validator.isValidType(value)) throw new DataException(field.getName()+" data-type is invalid");
columnDataWithAdditionalInformation=new ColumnDataWithAdditionalInformation();
columnDataWithAdditionalInformation.setColumnName(columnNameOnField);
columnDataWithAdditionalInformation.setColumnData(value);
columnDataWithAdditionalInformation.setDataType(Validator.whatTypeOf(value));
columnDataWithAdditionalInformation.setIsPrimaryKey(isPrimaryKey);
}catch(IllegalAccessException illegalAccessException) // checked exception
{
throw new DataException(illegalAccessException.getMessage());
}
listOfColumnsDataWithAdditionalInformation.add(columnDataWithAdditionalInformation);
}

FireSQL.insert(connection,tableName,listOfColumnsDataWithAdditionalInformation);


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
dataManager.setOfTables=com.thinking.machines.orm.util.table.Table.getAllTables(databaseMetaData);
dataManager.mapOfTablesWithColumns=com.thinking.machines.orm.util.column.Column.getMapOfTablesWithColumns(databaseMetaData,dataManager.setOfTables);
}catch(Exception exception)
{
System.out.println(exception);
}
return DataManager.dataManager;
} // method braces close

} // class braces close
