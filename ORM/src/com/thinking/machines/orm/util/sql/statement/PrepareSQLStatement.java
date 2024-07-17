package com.thinking.machines.orm.util.sql.statement;
import com.thinking.machines.orm.util.column.*;
import com.thinking.machines.orm.util.string.*;

import java.util.*;



public class PrepareSQLStatement
{

private static void putColumnNamesIntoAddSQLStatement(StringBuilder stringBuilder,List<ColumnDataWithAdditionalInformation> columnsDataWithAdditionalInformation)
{
stringBuilder.append(" (");
boolean firstColumn=true;
for(ColumnDataWithAdditionalInformation columnData: columnsDataWithAdditionalInformation)
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
}

private static void putValuesIntoAddSQLStatement(StringBuilder stringBuilder,List<ColumnDataWithAdditionalInformation> columnsDataWithAdditionalInformation)
{
Object value=null;
Class dataType=null;
boolean firstValue=true;
stringBuilder.append(" values(");
for(ColumnDataWithAdditionalInformation columnData: columnsDataWithAdditionalInformation)
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

public static String forAdd(String tableName,List<ColumnDataWithAdditionalInformation> columnsDataWithAdditionalInformation)
{
// insert into course (title) values("JAVA")
// insert into tableName (columnName) values(list_of_values)
StringBuilder stringBuilder=new StringBuilder();
stringBuilder.append("insert into ");
stringBuilder.append(tableName);
System.out.println(1);
putColumnNamesIntoAddSQLStatement(stringBuilder,columnsDataWithAdditionalInformation);
System.out.println(2);
putValuesIntoAddSQLStatement(stringBuilder,columnsDataWithAdditionalInformation);
System.out.println(3);
return stringBuilder.toString();
}

public static String forForiegnKeyExists(String tableName,String columnName,Object value,Class dataType)
{
// select code from course where code=1
// select columnName from tableName where code=value
StringBuilder stringBuilder=new StringBuilder();
stringBuilder.append("select ");
stringBuilder.append(columnName);
stringBuilder.append(" from ");
stringBuilder.append(tableName);
stringBuilder.append(" where ");
stringBuilder.append(columnName);
stringBuilder.append("=");
stringBuilder.append(StringUtility.stringFormOfValue(value,dataType));
return stringBuilder.toString();
}

}// class braces close
