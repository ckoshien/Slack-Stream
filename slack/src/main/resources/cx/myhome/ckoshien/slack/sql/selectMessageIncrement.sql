select
m.user_id,
m.channel_id,
c.name,
field1.cnt as today,
field2.cnt as yesterday,
case
when field1.cnt is null then -field2.cnt
when field2.cnt is null then field1.cnt
when field1.cnt is null and field2.cnt is null then 0
else field1.cnt-field2.cnt
end increment
from message m
left outer join
(select count(*) as cnt,
m.channel_id
 from message m
where m.posted_time>='2018-04-29' and m.posted_time<'2018-04-30'
group by m.channel_id) field1
on field1.channel_id=m.channel_id
left outer join
(select count(*) as cnt,
m.channel_id
from message m
where m.posted_time>='2018-04-28' and m.posted_time<'2018-04-29'
group by m.channel_id) field2
on field2.channel_id=m.channel_id
inner join channel c
on c.id=m.channel_id
group by m.channel_id
-- order by increment desc
order by today desc