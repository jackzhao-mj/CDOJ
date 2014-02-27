package cn.edu.uestc.acmicpc.db.dto.impl.contest;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.sql.Timestamp;
import java.util.Map;

/**
 * DTO used in contest list.
 * <br/>
 * <code>@Fields({ "contestId", "title", "type", "time", "length", "isVisible" })</code>
 */
@Fields({"contestId", "title", "type", "time", "length", "isVisible"})
public class ContestListDTO implements BaseDTO<Contest> {

  private Integer contestId;
  private String title;
  private String description;
  private Byte type;
  private Timestamp time;
  private Integer length;
  private Boolean isVisible;

  public ContestListDTO() {
  }

  public ContestListDTO(Integer contestId, String title, String description,
                        Byte type, Timestamp time, Integer length, Boolean isVisible) {
    this.contestId = contestId;
    this.title = title;
    this.description = description;
    this.type = type;
    this.time = time;
    this.length = length;
    this.isVisible = isVisible;
  }

  public Integer getContestId() {
    return contestId;
  }

  public void setContestId(Integer contestId) {
    this.contestId = contestId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Byte getType() {
    return type;
  }

  public void setType(Byte type) {
    this.type = type;
  }

  public Timestamp getTime() {
    return time;
  }

  public void setTime(Timestamp time) {
    this.time = time;
  }

  public Integer getLength() {
    return length;
  }

  public void setLength(Integer length) {
    this.length = length;
  }

  public Boolean getIsVisible() {
    return isVisible;
  }

  public void setIsVisible(Boolean isVisible) {
    this.isVisible = isVisible;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseBuilder<ContestListDTO> {

    private Integer contestId;
    private String title;
    private String description;
    private Byte type;
    private Timestamp time;
    private Integer length;
    private Boolean isVisible;

    @Override
    public ContestListDTO build() {
      return new ContestListDTO(contestId, title, description, type, time,
          length, isVisible);
    }

    @Override
    public ContestListDTO build(Map<String, Object> properties) {
      contestId = (Integer) properties.get("contestId");
      title = (String) properties.get("title");
      description = (String) properties.get("description");
      type = (Byte) properties.get("type");
      time = (Timestamp) properties.get("time");
      length = (Integer) properties.get("length");
      isVisible = (Boolean) properties.get("isVisible");
      return build();
    }

    public Builder setContestId(Integer contestId) {
      this.contestId = contestId;
      return this;
    }

    public Builder setTitle(String title) {
      this.title = title;
      return this;
    }

    public Builder setDescription(String description) {
      this.description = description;
      return this;
    }

    public Builder setType(Byte type) {
      this.type = type;
      return this;
    }

    public Builder setTime(Timestamp time) {
      this.time = time;
      return this;
    }

    public Builder setLength(Integer length) {
      this.length = length;
      return this;
    }

    public Builder setIsVisible(Boolean isVisible) {
      this.isVisible = isVisible;
      return this;
    }
  }
}
