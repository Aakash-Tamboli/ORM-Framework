package com.thinking.machines.orm.util.compiler;
import com.thinking.machines.orm.exception.*;

import javax.tools.*;
import java.io.*;
import java.util.*;
import java.net.*;

public class Compiler
{

public static void compile(String pathToSourceCode,String pathToCompiledCode) throws DataException
{
JavaCompiler javaCompiler=null;
StandardJavaFileManager standardJavaFileManager=null;
File []files=null;
List<JavaFileObject> javaFileObjects=null;
JavaCompiler.CompilationTask task=null;
boolean success=false;


File file=null;

file=new File(pathToCompiledCode);

if(!file.exists())
{
if(!file.mkdirs()) throw new DataException("Unable to create folder to compiler all the files");
}


try
{

javaCompiler=ToolProvider.getSystemJavaCompiler();
if(javaCompiler==null)
{
throw new DataException("Java compiler is not available. Ensure you are using a JDK, not a JRE.");
}

standardJavaFileManager=javaCompiler.getStandardFileManager(null,null,null);

standardJavaFileManager.setLocation(StandardLocation.CLASS_OUTPUT,Arrays.asList(new File(pathToCompiledCode)));

files = new File(pathToSourceCode).listFiles((dir, name) -> name.endsWith(".java"));
        
if(files == null) 
{
throw new DataException("No files found in: "+pathToSourceCode);
}

javaFileObjects = new ArrayList<>();
for (File file1 : files) 
{
javaFileObjects.add(new SimpleJavaFileObject(file1.toURI(), JavaFileObject.Kind.SOURCE) 
{
public CharSequence getCharContent(boolean ignoreEncodingErrors) 
{
try 
{
return new String(java.nio.file.Files.readAllBytes(file1.toPath()));
} 
catch (IOException ioException) 
{
throw new RuntimeException(ioException);
}
}
});
}

task=javaCompiler.getTask(null,standardJavaFileManager,null,null,null,javaFileObjects);

success=task.call();

standardJavaFileManager.close();

if(!success) throw new DataException("Compilation failed.");

}catch(DataException dataException)
{
throw dataException;
}
catch(Exception exception)
{
throw new DataException(exception.getMessage());
}
} // method braces close
} // class braces ends
