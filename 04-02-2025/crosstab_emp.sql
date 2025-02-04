select * from employees;

CREATE EXTENSION IF NOT EXISTS tablefunc;


select designation,department,sum(salary) as total_salary
from employees
group by department,designation

--
SELECT * FROM crosstab(
    'SELECT department, age::TEXT, SUM(salary) 
     FROM employees 
     GROUP BY department, age 
     ORDER BY department, age'
) 
AS ct(department varchar, "23" DECIMAL, "34" DECIMAL);

--Age Distribution of Employees along departments
SELECT * FROM crosstab(
    'SELECT department, 
            CASE 
                WHEN age BETWEEN 20 AND 25 THEN ''20-25'' 
                WHEN age BETWEEN 26 AND 30 THEN ''26-30'' 
                WHEN age BETWEEN 31 AND 40 THEN ''31-40'' 
                ELSE ''40+'' 
            END AS age_group, COUNT(*) 
     FROM employees 
     GROUP BY department, age_group 
     ORDER BY department, age_group'
) 
AS ct(department varchar, "20-25" BIGINT, "26-30" BIGINT, "31-40" BIGINT, "40+" BIGINT);



--Average salary given to designation in each department
SELECT * FROM crosstab(
    'SELECT department, designation, sum(salary)
     FROM employees 
     GROUP BY department, designation 
     ORDER BY department, designation',
	 'SELECT DISTINCT designation FROM employees ORDER BY designation'
) 
AS ct(department varchar, "Manager" DECIMAL, "Programmer" DECIMAL, "Sales Executive" DECIMAL, "Tester" DECIMAL,"Sales Rep" DECIMAL);






