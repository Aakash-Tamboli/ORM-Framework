package com.thinking.machines.orm.util.column;
import java.lang.reflect.*;

public class ColumnDataWithAdditionalInformation
{
private String columnName;
private Object columnData;
private Class dataType;
private boolean isPrimaryKey;

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

public boolean isPrimaryKey()
{
return this.isPrimaryKey;
}

}// class braces close
