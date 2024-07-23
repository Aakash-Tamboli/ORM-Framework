package com.thinking.machines.orm.testcases.pojos;
import com.thinking.machines.orm.annotation.*;

@Table(name="course")
public class Course
{
@PrimaryKey
@AutoIncrement
@NOTNULL
@Unique
@Column(name="code")
public int code;


@NOTNULL
@Unique
@Column(name="title")
public String title;


}