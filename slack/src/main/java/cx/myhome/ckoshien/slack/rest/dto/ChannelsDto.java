package cx.myhome.ckoshien.slack.rest.dto;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChannelsDto {

	private List<ChannelDetailDto> channels;

	public List<ChannelDetailDto> getChannels() {
		return channels;
	}

	public void setChannels(List<ChannelDetailDto> channels) {
		this.channels = channels;
	}
}
