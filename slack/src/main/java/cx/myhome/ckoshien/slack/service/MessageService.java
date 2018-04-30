package cx.myhome.ckoshien.slack.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cx.myhome.ckoshien.slack.dto.IncrementDto;
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


	public Channel findById(String id){
		SqlResource selectChannelSql = new ClasspathSqlResource("selectChannelById.sql");
		HashMap<String, String> param=new HashMap<String, String>();
		param.put("id", id);
		return sqlManager.getSingleResult(Channel.class,selectChannelSql,param);
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


}