package com.thinking.machines.orm.util.column;
import com.thinking.machines.orm.util.string.*;
import com.thinking.machines.orm.util.foreignkey.*;
import com.thinking.machines.orm.util.constant.*;
import com.thinking.machines.orm.exception.*;
import com.thinking.machines.orm.model.*;
import java.sql.*;
import java.io.*;
import java.util.*;

public class Column
{

public static void convertColumnsIntoClassProps(DatabaseMetaData databaseMetaData,RandomAccessFile randomAccessFile,String tableNameInDB) throws DataException
{
StringBuilder stringBuilder=new StringBuilder();
Set<String> primaryKeysSet=null;
Set<String> uniqueKeysSet=null;
Map<String,ForeignKeyInformation> foreignKeysMap=null;

ResultSet columns=null;

ForeignKeyInformation foreignKeyInformation=null;

String columnName=null;
String columnType=null;
String foreignKeyTableName=null;
String foreignKeyColumnName=null;
String isAutoIncremented=null;
String isNullable=null;

int columnSize;
boolean isPrimaryKey=false;

try
{

primaryKeysSet=getPrimaryKeys(databaseMetaData,tableNameInDB);

foreignKeysMap=getForeignKeys(databaseMetaData,tableNameInDB);

uniqueKeysSet=getUniqueKeys(databaseMetaData,tableNameInDB);

columns=databaseMetaData.getColumns(null,null,tableNameInDB,"%");

while(columns.next())
{
columnName=columns.getString(Constant.COLUMN_NAME);

columnType=columns.getString(Constant.TYPE_NAME);

columnSize=columns.getInt(Constant.COLUMN_SIZE);

isAutoIncremented=columns.getString(Constant.IS_AUTOINCREMENT);

isNullable=columns.getString(Constant.IS_NULLABLE);


if(primaryKeysSet.contains(columnName)) stringBuilder.append("@PrimaryKey\n");

if(isAutoIncremented.equals("YES")) stringBuilder.append("@AutoIncrement\n");

if(isNullable.equalsIgnoreCase("NO")) stringBuilder.append("@NOTNULL\n");

if(uniqueKeysSet.contains(columnName)) stringBuilder.append("@Unique\n");

// System.out.println("Column Name: "+columnName);
if(foreignKeysMap.containsKey(columnName))
{
foreignKeyInformation=foreignKeysMap.get(columnName);
stringBuilder.append("@ForeignKey(parent=\""+foreignKeyInformation.getForeignTableName()+"\",column=\""+foreignKeyInformation.getForeignTableColumnName()+"\")\n");
}

stringBuilder.append("@Column(name=\""+columnName+"\")\n");
stringBuilder.append("public ");

if(columnType.equalsIgnoreCase("TINYINT") || columnType.equals("BIT")) stringBuilder.append("byte ");
else if(columnType.equalsIgnoreCase("SMALLINT")) stringBuilder.append("short ");
else if(columnType.equals("MEDIUMINT") || columnType.equals("INT") || columnType.equals("INTEGER") || columnType.equals("YEAR")) stringBuilder.append("int ");
else if(columnType.equals("BIGINT")) stringBuilder.append("long ");
else if(columnType.equals("FLOAT")) stringBuilder.append("float ");
else if(columnType.equals("DOUBLE") || columnType.equals("REAL")) stringBuilder.append("double ");
else if(columnType.equals("DECIMAL") || columnType.equals("NUMERIC")) stringBuilder.append("java.math.BigDecimal ");
else if(columnType.equals("CHAR") && columnSize==1) stringBuilder.append("char ");
else if((columnSize!=1) && (columnType.equals("CHAR") || columnType.equals("VARCHAR") || columnType.equals("TEXT") || columnType.equals("MEDUIMTEXT") || columnType.equals("LONGTEXT") || columnType.equals("ENUM") || columnType.equals("SET"))) stringBuilder.append("String ");
else if(columnType.equals("DATE")) stringBuilder.append("java.sql.Date ");
else if(columnType.equals("DATETIME") || columnType.equals("TIMESTAMP")) stringBuilder.append("java.sql.Timestamp ");
else if(columnType.equals("TIME")) stringBuilder.append("java.sql.Time ");
else if(columnType.equals("BINARY") || columnType.equals("VARBINARY") || columnType.equals("BLOB")) stringBuilder.append("byte[] ");
else if(columnType.equals("BOOLEAN") || columnType.equals("BOOL")) stringBuilder.append("boolean ");
// System.out.printf("DEBUG [%s type of %s]\n",columnName,columnType);
stringBuilder.append(StringUtility.ConvertIntoVariableName(columnName)+";\n\n\n");;
}
String str=stringBuilder.toString();
randomAccessFile.writeBytes(str);
randomAccessFile.writeBytes("}");
columns.close();
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
try
{
if(columns!=null) columns.close();
}catch(SQLException sqlException)
{
throw new DataException(sqlException.getMessage());
//this will happen due to internal issues
}
}


} // method braces close


public static Map<String,ColumnInfo> getColumnsInformation(DatabaseMetaData databaseMetaData,String tableName) throws DataException
{
Map<String,ColumnInfo> columnsInformation=null;

ColumnInfo columnInfo=null;

Set<String> primaryKeysSet=null;
Set<String> uniqueKeysSet=null;
Map<String,ForeignKeyInformation> foreignKeysMap=null;

ResultSet columns=null;

ForeignKeyInformation foreignKeyInformation=null;


String columnName=null;
String columnType=null;
String foreignKeyTableName=null;
String foreignKeyColumnName=null;
String isAutoIncremented=null;
String isNullable=null;

int columnSize;
boolean isPrimaryKey=false;

try
{

primaryKeysSet=getPrimaryKeys(databaseMetaData,tableName);

foreignKeysMap=getForeignKeys(databaseMetaData,tableName);

uniqueKeysSet=getUniqueKeys(databaseMetaData,tableName);

columns=databaseMetaData.getColumns(null,null,tableName,"%");

columnsInformation=new HashMap<>();

while(columns.next())
{
columnName=columns.getString(Constant.COLUMN_NAME);

columnType=columns.getString(Constant.TYPE_NAME);

columnSize=columns.getInt(Constant.COLUMN_SIZE);

isAutoIncremented=columns.getString(Constant.IS_AUTOINCREMENT);

isNullable=columns.getString(Constant.IS_NULLABLE);

columnInfo=new ColumnInfo();


if(primaryKeysSet.contains(columnName)) columnInfo.setPrimaryKey(true);
else columnInfo.setPrimaryKey(false);

if(isAutoIncremented.equals("YES")) columnInfo.setAutoIncrement(true);
else columnInfo.setAutoIncrement(false);

if(isNullable.equalsIgnoreCase("NO")) columnInfo.setNotNull(true);
else columnInfo.setNotNull(false);

if(uniqueKeysSet.contains(columnName)) columnInfo.setUnique(true);
else columnInfo.setUnique(false);

// System.out.println("Column Name: "+columnName);
if(foreignKeysMap.containsKey(columnName))
{
foreignKeyInformation=foreignKeysMap.get(columnName);
columnInfo.setForeignKeyInformation(foreignKeyInformation);
}
else
{
columnInfo.setForeignKeyInformation(null);
}

columnsInformation.put(columnName,columnInfo);

} // loop ends
columns.close();
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
try
{

if(columns!=null) columns.close();

}catch(SQLException sqlException)
{

throw new DataException(sqlException.getMessage());

// this will happen due to internal issues
}
}
return columnsInformation;
} // getColumnsInformation braces close

//***** FOLLOWING ARE ALL THE UTILITY METHODS RELATED TO THIS CLASS ****************

private static Set<String> getPrimaryKeys(DatabaseMetaData databaseMetaData,String tableName) throws DataException
{
Set<String> primaryKeysSet=new HashSet<>();

ResultSet primaryKeys=null;

try
{

primaryKeys=databaseMetaData.getPrimaryKeys(null,null,tableName);

while(primaryKeys.next())
{

primaryKeysSet.add(primaryKeys.getString(Constant.COLUMN_NAME));

}


}catch(Exception exception)
{

throw new DataException(exception.getMessage());

}
finally
{

try
{

if(primaryKeys!=null) primaryKeys.close();

}catch(Exception exception)
{
throw new DataException(exception.getMessage());
}

}

return primaryKeysSet;
} // method braces close

private static Map<String,ForeignKeyInformation> getForeignKeys(DatabaseMetaData databaseMetaData,String tableName) throws DataException
{

String columnName=null;
String foreignKeyTableName=null;
String foreignKeyColumnName=null;

Map<String,ForeignKeyInformation> foreignKeysMap=new HashMap<>();

ResultSet foreignKeys=null;

try
{

foreignKeys=databaseMetaData.getImportedKeys(null,null,tableName);

while(foreignKeys.next())
{
columnName=foreignKeys.getString(Constant.FKCOLUMN_NAME);

foreignKeyTableName=foreignKeys.getString(Constant.PKTABLE_NAME);

foreignKeyColumnName=foreignKeys.getString(Constant.PKCOLUMN_NAME);

foreignKeysMap.put(columnName,new ForeignKeyInformation(columnName,foreignKeyTableName,foreignKeyColumnName));
}

}catch(Exception exception)
{

throw new DataException(exception.getMessage());

}
finally
{
try
{

if(foreignKeys!=null) foreignKeys.close();

}catch(Exception exception)
{

throw new DataException(exception.getMessage());

}

}

return foreignKeysMap;
} // method braces close


private static Set<String> getUniqueKeys(DatabaseMetaData databaseMetaData,String tableName) throws DataException
{
Set<String> uniqueKeysSet=new HashSet<>();

String columnName=null;

ResultSet uniqueKeys=null;

try
{

uniqueKeys=databaseMetaData.getIndexInfo(null,null,tableName,true,false);

while(uniqueKeys.next())
{
columnName=uniqueKeys.getString(Constant.COLUMN_NAME);
uniqueKeysSet.add(columnName);
}



}catch(Exception exception)
{

throw new DataException(exception.getMessage());

}
finally
{

try
{

if(uniqueKeys!=null) uniqueKeys.close();

}catch(Exception exception)
{
throw new DataException(exception.getMessage());
}

}
return uniqueKeysSet;
} // method braces close








} // class brace close
