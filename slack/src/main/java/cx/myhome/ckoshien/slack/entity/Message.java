package cx.myhome.ckoshien.slack.entity;

import java.io.Serializable;
import java.util.Date;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;


@Table(name="message")
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

    @Column(name="user_id")
    private String userId;

    @Column(name="channel_id")
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    private String channelId;

    @Column(name="posted_time")
    @PrimaryKey(generationType = GenerationType.APPLICATION)
    private Date postedTime;

    @Column(name="reaction_count")
    private Integer reactionCount;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public Date getPostedTime() {
		return postedTime;
	}

	public void setPostedTime(Date postedTime) {
		this.postedTime = postedTime;
	}

	public Integer getReactionCount() {
		return reactionCount;
	}

	public void setReactionCount(Integer reactionCount) {
		this.reactionCount = reactionCount;
	}


}


