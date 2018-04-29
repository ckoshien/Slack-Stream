package cx.myhome.ckoshien.slack.service;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


}