package com.thinking.machines.orm.util.table;
import com.thinking.machines.orm.exception.*;
import com.thinking.machines.orm.util.column.*;
import com.thinking.machines.orm.util.string.*;
import com.thinking.machines.orm.util.constant.*;
import java.sql.*;
import java.io.*;
import java.util.*;

public class Table
{
public static void convertTablesIntoClasses(DatabaseMetaData databaseMetaData,String directoryName) throws DataException
{
ResultSet tables=null;
File file=null;
RandomAccessFile randomAccessFile=null;
String []types={"table"};
String tableNameInDB="";
String tableNameForClass="";
String str="";

try
{

tables=databaseMetaData.getTables(null,null,"%",types);

while(tables.next())
{
tableNameInDB=tables.getString(Constant.TABLE_NAME);
tableNameForClass=StringUtility.ConvertIntoClassName(tableNameInDB);
if(directoryName!=null && directoryName.equals("")==false)
{
file=new File(directoryName+File.separator+tableNameForClass+".java");
}
else
{
file=new File(tableNameForClass+".java");
}
if(file.exists()) file.delete();
randomAccessFile=new RandomAccessFile(file,"rw");

str="import com.thinking.machines.orm.annotation.*;\n\n";
str=str+"@Table(name=\""+tableNameInDB+"\")\n";
randomAccessFile.writeBytes(str);
str="public class "+tableNameForClass+"\n{\n";
randomAccessFile.writeBytes(str);
Column.convertColumnsIntoClassProps(databaseMetaData,randomAccessFile,tableNameInDB,tableNameForClass);
// System.out.println("For: "+tableNameInDB+" Corresponding className is generated");
randomAccessFile.close();
}// outer loop ends
tables.close();
tables=null;
file=null;
randomAccessFile=null;
}catch(DataException dataException)
{
throw dataException;
}
catch(Exception exception)
{
throw new DataException(exception.getMessage());
}
finally
{
// it makes senese when exception occured
try
{
if(tables!=null) tables.close(); 
if(randomAccessFile!=null) randomAccessFile.close();
}catch(Exception exception)
{
// do nothing will rarely happen due to internal issues
}
}
} // method braces close

public static Set<String> getAllTables(DatabaseMetaData databaseMetaData) throws DataException
{
//
ResultSet tables=null;
Set<String> setOfTables=new HashSet<>();
String []types={"table"};
String tableNameInDB="";

try
{

tables=databaseMetaData.getTables(null,null,"%",types);

while(tables.next())
{
tableNameInDB=tables.getString(Constant.TABLE_NAME);
setOfTables.add(tableNameInDB);
}// outer loop ends

tables.close();
tables=null;
}
catch(Exception exception)
{
throw new DataException(exception.getMessage());
}
finally
{
// it makes senese when exception occured
try
{
if(tables!=null) tables.close(); 
}catch(Exception exception)
{
// do nothing will rarely happen due to internal issues
}
}
return setOfTables;
}

} // class braces close
