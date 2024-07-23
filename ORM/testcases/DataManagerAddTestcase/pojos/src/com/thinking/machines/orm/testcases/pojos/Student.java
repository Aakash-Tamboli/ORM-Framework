package com.thinking.machines.orm.testcases.pojos;
import com.thinking.machines.orm.annotation.*;

@Table(name="student")
public class Student
{
@PrimaryKey
@NOTNULL
@Unique
@Column(name="roll_number")
public int rollNumber;


@NOTNULL
@Column(name="first_name")
public String firstName;


@NOTNULL
@Column(name="last_name")
public String lastName;


@NOTNULL
@Unique
@Column(name="aadhar_card_number")
public String aadharCardNumber;


@NOTNULL
@ForeignKey(parent="course",column="code")
@Column(name="course_code")
public int courseCode;


@NOTNULL
@Column(name="gender")
public char gender;


@NOTNULL
@Column(name="date_of_birth")
public java.sql.Date dateOfBirth;


}