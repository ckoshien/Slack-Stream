select count(*),
	hour(m.posted_time),
	dayofweek(m.posted_time)
 from
message m
group by hour(m.posted_time),dayofweek(m.posted_time)
-- order by dayofweek(m.posted_time),hour(m.posted_time)
order by count(*)desc