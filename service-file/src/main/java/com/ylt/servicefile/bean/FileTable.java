package com.ylt.servicefile.bean;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="filetable")
public class FileTable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String fileName;
  private String filePath;
  //  jackson转化的时候，默认的时间格式是 'yyyy-MM-dd'T'HH:mm:ss.SSS,所以需要自定义
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
  private LocalDateTime createTime;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public LocalDateTime getCreateTime() {
    return createTime;
  }

  public void setCreateTime(LocalDateTime createTime) {
    this.createTime = createTime;
  }

  @Override
  public String toString() {
    return "FileTable{" +
            "id=" + id +
            ", fileName='" + fileName + '\'' +
            ", filePath='" + filePath + '\'' +
            ", createTime=" + createTime +
            '}';
  }

  public FileTable() {
  }

  public FileTable(String fileName, String filePath, LocalDateTime createTime) {
    this.fileName = fileName;
    this.filePath = filePath;
    this.createTime = createTime;
  }
}
