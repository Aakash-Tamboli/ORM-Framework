package com.thinking.machines.util.column;
import com.thinking.machines.util.string.*;
import com.thinking.machines.util.foreignkey.*;
import com.thinking.machines.util.constant.*;
import com.thinking.machines.orm.exception.*;
import java.sql.*;
import java.io.*;
import java.util.*;

public class Column
{
public static void convertColumnsIntoClassProps(DatabaseMetaData databaseMetaData,RandomAccessFile randomAccessFile,String tableNameInDB,String tableNameForClass) throws DataException
{
Set<String> primaryKeysSet=new HashSet<>();
Map<String,ForeignKeyInformation> foreignKeysMap=new HashMap<>();
ResultSet columns=null;
ResultSet primaryKeys=null;
ResultSet foreignKeys=null;
ForeignKeyInformation foreignKeyInformation=null;
String columnName=null;
String columnType=null;
String foreignKeyTableName=null;
String foreignKeyColumnName=null;
String isAutoIncremented="";
String str=null;
int columnSize;
boolean isPrimaryKey=false;

try
{
str="import com.thinking.machines.orm.annotation.*;\n\n";

str=str+"@Table(name=\""+tableNameInDB+"\")\n";
randomAccessFile.writeBytes(str);
str="class "+tableNameForClass+"\n{\n";
randomAccessFile.writeBytes(str);

primaryKeys=databaseMetaData.getPrimaryKeys(null,null,tableNameInDB);
while(primaryKeys.next())
{
primaryKeysSet.add(primaryKeys.getString(Constant.COLUMN_NAME));
}
primaryKeys.close();
primaryKeys=null;


foreignKeys=databaseMetaData.getImportedKeys(null,null,tableNameInDB);
while(foreignKeys.next())
{
columnName=foreignKeys.getString(Constant.FKCOLUMN_NAME);
foreignKeyTableName=foreignKeys.getString(Constant.PKTABLE_NAME);
foreignKeyColumnName=foreignKeys.getString(Constant.PKCOLUMN_NAME);
// System.out.println("FK_Column: "+columnName);
foreignKeysMap.put(columnName,new ForeignKeyInformation(columnName,foreignKeyTableName,foreignKeyColumnName));
}
foreignKeys.close();
foreignKeys=null;


columns=databaseMetaData.getColumns(null,null,tableNameInDB,"%");

while(columns.next())
{
columnName=columns.getString(Constant.COLUMN_NAME);
columnType=columns.getString(Constant.TYPE_NAME);
columnSize=columns.getInt(Constant.COLUMN_SIZE);
isAutoIncremented=columns.getString(Constant.IS_AUTOINCREMENT);

str="";
if(primaryKeysSet.contains(columnName)) str=str+"@PrimaryKey\n";

if(isAutoIncremented.equals("YES")) str=str+"@AutoIncrement\n";

// System.out.println("Column Name: "+columnName);
if(foreignKeysMap.containsKey(columnName))
{
foreignKeyInformation=foreignKeysMap.get(columnName);
str=str+"@ForeignKey(parent=\""+foreignKeyInformation.foreignTableName+"\",column=\""+foreignKeyInformation.foreignTableColumnName+"\")\n";
}

str=str+"@Column(name=\""+columnName+"\")\n";
str=str+"public ";

if(columnType.equalsIgnoreCase("TINYINT") || columnType.equals("BIT")) str=str+"byte ";
else if(columnType.equalsIgnoreCase("SMALLINT")) str=str+"short ";
else if(columnType.equals("MEDIUMINT") || columnType.equals("INT") || columnType.equals("INTEGER") || columnType.equals("YEAR")) str=str+"int ";
else if(columnType.equals("BIGINT")) str=str+"long ";
else if(columnType.equals("FLOAT")) str=str+"float ";
else if(columnType.equals("DOUBLE") || columnType.equals("REAL")) str=str+"double ";
else if(columnType.equals("DECIMAL") || columnType.equals("NUMERIC")) str=str+"java.math.BigDecimal ";
else if(columnType.equals("CHAR") && columnSize==1) str=str+"char ";
else if((columnSize!=1) && (columnType.equals("CHAR") || columnType.equals("VARCHAR") || columnType.equals("TEXT") || columnType.equals("MEDUIMTEXT") || columnType.equals("LONGTEXT") || columnType.equals("ENUM") || columnType.equals("SET"))) str=str+"String ";
else if(columnType.equals("DATE")) str=str+"java.sql.Date ";
else if(columnType.equals("DATETIME") || columnType.equals("TIMESTAMP")) str=str+"java.sql.Timestamp ";
else if(columnType.equals("TIME")) str=str+"java.sql.Time ";
else if(columnType.equals("BINARY") || columnType.equals("VARBINARY") || columnType.equals("BLOB")) str=str+"byte[] ";
else if(columnType.equals("BOOLEAN") || columnType.equals("BOOL")) str=str+"boolean ";
// System.out.printf("DEBUG [%s type of %s]\n",columnName,columnType);
str=str+StringUtility.ConvertIntoVariableName(columnName)+";\n";
randomAccessFile.writeBytes(str);
}
randomAccessFile.writeBytes("}");
columns.close();
columns=null;
}catch(Exception exception)
{
throw new DataException(exception.getMessage());
}
finally
{
try
{
if(columns!=null) columns.close();
if(primaryKeys!=null) primaryKeys.close();
if(foreignKeys!=null) foreignKeys.close();
}catch(SQLException sqlException)
{
// do nothing, this will happen due to internal issues
}
}






}




}