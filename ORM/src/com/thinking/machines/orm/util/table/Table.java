package com.thinking.machines.orm.util.table;
import com.thinking.machines.orm.exception.*;
import com.thinking.machines.orm.util.column.*;
import com.thinking.machines.orm.util.string.*;
import com.thinking.machines.orm.util.constant.*;
import com.thinking.machines.orm.util.compiler.*;
import com.thinking.machines.orm.util.jar.*;
import com.thinking.machines.orm.model.*;
import java.sql.*;
import java.io.*;
import java.util.*;


public class Table
{

public static void convertTablesIntoClasses(DatabaseMetaData databaseMetaData,String packaging) throws DataException
{
final String folderName="pojos";
ResultSet tables=null;
File file=null;
RandomAccessFile randomAccessFile=null;
String []types={"table"};
String tableNameInDB=null;
String tableNameForClass=null;
String pathToSourceCode=null;
String pathToCompiledCode=null;
String subDirectories=new String(packaging);
StringBuilder stringBuilder=new StringBuilder();
String compiledFilesPath=null;
String jarFileFolder=null;


// pojos.abc.xyz
subDirectories=subDirectories.replace(".",File.separator);
// pojos/abc/xyz


pathToSourceCode=folderName+File.separator+"src"+File.separator+subDirectories;
// pojos/src/abc/xyz


try
{
tables=databaseMetaData.getTables(null,null,"%",types);

file=new File(pathToSourceCode);

if(!file.exists())
{
if(!file.mkdirs()) throw new DataException("Unable to create dist folder");
}


while(tables.next())
{
tableNameInDB=tables.getString(Constant.TABLE_NAME);
tableNameForClass=StringUtility.ConvertIntoClassName(tableNameInDB);
file=new File(pathToSourceCode+File.separator+tableNameForClass+".java");
// pojos/src/abc/xyz/Student.java
if(file.exists()) file.delete();
randomAccessFile=new RandomAccessFile(file,"rw");
if(packaging!=null) stringBuilder.append("package "+packaging+";\n");
stringBuilder.append("import com.thinking.machines.orm.annotation.*;\n\n");
stringBuilder.append("@Table(name=\""+tableNameInDB+"\")\n");
stringBuilder.append("public class "+tableNameForClass+"\n{\n");
randomAccessFile.writeBytes(stringBuilder.toString());
Column.convertColumnsIntoClassProps(databaseMetaData,randomAccessFile,tableNameInDB);
// System.out.println("For: "+tableNameInDB+" Corresponding className is generated");
randomAccessFile.close();
stringBuilder.setLength(0); 
}// outer loop ends
tables.close();
tables=null;
file=null;
randomAccessFile=null;

pathToCompiledCode=folderName+File.separator+"classes";

com.thinking.machines.orm.util.compiler.Compiler.compile(pathToSourceCode,pathToCompiledCode);

jarFileFolder=folderName+File.separator+"dist";

Jar.makeJar(pathToCompiledCode,jarFileFolder);

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

public static Map<String,Map<String,ColumnInfo>> getTablesInfo(DatabaseMetaData databaseMetaData) throws DataException
{
ResultSet tables=null;
String []types={"table"};
String tableName=null;
Map<String,Map<String,ColumnInfo>> tablesInformation=null;
Map<String,ColumnInfo> columnsInformation=null;

try
{

tables=databaseMetaData.getTables(null,null,"%",types);

tablesInformation=new HashMap<>();


while(tables.next())
{
tableName=tables.getString(Constant.TABLE_NAME);
columnsInformation=Column.getColumnsInformation(databaseMetaData,tableName);
tablesInformation.put(tableName,columnsInformation);
// System.out.println("For: "+tableNameInDB+" Corresponding className is generated");
}// outer loop ends
tables.close();
tables=null;
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
// it makes sense when exception occured
try
{
if(tables!=null) tables.close(); 
}catch(Exception exception)
{
// do nothing will rarely happen due to internal issues
}
} // final block end braces
return tablesInformation;
} // method end braces
} // class braces close
