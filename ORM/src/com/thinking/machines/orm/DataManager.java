package com.thinking.machines.orm;
import com.thinking.machines.orm.util.json.*;
import com.thinking.machines.orm.util.table.*;
import com.thinking.machines.orm.util.column.*;
import com.thinking.machines.orm.util.validator.*;
import com.thinking.machines.orm.util.sql.*;
import com.thinking.machines.orm.util.fieldwrapper.*;
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
int result=-1;
Class c=object.getClass();
String tableName=null;
com.thinking.machines.orm.annotation.Table tableAnnotation=null;
com.thinking.machines.orm.annotation.Column columnAnnotation=null;
ForeignKey foreignKeyAnnotation=null;
String foreignTableName=null;
String foreignTableColumnName=null;
Class dataType=null;
Field []fields=null;
String columnName=null;
Set<String> setOfColumns=null;
boolean isPrimaryKey=false;
boolean isPKSetByUser=false;
boolean isAutoIncremented=false;
boolean isTableContainsAutoIncrementedProperty=false;


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
// reseting varaibles for next cycle for good readability
columnAnnotation=null;
columnName=null;
value=null;
isPrimaryKey=false;
isPKSetByUser=false;
isAutoIncremented=false;
foreignKeyAnnotation=null;
foreignTableName=null;
foreignTableColumnName=null;
dataType=null;

// analysis phase starts 

if(!field.isAnnotationPresent(com.thinking.machines.orm.annotation.Column.class)) throw new DataException("@Column annotation is not applied on "+field.getName()+" props of class "+c.getSimpleName());
columnAnnotation=(com.thinking.machines.orm.annotation.Column)field.getAnnotation(com.thinking.machines.orm.annotation.Column.class);
columnName=columnAnnotation.name();
if(!setOfColumns.contains(columnName)) throw new DataException(columnName+" not exists in "+tableName+" table as column");
value=FieldWrapper.get(field,object);
if(!Validator.isValidType(value)) throw new DataException(field.getName()+" data-type is invalid");
dataType=Validator.whatTypeOf(value);
if(field.isAnnotationPresent(com.thinking.machines.orm.annotation.PrimaryKey.class))
{
isPrimaryKey=true;
if(field.isAnnotationPresent(AutoIncrement.class))
{
isAutoIncremented=true;
isTableContainsAutoIncrementedProperty=true;
isPKSetByUser=false;
}
else
{
isAutoIncremented=false;
isPKSetByUser=true;
}
}
if(field.isAnnotationPresent(ForeignKey.class))
{
foreignKeyAnnotation=(ForeignKey)field.getAnnotation(ForeignKey.class);
foreignTableName=foreignKeyAnnotation.parent();
foreignTableColumnName=foreignKeyAnnotation.column();
}



// analysis phase ends


// validation phase
if(isPKSetByUser)
{
/*
if primary key is set by user then
	FireSQL.isExists(-/-) throw new DataException("Primary key already exists in record insertion failed");
for self reff -> later on create static class for collection of exception message 
*/
if(FireSQL.isExists(connection,value,dataType,tableName,columnName)) throw new DataException("Primary key already exists in record");
}


if(foreignKeyAnnotation!=null) // not null means annotation applied on prop. of class
{
/*
if foriegnKey Annotation Applied -
	FireSQL.isExists(-/-) if false then throw exception otherwise continue to rest of the process
*/
if(!FireSQL.isExists(connection,value,dataType,foreignTableName,foreignTableColumnName)) throw new DataException("Foreign key is not found in foreign table");
}

// validation phase ends



columnDataWithAdditionalInformation=new ColumnDataWithAdditionalInformation();
columnDataWithAdditionalInformation.setColumnName(columnName);
columnDataWithAdditionalInformation.setColumnData(value);
columnDataWithAdditionalInformation.setDataType(dataType);
columnDataWithAdditionalInformation.setIsPrimaryKey(isPrimaryKey);
columnDataWithAdditionalInformation.setIsAutoIncremented(isAutoIncremented);
columnDataWithAdditionalInformation.setForeignTableName(foreignTableName);
columnDataWithAdditionalInformation.setForeignTableColumnName(foreignTableColumnName);

listOfColumnsDataWithAdditionalInformation.add(columnDataWithAdditionalInformation);
} // loop braces ends
result=FireSQL.insert(connection,tableName,listOfColumnsDataWithAdditionalInformation,isTableContainsAutoIncrementedProperty);
return result;
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
