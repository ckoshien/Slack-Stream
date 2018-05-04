package cx.myhome.ckoshien.slack.dto;

import cx.myhome.ckoshien.slack.entity.Message;

public class MessageExDto extends Message{

	private static final long serialVersionUID = 1L;
	private String name;
	private Integer cnt1;
	private Integer cnt2;
	private Integer cnt3;
	private Integer cnt4;
	private Integer cnt5;
	private Integer cnt6;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCnt1() {
		return cnt1;
	}
	public void setCnt1(Integer cnt1) {
		this.cnt1 = cnt1;
	}
	public Integer getCnt2() {
		return cnt2;
	}
	public void setCnt2(Integer cnt2) {
		this.cnt2 = cnt2;
	}
	public Integer getCnt3() {
		return cnt3;
	}
	public void setCnt3(Integer cnt3) {
		this.cnt3 = cnt3;
	}
	public Integer getCnt4() {
		return cnt4;
	}
	public void setCnt4(Integer cnt4) {
		this.cnt4 = cnt4;
	}
	public Integer getCnt5() {
		return cnt5;
	}
	public void setCnt5(Integer cnt5) {
		this.cnt5 = cnt5;
	}
	public Integer getCnt6() {
		return cnt6;
	}
	public void setCnt6(Integer cnt6) {
		this.cnt6 = cnt6;
	}

}
