package com.thinking.machines.orm;
import com.thinking.machines.orm.util.json.*;
import com.thinking.machines.orm.util.table.*;
import com.thinking.machines.orm.util.column.*;
import com.thinking.machines.orm.util.foreignkey.*;
import com.thinking.machines.orm.util.validator.*;
import com.thinking.machines.orm.util.sql.*;
import com.thinking.machines.orm.util.fieldwrapper.*;
import com.thinking.machines.orm.model.*;
import com.thinking.machines.orm.pojo.*;
import com.thinking.machines.orm.exception.*;
import com.thinking.machines.orm.annotation.*;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;


public class DataManager
{

// ******** PRIVATE SECTION OF CLASS  STARTS ********************
private static ConfigurationFile configurationFile=null;
private static DataManager dataManager=null;
private static Connection connection=null;
private static DatabaseMetaData databaseMetaData=null;
private Map<String,Map<String,ColumnInfo>> tablesInfo=null;

private DataManager()
{
this.tablesInfo=null;
}

private static Connection getConnection() throws Exception
{
Connection connection=null;
try
{
String JDBCDriver=configurationFile.getJDBCDriver();
if(JDBCDriver!=null) JDBCDriver=JDBCDriver.trim();
else throw new Exception("JDBC Driver not specify in config.json");
String connectionURL=configurationFile.getConnectionURL();
if(connectionURL!=null) connectionURL=connectionURL.trim();
else throw new Exception("connectionURL not specify in config.json");
String username=configurationFile.getUsername();
if(username!=null) username=username.trim();
else throw new Exception("username not specify in config.json");
String password=configurationFile.getPassword();
if(password!=null) password=password.trim();
else throw new Exception("password not specify in config.json");

Class.forName(JDBCDriver);

connection=DriverManager.getConnection(connectionURL,username,password);

}catch(Exception exception)
{
throw exception;
}
return connection;
} // method ends

// ******** PRIVATE SECTION OF CLASS  ENDS  ********************

// ******** PUBLIC SECTION OF CLASS STARTS ********************


public static DataManager getDataManager()
{
if(dataManager!=null) return dataManager;
try
{
configurationFile=JsonOperation.loadConfigFile();
DataManager.connection=DataManager.getConnection();
DataManager.databaseMetaData=DataManager.connection.getMetaData();
DataManager.dataManager=new DataManager();
dataManager.tablesInfo=com.thinking.machines.orm.util.table.Table.getTablesInfo(databaseMetaData);
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

public void generateClasses() throws DataException
{
/*
This method will generate All POJOS Classes into dist folder
where each POJO classes are representation of acutal tables in DB
*/
try
{
com.thinking.machines.orm.util.table.Table.convertTablesIntoClasses(databaseMetaData,configurationFile.getPackaging());
}catch(DataException dataException)
{
throw dataException;
}
}

public int save(Object object) throws DataException
{
/*
Pseudo Code -
- Known Fact :
	I have DS which is Map(Tree) in it -
		I have tableName and Column Details


- if @Table annotation not found then throw exception

- Extract @Table annotation values

- if table name is not found in our DS then throw exception

- Extract all fields of object's class

- check one by one

loop starts
- if @Column Annotation not found then then throw exception

- Extract @Column Annotation values

- if columnName not found in DS then then throw exception

- Extract ColumnInfo Object from DS that represent current column

	- Extract value from object's attribute
	
	- if Object's attribute data-type is not correct then throw exception
	
	- if column 
		- has constraint i.e not null, then
			- if Extracted value is null then throw exception
		- has constraint i.e primary key then
			- if set by user then
				- check extracted value is present or not present in DB
					if it is then throw exception
			- if AutoIncremented then OK
		- has constraint i.e unique then
				- if extracted value already present in DB then throw exception
				  otherwise no problem
		- has constraint i.e foreign key then,
			- foriegnkey table & foreignKey column Must be be present in DB
				if not present then throw exception
				otherwise no problem.
	
loop ends



*/
int result=-1;

String tableName=null;
String columnName=null;

ForeignKey foreignKeyAnnotation=null;
com.thinking.machines.orm.annotation.Table tableAnnotation=null;
com.thinking.machines.orm.annotation.Column columnAnnotation=null;

Field []fields=null;

List<ColumnData> listOfColumnsData=null;
ColumnData columnData=null;

Map<String,ColumnInfo> columnsInformation=null;
ColumnInfo columnInfo=null;

boolean isPrimaryKey=false;
boolean isNOTNULL=false;
boolean isPKSetByUser=false;
boolean isUnique=false;
boolean isAutoIncremented=false;
boolean isTableContainsAutoIncrementedProperty=false;

ForeignKeyInformation foreignKeyInformation=null;

String foreignTableName=null;
String foreignTableColumnName=null;

Class c=null;
Class dataType=null;
Object value=null;


c=object.getClass();

if(!c.isAnnotationPresent(com.thinking.machines.orm.annotation.Table.class))
{
throw new DataException("@Table Annotation is applied on class");
}

tableAnnotation=(com.thinking.machines.orm.annotation.Table)c.getAnnotation(com.thinking.machines.orm.annotation.Table.class);
tableName=tableAnnotation.name();

if(!tablesInfo.containsKey(tableName))
{
throw new DataException(tableName+" not exitst in database");
}

columnsInformation=tablesInfo.get(tableName);


fields=c.getFields();

listOfColumnsData=new ArrayList<>();

for(Field field: fields)
{
// reseting varaibles for next cycle for good readability


if(!field.isAnnotationPresent(com.thinking.machines.orm.annotation.Column.class)) throw new DataException("@Column annotation is not applied on "+field.getName()+" props of class "+c.getSimpleName());

columnAnnotation=(com.thinking.machines.orm.annotation.Column)field.getAnnotation(com.thinking.machines.orm.annotation.Column.class);

columnName=columnAnnotation.name();

if(!columnsInformation.containsKey(columnName)) throw new DataException(columnName+" not exists in "+tableName+" table as column");

columnInfo=columnsInformation.get(columnName);

// generate warning if everything is alright but annotation is not applied by user

value=FieldWrapper.get(field,object);

if(!Validator.isValidType(value)) throw new DataException(field.getName()+" data-type is invalid");

dataType=Validator.whatTypeOf(value);

System.out.println("[DEBUG] Value props: "+value);
System.out.println("[DEBUG] Data props: "+dataType);


// Analysis Phase Starts

if(columnInfo.hasNOTNULL())
{
if(!field.isAnnotationPresent(NOTNULL.class)) System.out.println("[WARN] @NOT NULL Annotation not applied");
isNOTNULL=true;
}
else
{
// else is not necc. but I gave it because of readability.
isNOTNULL=false;
}

if(columnInfo.hasPrimaryKey())
{

if(!field.isAnnotationPresent(PrimaryKey.class)) System.out.println("[WARN] @PrimaryKey Annotation not applied");

isPrimaryKey=true;

if(columnInfo.hasAutoIncrement())
{
if(!field.isAnnotationPresent(AutoIncrement.class)) System.out.println("[WARN] @AutoIncrement Annotation not applied");
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
else
{
// else is not necc. but I gave it because of readability.
isPrimaryKey=false;
isPKSetByUser=false;
isAutoIncremented=false;
}

if(columnInfo.hasUnique())
{
if(!field.isAnnotationPresent(Unique.class)) System.out.println("[WARN] @Unique Annotation not applied");
isUnique=true;
}
else
{
isUnique=false;
}


if(columnInfo.hasForeignKey()!=null)
{
if(!field.isAnnotationPresent(ForeignKey.class))
{
System.out.println("[WARN] @ForeignKey Annotation not applied");
// user not provided foreign key information, I'll get from model ColumnInfo
foreignKeyInformation=columnInfo.getForeignKeyInformation();
foreignTableName=foreignKeyInformation.getForeignTableName();
foreignTableColumnName=foreignKeyInformation.getForeignTableColumnName();
}
else
{
foreignKeyAnnotation=(ForeignKey)field.getAnnotation(ForeignKey.class);
foreignTableName=foreignKeyAnnotation.parent();
foreignTableColumnName=foreignKeyAnnotation.column();
}
}
else
{
// else is not necc. but I gave it because of readability.
foreignKeyInformation=null;
foreignKeyAnnotation=null;
}

// Analysis phase ends


// validation phase Starts

if(isNOTNULL)
{
if(value==null) throw new DataException(columnName+" has NOT NULL constraint applied, So pass valid value");
}

if(isPKSetByUser)
{
/*
for self reff -> later on create static class for collection of exception message 
*/
if(FireSQL.isExists(connection,value,dataType,tableName,columnName)) throw new DataException("Primary key already exists in record");
}

if(isUnique)
{
if(FireSQL.isExists(connection,value,dataType,tableName,columnName)) throw new DataException(columnName+" has Unique constraint hence your value already exists in record");
}


if(foreignKeyInformation!=null || foreignKeyAnnotation!=null)
{
/*
if foriegnKey Annotation Applied -
	FireSQL.isExists(-/-) if false then throw exception otherwise continue to rest of the process
*/
if(!FireSQL.isExists(connection,value,dataType,foreignTableName,foreignTableColumnName)) throw new DataException("Problem with Foreign key, May Foreign Table or Column or value not exitst");
}

// validation phase ends


// Pojos to send data to FireSQL buddy to execute statements
columnData=new ColumnData();
columnData.setColumnName(columnName);
columnData.setColumnData(value);
columnData.setDataType(dataType);
columnData.setIsPrimaryKey(isPrimaryKey);
columnData.setIsAutoIncremented(isAutoIncremented);
columnData.setForeignTableName(foreignTableName);
columnData.setForeignTableColumnName(foreignTableColumnName);

// adding into list
listOfColumnsData.add(columnData);

// reseting variabls for next cycle
columnAnnotation=null;
columnName=null;
value=null;
dataType=null;
isPrimaryKey=false;
isPKSetByUser=false;
isAutoIncremented=false;
isNOTNULL=false;
isUnique=false;
foreignKeyInformation=null;
foreignKeyAnnotation=null;
foreignTableName=null;
foreignTableColumnName=null;
} // loop braces ends
result=FireSQL.insert(connection,tableName,listOfColumnsData,isTableContainsAutoIncrementedProperty);
return result;
}

// ******** PUBLIC SECTION OF CLASS ENDS ********************

} // class braces close
