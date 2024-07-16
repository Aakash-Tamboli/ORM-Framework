package com.thinking.machines.orm.util.json;

public class ConfigurationFile
{
private String jdbcDriver;
private String connectionUrl;
private String username;
private String password;

public ConfigurationFile()
{
this.jdbcDriver="";
this.connectionUrl="";
this.username="";
this.password="";
}

// setter starts
public void setJdbcDriver(String jdbcDriver)
{
this.jdbcDriver=jdbcDriver;
}

public void setConnectionUrl(String connectionUrl)
{
this.connectionUrl=connectionUrl;
}

public void setUsername(String username)
{
this.username=username;
}

public void setPassword(String password)
{
this.password=password;
}

// setter ends
// getter starts
public String getJdbcDriver()
{
return this.jdbcDriver;
}
public String getConnectionUrl()
{
return this.connectionUrl=connectionUrl;
}
public String getUsername()
{
return this.username=username;
}
public String getPassword()
{
return this.password;
}
// getter ends
} // class ends
