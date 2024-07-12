package com.thinking.machines.util.table;
import com.thinking.machines.orm.exception.*;
import com.thinking.machines.util.column.*;
import com.thinking.machines.util.string.*;
import com.thinking.machines.util.constant.*;
import java.sql.*;
import java.io.*;

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

public static Set<String> getAllTables(DatabaseMetaData databaseMetaData,String directoryName) throws DataException



} // class braces close