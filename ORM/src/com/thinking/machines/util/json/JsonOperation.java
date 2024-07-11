package com.thinking.machines.util.json;
import java.io.*;
import com.google.gson.*;


public class JsonOperation
{

private JsonOperation()
{
// So that nobody makes object of this class
}

public static ConfigurationFile loadConfigFile() throws Exception
{
ConfigurationFile configurationFile=null;
try
{
Gson gson=new Gson();
File file=new File("config.json");
if(!file.exists())
{
System.out.println(file.getAbsolutePath()+" does not exists");
return configurationFile;
}
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
StringBuffer stringBuffer=new StringBuffer();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
stringBuffer.append((char)randomAccessFile.read());
}
String json=stringBuffer.toString();
configurationFile=gson.fromJson(json,ConfigurationFile.class);
randomAccessFile.close();
}catch(Exception exception)
{
throw exception;
}
return configurationFile;
}
}
