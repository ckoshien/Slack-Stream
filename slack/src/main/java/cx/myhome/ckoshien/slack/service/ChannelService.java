package cx.myhome.ckoshien.slack.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cx.myhome.ckoshien.slack.entity.Channel;
import jp.sf.amateras.mirage.ClasspathSqlResource;
import jp.sf.amateras.mirage.SqlManager;
import jp.sf.amateras.mirage.SqlResource;

@Service
@Transactional
public class ChannelService {
	@Autowired
	private SqlManager sqlManager;


	public Integer insert(Channel channelEntity){
		return sqlManager.insertEntity(channelEntity);
	}

	public Integer update(Channel channelEntity){
		return sqlManager.updateEntity(channelEntity);
	}
	public Integer delete(Channel channelEntity){
		return sqlManager.deleteEntity(channelEntity);
	}


	public Channel findById(String id){
		SqlResource selectChannelSql = new ClasspathSqlResource("selectChannelById.sql");
		HashMap<String, String> param=new HashMap<String, String>();
		param.put("id", id);
		return sqlManager.getSingleResult(Channel.class,selectChannelSql,param);
	}




}
