select
m.user_id,
m.channel_id,
c.name,
case
when field1.cnt is null then 0
else field1.cnt
end as today,
case
when field2.cnt is null then 0
else field2.cnt
end as yesterday,
case
when field1.cnt is null then -field2.cnt
when field2.cnt is null then field1.cnt
when field1.cnt is null and field2.cnt is null then 0
else field1.cnt-field2.cnt
end as increment,
case
when field1.cnt is null then field2.cnt
when field2.cnt is null then field1.cnt
when field1.cnt is null and field2.cnt is null then 0
else field1.cnt+field2.cnt
end as sum
from message m
left outer join
(select
count(*) as cnt,
m.channel_id
 from message m
where m.posted_time>=/*today*/ and m.posted_time</*tomorrow*/
group by m.channel_id) field1
on field1.channel_id=m.channel_id
left outer join
(select
count(*) as cnt,
m.channel_id
from message m
where m.posted_time>=/*yesterday*/ and m.posted_time</*today*/
group by m.channel_id) field2
on field2.channel_id=m.channel_id
inner join channel c
on c.id=m.channel_id
group by m.channel_id
having sum>2
order by sum desc,increment desc,today desc
limit 10