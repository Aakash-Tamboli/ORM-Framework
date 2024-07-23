package com.thinking.machines.orm.util.json;

public class ConfigurationFile
{
private String JDBCDriver;
private String connectionURL;
private String username;
private String password;
private String packaging;

public ConfigurationFile()
{
this.JDBCDriver=null;
this.connectionURL=null;
this.username=null;
this.password=null;
this.packaging=null;
}

// setter starts
public void setJDBCDriver(String JDBCDriver)
{
this.JDBCDriver=JDBCDriver;
}

public void setConnectionURL(String connectionURL)
{
this.connectionURL=connectionURL;
}

public void setUsername(String username)
{
this.username=username;
}

public void setPassword(String password)
{
this.password=password;
}

public void setPackaging(String packaging)
{
this.packaging=packaging;
}

// setter ends
// getter starts
public String getJDBCDriver()
{
return this.JDBCDriver;
}
public String getConnectionURL()
{
return this.connectionURL;
}
public String getUsername()
{
return this.username=username;
}
public String getPassword()
{
return this.password;
}
public String getPackaging()
{
return this.packaging;
}
// getter ends
} // class ends
