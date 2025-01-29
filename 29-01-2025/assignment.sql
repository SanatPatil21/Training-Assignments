create table employees(
	eid SERIAL PRIMARY KEY,
	name varchar(30) NOT NULL,
	age INT NOT NULL,
	salary numeric(8,2) check(salary > 0),
	designation varchar(50) NOT NULL,
	department varchar(50)
)

select * from employees;

delete from employees;

update employees set salary =  salary + 100 where eid = 7;