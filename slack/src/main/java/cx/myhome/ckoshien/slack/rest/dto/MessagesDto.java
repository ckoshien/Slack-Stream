package cx.myhome.ckoshien.slack.rest.dto;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessagesDto {
	private List<PostDto> messages;

	public List<PostDto> getMessages() {
		return messages;
	}

	public void setMessages(List<PostDto> messages) {
		this.messages = messages;
	}

}
