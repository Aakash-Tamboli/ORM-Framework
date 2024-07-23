package com.thinking.machines.orm.util.validator;
import java.lang.reflect.*;

public class Validator
{
public static boolean isValidType(Object object)
{
if(object instanceof Long ||
object instanceof Integer ||
object instanceof Short ||
object instanceof Byte ||
object instanceof Double ||
object instanceof Float ||
object instanceof Character ||
object instanceof Boolean ||
object instanceof String ||
object instanceof java.sql.Date ||
object == null // It makes sense when user doesn't set any value against column and column also take null values
)
{
return true;
}
else
{
return false;
}
}
public static Class whatTypeOf(Object object)
{
if(object instanceof Long) return Long.class;
else if(object instanceof Integer) return Integer.class;
else if(object instanceof Short) return Short.class;
else if(object instanceof Byte) return Byte.class;
else if(object instanceof Double) return Double.class;
else if(object instanceof Float) return Float.class;
else if(object instanceof Character) return Character.class;
else if(object instanceof Boolean) return boolean.class;
else if(object instanceof String) return String.class;
else if(object instanceof java.sql.Date) return java.sql.Date.class;
return null;
}
}
