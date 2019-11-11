package com.ylt.serviceorder.bean;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="product")
public class Product {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long pid;
  private String pname;
  private double marketPrice;
  private double shopPrice;
  private String pimage;

//  jackson转化的时候，默认的时间格式是 'yyyy-MM-dd'T'HH:mm:ss.SSS,所以需要自定义
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
  @Column(name="pdate")
  private LocalDate pdate;
  private long isHot;
  private String pdesc;
  private long pflag;
  private String cid;


  public long getPid() {
    return pid;
  }

  public void setPid(long pid) {
    this.pid = pid;
  }


  public String getPname() {
    return pname;
  }

  public void setPname(String pname) {
    this.pname = pname;
  }


  public double getMarketPrice() {
    return marketPrice;
  }

  public void setMarketPrice(double marketPrice) {
    this.marketPrice = marketPrice;
  }


  public double getShopPrice() {
    return shopPrice;
  }

  public void setShopPrice(double shopPrice) {
    this.shopPrice = shopPrice;
  }


  public String getPimage() {
    return pimage;
  }

  public void setPimage(String pimage) {
    this.pimage = pimage;
  }


  public LocalDate getPdate() {
    return pdate;
  }

  public void setPdate(LocalDate pdate) {
    this.pdate = pdate;
  }


  public long getIsHot() {
    return isHot;
  }

  public void setIsHot(long isHot) {
    this.isHot = isHot;
  }


  public String getPdesc() {
    return pdesc;
  }

  public void setPdesc(String pdesc) {
    this.pdesc = pdesc;
  }


  public long getPflag() {
    return pflag;
  }

  public void setPflag(long pflag) {
    this.pflag = pflag;
  }


  public String getCid() {
    return cid;
  }

  public void setCid(String cid) {
    this.cid = cid;
  }

  @Override
  public String toString() {
    return "Product{" +
            "pid=" + pid +
            ", pname='" + pname + '\'' +
            ", marketPrice=" + marketPrice +
            ", shopPrice=" + shopPrice +
            ", pimage='" + pimage + '\'' +
            ", pdate=" + pdate +
            ", isHot=" + isHot +
            ", pdesc='" + pdesc + '\'' +
            ", pflag=" + pflag +
            ", cid='" + cid + '\'' +
            '}';
  }
}
