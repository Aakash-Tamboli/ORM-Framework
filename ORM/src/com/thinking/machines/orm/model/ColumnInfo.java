package com.thinking.machines.orm.model;
import com.thinking.machines.orm.util.foreignkey.*;

public class ColumnInfo
{
private ForeignKeyInformation foreignKeyInformation;
private boolean autoIncrement;
private boolean primaryKey;
private boolean notNull;
private boolean unique;

// As project requirement grows in future props will be increased as per requirement

public ColumnInfo()
{
this.foreignKeyInformation=null;
this.autoIncrement=false;
this.primaryKey=false;
this.notNull=false;
this.unique=false;
}


// setter 
public void setForeignKeyInformation(ForeignKeyInformation foreignKeyInformation)
{
this.foreignKeyInformation=foreignKeyInformation;
}

public void setAutoIncrement(boolean autoIncrement)
{
this.autoIncrement=autoIncrement;
}

public void setPrimaryKey(boolean primaryKey)
{
this.primaryKey=primaryKey;
}

public void setNotNull(boolean notNull)
{
this.notNull=notNull;
}

public void setUnique(boolean unique)
{
this.unique=unique;
}

// getter

public ForeignKeyInformation getForeignKeyInformation()
{
return this.foreignKeyInformation;
}

public boolean getAutoIncrement()
{
return this.autoIncrement;
}

public boolean getPrimaryKey()
{
return this.primaryKey;
}

public boolean getNotNull()
{
return this.notNull;
}

public boolean getUnique()
{
return this.unique;
}

// Some More method for better readability

public boolean hasNOTNULL()
{
return getNotNull();
}

public boolean hasPrimaryKey()
{
return getPrimaryKey();
}

public boolean hasAutoIncrement()
{
return getAutoIncrement();
}

public ForeignKeyInformation hasForeignKey()
{
return getForeignKeyInformation();
}

public boolean hasUnique()
{
return getUnique();
}

}// class braces close
