package cx.myhome.ckoshien.slack.dto;

import cx.myhome.ckoshien.slack.entity.Message;

public class IncrementDto extends Message{
	private String name;
	private Integer today;
	private Integer yesterday;
	private Integer increment;
	private Integer sum;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getToday() {
		return today;
	}
	public void setToday(Integer today) {
		this.today = today;
	}
	public Integer getYesterday() {
		return yesterday;
	}
	public void setYesterday(Integer yesterday) {
		this.yesterday = yesterday;
	}
	public Integer getIncrement() {
		return increment;
	}
	public void setIncrement(Integer increment) {
		this.increment = increment;
	}
	public Integer getSum() {
		return sum;
	}
	public void setSum(Integer sum) {
		this.sum = sum;
	}

}
