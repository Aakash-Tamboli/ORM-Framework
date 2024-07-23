package com.thinking.machines.orm.pojo;
import java.lang.reflect.*;

public class ColumnData
{
private String columnName;
private Object columnData;
private Class dataType;
private boolean isPrimaryKey;
private boolean isAutoIncremented;
private String foreignTableName;
private String foreignTableColumnName;

// I'll write constructor once this class finalized

// setter
public void setColumnName(String columnName)
{
this.columnName=columnName;
}

public void setColumnData(Object columnData)
{
this.columnData=columnData;
}

public void setDataType(Class dataType)
{
this.dataType=dataType;
}

public void setIsPrimaryKey(boolean isPrimaryKey)
{
this.isPrimaryKey=isPrimaryKey;
}

public void setForeignTableName(String foreignTableName)
{
this.foreignTableName=foreignTableName;
}

public void setForeignTableColumnName(String foreignTableColumnName)
{
this.foreignTableColumnName=foreignTableColumnName;
}

public void setIsAutoIncremented(boolean isAutoIncremented)
{
this.isAutoIncremented=isAutoIncremented;
}

// getters

public String getColumnName()
{
return this.columnName;
}
public Object getColumnData()
{
return this.columnData;
}

public Class getDataType()
{
return this.dataType;
}

public boolean getIsPrimaryKey()
{
return this.isPrimaryKey;
}

public String getForeignTableName()
{
return this.foreignTableName;
}

public String getForeignTableColumnName()
{
return this.foreignTableColumnName;
}

public boolean getIsAutoIncremented()
{
return this.isAutoIncremented;
}

}// class braces close
