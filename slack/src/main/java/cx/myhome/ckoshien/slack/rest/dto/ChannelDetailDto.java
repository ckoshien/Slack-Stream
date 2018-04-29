package cx.myhome.ckoshien.slack.rest.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChannelDetailDto {
	private String id;
	private String name;
	private Integer num_members;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNum_members() {
		return num_members;
	}
	public void setNum_members(Integer num_members) {
		this.num_members = num_members;
	}

}
