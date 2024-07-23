PreparedSQLStatement.java -

It is person which is responsible to generate SQL Format statements
on behalf of ColumnsData

PreparedSQLStatement provides following services -
- forInsert 
	It will generate insert statement on behalf of list of columnsData
	eg: insert into table_name (title,fee) values("Java",7000)

- toCheck
	It will generate sql statement to check existence of anthing in table
	select title from table_name where title=value
	it returns true it exists otherwise false
