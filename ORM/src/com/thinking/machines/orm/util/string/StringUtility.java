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
}


