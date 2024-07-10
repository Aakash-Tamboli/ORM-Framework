package com.thinking.machines.util.foreignkey;

public class ForeignKeyInformation implements Comparable<ForeignKeyInformation>
{
public String currentTableColumn;
public String foreignTableName;
public String foreignTableColumnName;

public ForeignKeyInformation()
{
this.currentTableColumn=null;
this.foreignTableName=null;
this.foreignTableColumnName=null;
}

public ForeignKeyInformation(String currentTableColumn,String foreignTableName,String foreignTableColumnName)
{
this.currentTableColumn=currentTableColumn;
this.foreignTableName=foreignTableName;
this.foreignTableColumnName=foreignTableColumnName;
}

public boolean equals(Object other) // for set uses
{
if(!(other instanceof ForeignKeyInformation)) return false;
ForeignKeyInformation foreignKeyInformation=(ForeignKeyInformation)other;
return this.currentTableColumn.equals(foreignKeyInformation.currentTableColumn);
}

public int compareTo(ForeignKeyInformation foreignKeyInformation)
{
return this.currentTableColumn.compareTo(foreignKeyInformation.currentTableColumn);
}

public int hashCode()
{
return this.currentTableColumn.hashCode();
}
}


