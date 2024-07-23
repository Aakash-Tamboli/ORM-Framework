package com.thinking.machines.orm.util.foreignkey;

public class ForeignKeyInformation implements Comparable<ForeignKeyInformation>
{
private String columnName;
private String foreignTableName;
private  String foreignTableColumnName;

public ForeignKeyInformation()
{
this.columnName=null;
this.foreignTableName=null;
this.foreignTableColumnName=null;
}

public ForeignKeyInformation(String columnName,String foreignTableName,String foreignTableColumnName)
{
this.columnName=columnName;
this.foreignTableName=foreignTableName;
this.foreignTableColumnName=foreignTableColumnName;
}


// setters
public void setColumnName(String columnName)
{
this.columnName=columnName;
}

public void setForeignTableName(String foreignTableName)
{
this.foreignTableName=foreignTableName;
}

public void setForeignTableColumnName(String foreignTableColumnName)
{
this.foreignTableColumnName=foreignTableColumnName;
}


// getters

public String getCurrentTableColumn()
{
return this.columnName;
}

public String getForeignTableName()
{
return this.foreignTableName;
}

public String getForeignTableColumnName()
{
return this.foreignTableColumnName;
}

// **** Following method helps to Collection classes
public boolean equals(Object other) // for set uses
{
if(!(other instanceof ForeignKeyInformation)) return false;
ForeignKeyInformation foreignKeyInformation=(ForeignKeyInformation)other;
return this.columnName.equals(foreignKeyInformation.columnName);
}

public int compareTo(ForeignKeyInformation foreignKeyInformation)
{
return this.columnName.compareTo(foreignKeyInformation.columnName);
}

public int hashCode()
{
return this.columnName.hashCode();
}
} // class braces ends


