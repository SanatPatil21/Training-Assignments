create table inventory(
	item_id SERIAL PRIMARY KEY,
	item_information jsonb
);

drop table inventory;

select * from inventory;

delete from inventory;


select item_information  from inventory
where item_information->'product_id'='124';

DELETE from inventory where item_information->'product_id'=''"123"'';
