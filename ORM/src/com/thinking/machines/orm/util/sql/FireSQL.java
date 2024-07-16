package com.thinking.machines.orm.util.sql;
import com.thinking.machines.orm.util.column.*;
import com.thinking.machines.orm.exception.*;
import java.sql.*;
import java.util.*;



public class FireSQL
{

private static void putColumnNamesIntoSQLStatement(StringBuilder stringBuilder,List<ColumnDataWithAdditionalInformation> columnsDataWithAdditionalInformation)
{
stringBuilder.append(" (");
short counter=1;
for(ColumnDataWithAdditionalInformation columnData: columnsDataWithAdditionalInformation)
{
if(columnData.isPrimaryKey()) continue;
if(counter==1)
{
stringBuilder.append(columnData.getColumnName());
counter++;
}
else
{
stringBuilder.append(","+columnData.getColumnName());
}
}
stringBuilder.append(")");
}

private static void putValuesIntoSQLStatement(StringBuilder stringBuilder,List<ColumnDataWithAdditionalInformation> columnsDataWithAdditionalInformation)
{
Long l=null;
Integer i=null;
Short s=null;
Byte b=null;
Double d=null;
Float f=null;
String str=null;
Boolean bool=null;
Class dataType=null;
short counter=1;

stringBuilder.append(" values(");
for(ColumnDataWithAdditionalInformation columnData: columnsDataWithAdditionalInformation)
{
if(columnData.isPrimaryKey()) continue;
dataType=columnData.getDataType();
if(dataType.equals(Long.class))
{
l=(Long)columnData.getColumnData();
if(counter==1) counter++;
else stringBuilder.append(",");
stringBuilder.append(l);
}
else if(dataType.equals(Integer.class))
{
i=(Integer)columnData.getColumnData();
if(counter==1) counter++;
else stringBuilder.append(",");
stringBuilder.append(i);
}
else if(dataType.equals(Short.class))
{
s=(Short)columnData.getColumnData();
if(counter==1) counter++;
else stringBuilder.append(",");
stringBuilder.append(s);
}
else if(dataType.equals(Byte.class))
{
b=(Byte)columnData.getColumnData();
if(counter==1) counter++;
else stringBuilder.append(",");
stringBuilder.append(b);
}
else if(dataType.equals(Double.class))
{
d=(Double)columnData.getColumnData();
if(counter==1) counter++;
else stringBuilder.append(",");
stringBuilder.append(d);
}
else if(dataType.equals(Float.class))
{
f=(Float)columnData.getColumnData();
if(counter==1) counter++;
else stringBuilder.append(",");
stringBuilder.append(f);
}
else if(dataType.equals(String.class))
{
str=(String)columnData.getColumnData();
if(counter==1) counter++;
else stringBuilder.append(",");
stringBuilder.append("\""+str+"\"");
}
else if(dataType.equals(Boolean.class))
{
bool=(Boolean)columnData.getColumnData();
if(counter==1) counter++;
else stringBuilder.append(",");
stringBuilder.append(bool);
}
}
stringBuilder.append(")");
}

private static String prepareSQLStatement(String tableName,List<ColumnDataWithAdditionalInformation> columnsDataWithAdditionalInformation)
{
StringBuilder stringBuilder=new StringBuilder();
stringBuilder.append("insert into ");
stringBuilder.append(tableName);

putColumnNamesIntoSQLStatement(stringBuilder,columnsDataWithAdditionalInformation);
putValuesIntoSQLStatement(stringBuilder,columnsDataWithAdditionalInformation);
return stringBuilder.toString();
}




public static int insert(Connection connection,String tableName,List<ColumnDataWithAdditionalInformation> columnsDataWithAdditionalInformation) throws DataException
{
Statement statement=null;
String sqlStatement=null;
ResultSet resultSet=null;
int generatedCode=0;
try
{
statement=connection.createStatement();
sqlStatement=prepareSQLStatement(tableName,columnsDataWithAdditionalInformation);
System.out.println("Query will fired: "+sqlStatement);
statement.executeUpdate(sqlStatement,Statement.RETURN_GENERATED_KEYS);
resultSet=statement.getGeneratedKeys();
resultSet.next(); // to fetch
generatedCode=resultSet.getInt(1);
}catch(Exception exception)
{
throw new DataException(exception.getMessage());
}
finally
{
try
{
if(statement!=null) statement.close();
if(resultSet!=null) resultSet.close();
}catch(Exception exception)
{
throw new DataException(exception.getMessage());
}
}
return generatedCode;
}
}
