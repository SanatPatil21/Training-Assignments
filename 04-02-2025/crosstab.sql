CREATE TABLE orders (
    orderID SERIAL PRIMARY KEY,
    product_name VARCHAR(50) NOT NULL,
    customer VARCHAR(50) NOT NULL,
    quantity INT NOT NULL,
    date_of_purchase DATE DEFAULT CURRENT_DATE
);


drop table orders;

drop table sales;

select * from orders;

INSERT INTO orders (product_name, customer, quantity, date_of_purchase) VALUES
('Laptop', 'Maddy', 2, '2024-02-01'),
('Phone', 'Sumit', 1, '2024-02-02'),
('Roses', 'Maddy', 3, '2024-02-02'),
('Laptop', 'Bob', 1, '2024-02-03'),
('Phone', 'Jatin', 3, '2024-02-03');


-- Working with Sales data region wise

CREATE EXTENSION IF NOT EXISTS tablefunc;

CREATE TABLE sales (
    sale_id SERIAL PRIMARY KEY,
    product_name VARCHAR(50),
    region VARCHAR(50),
    sales_amount DECIMAL(10,2),
    sale_date DATE
);

INSERT INTO sales (product_name, region, sales_amount, sale_date) VALUES
('Laptop', 'East', 5000, '2024-01-10'),
('Phone', 'East', 3000, '2024-01-15'),
('Laptop', 'West', 7000, '2024-01-12'),
('Tablet', 'West', 4000, '2024-01-18'),
('Phone', 'North', 2500, '2024-01-20'),
('Tablet', 'South', 3200, '2024-01-25'),
('Laptop', 'South', 6200, '2024-01-28'),
('Phone', 'West', 2800, '2024-02-02'),
('Tablet', 'East', 4500, '2024-02-05'),
('Laptop', 'North', 5400, '2024-02-08'),
('Phone', 'South', 4100, '2024-02-10'),
('Tablet', 'North', 3300, '2024-02-12'),
('Laptop', 'West', 4800, '2024-02-14'),
('Phone', 'East', 3700, '2024-02-16'),
('Tablet', 'West', 2900, '2024-02-18'),
('Laptop', 'East', 7200, '2024-02-20');


select * from sales;

--Analyzing Region wise sales of Different Categories of Product 
select region,product_name, sum(sales_amount) as total_sales
from sales
group by region,product_name
order by region,product_name;

--Using CrossTab
--Putting names of product as cols 
--1. Need to get names of all Products

select product_name from sales
group by product_name;

SELECT * FROM crosstab(
    'SELECT region, product_name, SUM(sales_amount) 
     FROM sales 
     GROUP BY region, product_name 
     ORDER BY region, product_name'
) 
AS ct(region varchar(50), Laptop DECIMAL, Phone DECIMAL, Tablet DECIMAL);






