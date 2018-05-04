select
m.user_id,
m.channel_id,
c.name,
case when field1.cnt is null then 0 else field1.cnt end cnt1,
case when field2.cnt is null then 0 else field2.cnt end cnt2,
case when field3.cnt is null then 0 else field3.cnt end cnt3,
case when field4.cnt is null then 0 else field4.cnt end cnt4,
case when field5.cnt is null then 0 else field5.cnt end cnt5,
case when field6.cnt is null then 0 else field6.cnt end cnt6,
case
 when field1.cnt is null then field2.cnt
 when field2.cnt is null then field1.cnt
 else field1.cnt+field2.cnt
 end
 as sum
from message m

left outer join
(select
count(*) as cnt,
m.channel_id
 from message m
where m.posted_time>=/*date2*/ and m.posted_time</*date1*/
group by m.channel_id) field1
on field1.channel_id=m.channel_id

left outer join
(select
count(*) as cnt,
m.channel_id
 from message m
where m.posted_time>=/*date3*/ and m.posted_time</*date2*/
group by m.channel_id) field2
on field2.channel_id=m.channel_id

left outer join
(select
count(*) as cnt,
m.channel_id
 from message m
where m.posted_time>=/*date4*/ and m.posted_time</*date3*/
group by m.channel_id) field3
on field3.channel_id=m.channel_id

left outer join
(select
count(*) as cnt,
m.channel_id
 from message m
where m.posted_time>=/*date5*/ and m.posted_time</*date4*/
group by m.channel_id) field4
on field4.channel_id=m.channel_id

left outer join
(select
count(*) as cnt,
m.channel_id
 from message m
where m.posted_time>=/*date6*/ and m.posted_time</*date5*/
group by m.channel_id) field5
on field5.channel_id=m.channel_id

left outer join
(select
count(*) as cnt,
m.channel_id
 from message m
where m.posted_time>=/*date7*/ and m.posted_time</*date6*/
group by m.channel_id) field6
on field6.channel_id=m.channel_id

inner join channel c
on c.id=m.channel_id

group by m.channel_id
having sum>0
order by sum desc
limit 10