package com.thinking.machines.orm.util.fieldwrapper;
import com.thinking.machines.orm.exception.*;
import java.lang.reflect.*;

public class FieldWrapper
{
public static Object get(Field field,Object userObject) throws DataException
{
Object value=null;
try
{
value=field.get(userObject);
}catch(IllegalAccessException illegalAccessException) // checked exception
{
throw new DataException("All The props whose representing column must be public hence: "+field.getName()+" unable to access by ORM framework");
}
return value;
}
}
