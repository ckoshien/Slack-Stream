package cx.myhome.ckoshien.slack.entity;

import java.io.Serializable;
import java.util.Date;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;


@Table(name="channel")
public class Channel implements Serializable {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@PrimaryKey(generationType = GenerationType.APPLICATION)
	@Column(name="id")
    private String id;

    @Column(name="name")
    private String name;

    @Column(name="member_count")
    private Integer memberCount;

    @Column(name="last_check_date")
    private Date lastCheckDate;

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

	public Integer getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(Integer memberCount) {
		this.memberCount = memberCount;
	}

	public Date getLastCheckDate() {
		return lastCheckDate;
	}

	public void setLastCheckDate(Date lastCheckDate) {
		this.lastCheckDate = lastCheckDate;
	}



}


