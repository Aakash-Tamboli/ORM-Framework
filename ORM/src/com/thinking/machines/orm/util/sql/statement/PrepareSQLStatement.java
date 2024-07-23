package com.thinking.machines.orm.util.sql.statement;
import com.thinking.machines.orm.util.column.*;
import com.thinking.machines.orm.util.string.*;
import com.thinking.machines.orm.pojo.*;


import java.util.*;



public class PrepareSQLStatement
{
private static StringBuilder stringBuilder=null; // I want that this props always acessible by all methods within class
private static String getColumns(List<ColumnData> columnsData)
{
/*
It will generate string contains columnsName in sql format
(name,gender,so on)
*/
stringBuilder.append(" (");
boolean firstColumn=true;
for(ColumnData columnData: columnsData)
{
if(columnData.getIsAutoIncremented()) continue;
if(firstColumn)
{
stringBuilder.append(columnData.getColumnName());
firstColumn=false;
}
else
{
stringBuilder.append(","+columnData.getColumnName());
}
}
stringBuilder.append(")");
return stringBuilder.toString();
}

private static void getValues(List<ColumnData> columnsData)
{
/*
It will generate string contains values in sql format
values("Aakash","M",so on)
*/
Object value=null;
Class dataType=null;
boolean firstValue=true;
stringBuilder.append(" values(");
for(ColumnData columnData: columnsData)
{
System.out.println(1.1);
if(columnData.getIsAutoIncremented()) continue;

value=columnData.getColumnData();
dataType=columnData.getDataType();

if(firstValue) firstValue=false;
else stringBuilder.append(",");

System.out.println(1.2+" For type of: "+dataType);
stringBuilder.append(StringUtility.stringFormOfValue(value,dataType));
System.out.println(1.3);
} // loop braces ends
stringBuilder.append(")");
} // method braces ends

public static String forInsert(String tableName,List<ColumnData> columnsData)
{
// insert into course (title) values("JAVA")
// insert into tableName (columnName) values(list_of_values)
String sql=null;
stringBuilder=new StringBuilder();
stringBuilder.append("insert into ");
stringBuilder.append(tableName);
getColumns(columnsData);
getValues(columnsData);
sql=stringBuilder.toString();
stringBuilder=null;
System.out.println("SQL in Prepared SQL Module: ["+sql+"]");
return sql;
}

// toCheck Foreign Key
// toCheck Primary Key & so on
public static String toCheck(String tableName,String columnName,Object value,Class dataType)
{
// select code from course where code=1
// select columnName from tableName where code=value
String sql=null;
stringBuilder=new StringBuilder();
stringBuilder.append("select ");
stringBuilder.append(columnName);
stringBuilder.append(" from ");
stringBuilder.append(tableName);
stringBuilder.append(" where ");
stringBuilder.append(columnName);
stringBuilder.append("=");
stringBuilder.append(StringUtility.stringFormOfValue(value,dataType));
sql=stringBuilder.toString();
stringBuilder=null;
return sql;
}

}// class braces close
