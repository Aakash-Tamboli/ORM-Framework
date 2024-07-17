package com.thinking.machines.orm.util.sql;
import com.thinking.machines.orm.util.sql.statement.*;
import com.thinking.machines.orm.util.column.*;
import com.thinking.machines.orm.exception.*;
import java.sql.*;
import java.util.*;



public class FireSQL
{

public static boolean isExists(Connection connection,Object value,Class dataType,String tableName,String columnName) throws DataException
{
Statement statement=null;
String sqlStatement=null;
ResultSet resultSet=null;
boolean isExists=false;
try
{
statement=connection.createStatement();
sqlStatement=PrepareSQLStatement.forForiegnKeyExists(tableName,columnName,value,dataType);
System.out.println("SQL Query will be fired: "+sqlStatement);
resultSet=statement.executeQuery(sqlStatement);
if(resultSet.next()) isExists=true;
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
// throw new DataException(exception.getMessage());
// wait think more about all corner testcases
}
}
return isExists;
}

// later on int will change into support of loose coupling
public static int insert(Connection connection,String tableName,List<ColumnDataWithAdditionalInformation> columnsDataWithAdditionalInformation,boolean isAutoIncremented) throws DataException
{
Statement statement=null;
String sqlStatement=null;
ResultSet resultSet=null;
int generatedCode=-1;
try
{
statement=connection.createStatement();
System.out.println("list of Object is going to generate sql");
sqlStatement=PrepareSQLStatement.forAdd(tableName,columnsDataWithAdditionalInformation);
System.out.println("Query will fired: "+sqlStatement);

// temporary code
if(isAutoIncremented)
{
statement.executeUpdate(sqlStatement,Statement.RETURN_GENERATED_KEYS);
resultSet=statement.getGeneratedKeys();
resultSet.next(); // to fetch
generatedCode=resultSet.getInt(1);
}
else
{
statement.executeUpdate(sqlStatement);
generatedCode=-1;
}
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
// throw new DataException(exception.getMessage());
// to do more think about corner testcases
}
}
return generatedCode;
}
}
