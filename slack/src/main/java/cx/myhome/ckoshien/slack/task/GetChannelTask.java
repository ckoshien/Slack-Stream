package cx.myhome.ckoshien.slack.task;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import cx.myhome.ckoshien.slack.dto.IncrementDto;
import cx.myhome.ckoshien.slack.entity.Channel;
import cx.myhome.ckoshien.slack.entity.Message;
import cx.myhome.ckoshien.slack.rest.RestClient;
import cx.myhome.ckoshien.slack.rest.dto.ChannelsDto;
import cx.myhome.ckoshien.slack.rest.dto.MessagesDto;
import cx.myhome.ckoshien.slack.rest.dto.SlackDto;
import cx.myhome.ckoshien.slack.service.ChannelService;
import cx.myhome.ckoshien.slack.service.MessageService;

@Service
public class GetChannelTask {

	//private ExecutorService pool;
	@Autowired
	protected ChannelService channelService;
	@Autowired
	protected MessageService messageService;

	private Logger logger=LoggerFactory.getLogger(GetChannelTask.class);

	@Value("${slack.token}")
	private String token;
	@Value("${slack.uri}")
	private String slackUri;

	@Scheduled(cron = "0 0 7 * * ?")
	public void doTask(){
		logger.info("タスク開始");
		RestClient client = new RestClient();
		String uri = "https://slack.com/api/channels.list?token="+token;
		HashMap<String, String> header=new HashMap<String, String>();
		ChannelsDto json=client.sendRequest(uri, "GET", null, ChannelsDto.class,header);
		for(int i=0;i<json.getChannels().size();i++){
			//存在チェック
			Channel oldChannel = channelService.findById(json.getChannels().get(i).getId());
			if(oldChannel!=null){
				oldChannel.setName(json.getChannels().get(i).getName());
				oldChannel.setMemberCount(json.getChannels().get(i).getNum_members());
				Date lastPostDate=addMessages(oldChannel);
				if(lastPostDate!=null){
					oldChannel.setLastCheckDate(lastPostDate);
				}
				channelService.update(oldChannel);
			}else{
				Channel channelEntity=new Channel();
				channelEntity.setId(json.getChannels().get(i).getId());
				channelEntity.setMemberCount(json.getChannels().get(i).getNum_members());
				channelEntity.setName(json.getChannels().get(i).getName());
//				channelEntity.setLastCheckDate(new Date());
				channelService.insert(channelEntity);
				addMessages(channelEntity);
			}
		}
		//テスト用
		Date date = null;
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//		try {
//			date=sdf.parse("2018-04-29");
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -1);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		date=cal.getTime();
		List<IncrementDto> resultList = messageService.findIncrementMessage(date);
		StringBuilder sb=new StringBuilder();
		sb.append("slack_stream_botです。\n");
		sb.append(cal.get(Calendar.YEAR)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"の流量計：\n");
		for(int i=0;i<resultList.size();i++){
			//System.out.println(resultList.get(i).getName()+" "+resultList.get(i).getToday()+" "+resultList.get(i).getYesterday());
			sb.append(":marshmallow_not_flying:#"+resultList.get(i).getName()+":\n");
			sb.append("2日間合計："+resultList.get(i).getSum()+"(");
			sb.append("本日:"+resultList.get(i).getToday()+"posts,");
			sb.append("昨日:"+resultList.get(i).getYesterday()+"posts),");
			if(resultList.get(i).getIncrement()>0){
				sb.append("増加数:arrow_upper_right:："+resultList.get(i).getIncrement()+"\n");
			}else{
				sb.append("増加数："+resultList.get(i).getIncrement()+"\n");
			}

		}
		client = new RestClient();
		header= new HashMap<String, String>();
//		SlackDto entity=new SlackDto();
//		entity.setText(new String(sb));
//		entity.setUsername("SlackStreamBot");
		try {
			slackUri=slackUri+"&text="+URLEncoder.encode(new String(sb), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		String jsonResult = client.sendRequest(slackUri, "GET", null, String.class,header);
		logger.info(jsonResult.toString());
		logger.info("タスク終了");

	}
	public Date addMessages(Channel channel){
		String channelUri="https://slack.com/api/channels.history?token="+token+"&channel="+channel.getId();
		if(channel.getLastCheckDate()!=null){
			channelUri=channelUri+"&oldest="+channel.getLastCheckDate().getTime()/1000;
		}
		RestClient client=new RestClient();
		HashMap<String, String> header=new HashMap<String, String>();
		MessagesDto json=client.sendRequest(channelUri, "GET", null, MessagesDto.class,header);
		for(int j=0;j<json.getMessages().size();j++){
			if(null==json.getMessages().get(j).getSubtype() && json.getMessages().get(j).getType().equals("message")){
				Message message=new Message();
				message.setChannelId(channel.getId());
				message.setPostedTime(new Date((long) (1000*json.getMessages().get(j).getTs())));
				message.setUserId(json.getMessages().get(j).getUser());
				try{
					messageService.insert(message);
				}catch(Exception e){
					logger.error("ERROR:",e);
				}
			}

		}
		if(json.getMessages().size()>0){
			return new Date((long) (1000*json.getMessages().get(0).getTs()));
		}else{
			return null;
		}
	}

}
