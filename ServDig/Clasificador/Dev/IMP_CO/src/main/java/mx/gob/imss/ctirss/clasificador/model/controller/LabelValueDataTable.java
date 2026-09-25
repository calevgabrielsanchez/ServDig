package mx.gob.imss.ctirss.clasificador.model.controller;

import java.io.Serializable;

public class LabelValueDataTable implements Serializable {
  private String name;
  
  private String value;
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getValue() {
    return this.value;
  }
  
  public void setValue(String value) {
    this.value = value;
  }
}