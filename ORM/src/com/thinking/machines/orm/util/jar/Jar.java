package com.thinking.machines.orm.util.jar;
import com.thinking.machines.orm.exception.*;
import java.io.*;
import java.util.*;
import java.util.jar.*;

public class Jar
{

public static void makeJar(String pathToCompiledCode,String jarFileFolder) throws DataException
{
File file=null;
FileOutputStream fileOutputStream=null;
JarOutputStream jarOutputStream=null;
Stack<File> stack=new Stack<>();
File dir=null;
File []files=null;
String entryName=null;
byte []buffer=null;
int bytesRead=0;
JarEntry jarEntry=null;
FileInputStream fileInputStream=null;

file=new File(jarFileFolder);

if(!file.exists())
{
if(!file.mkdirs()) throw new DataException("Unable to generate Jar Files"); 
}

jarFileFolder=jarFileFolder+File.separator+"pojos.jar";


try
{

fileOutputStream=new FileOutputStream(jarFileFolder);
jarOutputStream=new JarOutputStream(fileOutputStream);

file=new File(pathToCompiledCode);

stack = new Stack<>();

int basePathLength = file.getPath().length() + 1;

stack.push(file);

while(!stack.isEmpty())
{
dir=stack.pop();

files=dir.listFiles();

if(files!=null)
{

for(File file1: files)
{
if(file1.isDirectory())
{
stack.push(file1);
}
else
{
entryName=file1.getPath().substring(basePathLength).replace("\\", "/");

jarEntry=new JarEntry(entryName);

jarOutputStream.putNextEntry(jarEntry);

try
{
fileInputStream=new FileInputStream(file1);
buffer=new byte[1024];
while((bytesRead = fileInputStream.read(buffer)) != -1)
{
jarOutputStream.write(buffer,0,bytesRead);
} // loop ends
}catch(Exception exception)
{
throw new DataException("Unable to create Jar file");
}
finally
{
if(fileInputStream!=null) fileInputStream.close();
}

jarOutputStream.closeEntry();
} // else block ends
} // loop ends
} // if block ends
}// loop ends

}catch(Exception exception)
{
throw new DataException("Unable to create Jar file");
}
finally
{
try
{
if(jarOutputStream!=null) jarOutputStream.close();
if(fileOutputStream!=null) fileOutputStream.close();
}catch(Exception exception)
{
// not yet decided
}
}
} // method braces ends
} // class braces ends
