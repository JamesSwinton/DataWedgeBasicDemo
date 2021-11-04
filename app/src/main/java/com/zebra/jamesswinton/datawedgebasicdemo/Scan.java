package com.zebra.jamesswinton.datawedgebasicdemo;

public class Scan {

  String data, symbology, source;

  public Scan(String data, String symbology, String source) {
    this.data = data;
    this.symbology = symbology;
    this.source = source;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public String getSymbology() {
    return symbology;
  }

  public void setSymbology(String symbology) {
    this.symbology = symbology;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }
}
