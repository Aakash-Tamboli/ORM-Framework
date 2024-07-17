package com.thinking.machines.orm.util.string;

public class StringUtility
{
private StringUtility()
{
// so that nobody try to make object by mistake
}

public static String ConvertIntoClassName(String tableName)
{
String className="";
StringBuilder stringBuilder=null;
if(!isUnderscorePresent(tableName))
{
className=capitalize(tableName);
}
else
{
int i=0;
char [] vTableName=tableName.toCharArray();
stringBuilder=new StringBuilder();
while(i<vTableName.length)
{
while(i<vTableName.length && vTableName[i]=='_') i++;
if(i==vTableName.length) break;
stringBuilder.append((vTableName[i++]+"").toUpperCase());
while(i<vTableName.length && vTableName[i]!='_') stringBuilder.append(vTableName[i++]);
}
className=stringBuilder.toString();
}
return className;
}

public static String ConvertIntoVariableName(String fieldName)
{
String variableName="";
StringBuilder stringBuilder=null;
if(!isUnderscorePresent(fieldName))
{
variableName=uncapitalize(fieldName); // Name -> name
}
else
{
// camelize
int i=0;
char []vFieldName=fieldName.toCharArray();
stringBuilder=new StringBuilder();
boolean firstAlphabet=true;

while(i<vFieldName.length)
{
while(i<vFieldName.length && vFieldName[i]=='_') i++; // partition point logic helps
if(i==vFieldName.length) break;
if(firstAlphabet)
{
stringBuilder.append((vFieldName[i++]+"").toLowerCase()); // + < .(dot) '.' precedence is higher than '+'
firstAlphabet=false;
}
else
{
stringBuilder.append((vFieldName[i++]+"").toUpperCase());
}
while(i<vFieldName.length && vFieldName[i]!='_') stringBuilder.append(vFieldName[i++]); // I just concern about '_' on every underscore our algo will change into camel case
}
variableName=stringBuilder.toString();
}
return variableName;
}


private static boolean isUnderscorePresent(String str)
{
return str.indexOf("_")!=-1;
}
private static String capitalize(String str)
{
return str.substring(0,1).toUpperCase()+str.substring(1);
}
private static String uncapitalize(String str)
{
return str.substring(0,1).toLowerCase()+str.substring(1);
}

public static String stringFormOfValue(Object value,Class type)
{
Long l=null;
Integer i=null;
Short s=null;
Byte b=null;
Double d=null;
Float f=null;
Character ch=null;
Boolean bool=null;
String str=null;
Class dataType=null;
java.sql.Date date=null;
short counter=1;

if(type.equals(Long.class))
{
l=(Long)value;
return l.toString();
}
else if(type.equals(Integer.class))
{
i=(Integer)value;
return i.toString();
}
else if(type.equals(Short.class))
{
s=(Short)value;
return s.toString();
}
else if(type.equals(Byte.class))
{
b=(Byte)value;
return b.toString();
}
else if(type.equals(Double.class))
{
d=(Double)value;
return d.toString();
}
else if(type.equals(Float.class))
{
f=(Float)value;
return f.toString();
}
else if(type.equals(Character.class))
{
ch=(Character)value;
return "\""+ch.toString()+"\"";
}
else if(type.equals(String.class))
{
str=(String)value;
return "\""+str+"\"";
}
else if(type.equals(Boolean.class))
{
bool=(Boolean)value;
return bool.toString();
}
else if(type.equals(java.sql.Date.class))
{
date=(java.sql.Date)value;
return "\""+date.toString()+"\"";
}
return "";
} // method braces close
} // class braces close
