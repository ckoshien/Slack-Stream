package cx.myhome.ckoshien.slack.task;

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

import cx.myhome.ckoshien.slack.entity.Channel;
import cx.myhome.ckoshien.slack.entity.Message;
import cx.myhome.ckoshien.slack.rest.RestClient;
import cx.myhome.ckoshien.slack.rest.dto.ChannelsDto;
import cx.myhome.ckoshien.slack.rest.dto.MessagesDto;
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

	@Scheduled(cron = "0 11 23 * * ?")
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
				addMessages(oldChannel);
				oldChannel.setLastCheckDate(new Date());
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



//		Calendar cal=Calendar.getInstance();
//		cal.setTime(new Date());
//		String currentMonth = cal.get(Calendar.YEAR)+""+String.format("%02d", cal.get(Calendar.MONTH)+1);
//		pool = Executors.newFixedThreadPool(8);
//		List<Future<String>> processList = new ArrayList<Future<String>>();
//		for(int i=11;i<=100;i++){
//			@SuppressWarnings("unchecked")
//			Future<String> future=pool.submit(new CallableBackUpResult(i,String.valueOf(i),currentMonth,rawMarkingService,songService,batchService));
//			processList.add(future);
//		}
//		for (Future<String> future : processList) {
//			   try {
//			       String id = future.get();
//			   } catch (InterruptedException e) {
//			       logger.error("ERROR:",e);
//			   } catch (ExecutionException e) {
//				   logger.error("ERROR:",e);
//			   }
//		}
		logger.info("タスク終了");

	}
	public void addMessages(Channel channel){
		String channelUri="https://slack.com/api/channels.history?token="+token+"&channel="+channel.getId();
		if(channel.getLastCheckDate()!=null){
			channelUri=channelUri+"&oldest="+channel.getLastCheckDate().getTime();
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
	}

}
