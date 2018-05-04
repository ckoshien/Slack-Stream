package cx.myhome.ckoshien.slack.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cx.myhome.ckoshien.slack.dto.IncrementDto;
import cx.myhome.ckoshien.slack.dto.MessageExDto;
import cx.myhome.ckoshien.slack.entity.Channel;
import cx.myhome.ckoshien.slack.entity.Message;
import jp.sf.amateras.mirage.ClasspathSqlResource;
import jp.sf.amateras.mirage.SqlManager;
import jp.sf.amateras.mirage.SqlResource;

@Service
@Transactional
public class MessageService {
	@Autowired
	private SqlManager sqlManager;


	public Integer insert(Message messageEntity){
		return sqlManager.insertEntity(messageEntity);
	}

	public Integer update(Message messageEntity){
		return sqlManager.updateEntity(messageEntity);
	}
	public Integer delete(Message messageEntity){
		return sqlManager.deleteEntity(messageEntity);
	}


	public Message findByPK(String id,Date postedTime){
		SqlResource selectChannelSql = new ClasspathSqlResource("selectMessageByPK.sql");
		HashMap<String, Object> param=new HashMap<String, Object>();
		param.put("id", id);
		param.put("postedTime", postedTime);
		return sqlManager.getSingleResult(Message.class,selectChannelSql,param);
	}

	public List<IncrementDto> findIncrementMessage(Date date){
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		Date tomorrow = cal.getTime();
		Calendar cal2=Calendar.getInstance();
		cal2.setTime(date);
		cal2.add(Calendar.DATE, -1);
		Date yesterday=cal2.getTime();

		SqlResource selectChannelSql = new ClasspathSqlResource("selectMessageIncrement.sql");
		HashMap<String, Object> param=new HashMap<String, Object>();
		param.put("today", date);
		param.put("tomorrow", tomorrow);
		param.put("yesterday", yesterday);

		return sqlManager.getResultList(IncrementDto.class,selectChannelSql,param);
	}

	public List<MessageExDto> findNameByPeriod(){
		SqlResource selectChannelSql = new ClasspathSqlResource("selectChannelByPeriod.sql");
		HashMap<String,Date> param=new HashMap<String, Date>();
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Calendar cal2=Calendar.getInstance();
		cal2.setTime(new Date());
		cal2.add(Calendar.DATE, -2);
		cal2.set(Calendar.HOUR_OF_DAY, 0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0);
		Calendar cal3=Calendar.getInstance();
		cal3.setTime(new Date());
		cal3.add(Calendar.DATE, -3);
		cal3.set(Calendar.HOUR_OF_DAY, 0);
		cal3.set(Calendar.MINUTE, 0);
		cal3.set(Calendar.SECOND, 0);
		Calendar cal4=Calendar.getInstance();
		cal4.setTime(new Date());
		cal4.add(Calendar.DATE, -4);
		cal4.set(Calendar.HOUR_OF_DAY, 0);
		cal4.set(Calendar.MINUTE, 0);
		cal4.set(Calendar.SECOND, 0);
		Calendar cal5=Calendar.getInstance();
		cal5.setTime(new Date());
		cal5.add(Calendar.DATE, -5);
		cal5.set(Calendar.HOUR_OF_DAY, 0);
		cal5.set(Calendar.MINUTE, 0);
		cal5.set(Calendar.SECOND, 0);
		Calendar cal6=Calendar.getInstance();
		cal6.setTime(new Date());
		cal6.add(Calendar.DATE, -6);
		cal6.set(Calendar.HOUR_OF_DAY, 0);
		cal6.set(Calendar.MINUTE, 0);
		cal6.set(Calendar.SECOND, 0);
		Calendar cal7=Calendar.getInstance();
		cal7.setTime(new Date());
		cal7.add(Calendar.DATE, -7);
		cal7.set(Calendar.HOUR_OF_DAY, 0);
		cal7.set(Calendar.MINUTE, 0);
		cal7.set(Calendar.SECOND, 0);
		param.put("date1", cal.getTime());
		param.put("date2", cal2.getTime());
		param.put("date3", cal3.getTime());
		param.put("date4", cal4.getTime());
		param.put("date5", cal5.getTime());
		param.put("date6", cal6.getTime());
		param.put("date7", cal7.getTime());
		return sqlManager.getResultList(MessageExDto.class,selectChannelSql,param);
	}


}