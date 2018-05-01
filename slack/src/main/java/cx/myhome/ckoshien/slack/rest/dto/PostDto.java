package cx.myhome.ckoshien.slack.rest.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PostDto {
	private Double ts;
	private String type;
	private String user;
	private String subtype;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Double getTs() {
		return ts;
	}
	public void setTs(Double ts) {
		this.ts = ts;
	}
	public String getSubtype() {
		return subtype;
	}
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}
}
