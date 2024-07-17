package com.thinking.machines.orm.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ForeignKey
{
String parent();
String column();
} 
