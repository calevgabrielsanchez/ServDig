package mx.gob.imss.ctirss.clasificador.model.controller;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractDataTableReply
  implements Serializable
{
  private int iTotalRecords;
  private int iTotalDisplayRecords;
  private String sEcho;
  private String sColumns;
  private List aaData;
  private List<String> palabrasConcretas;
  
  public int getiTotalRecords() {
     return this.iTotalRecords;
  }
  
  public void setiTotalRecords(int iTotalRecords) {
     this.iTotalRecords = iTotalRecords;
  }
  
  public int getiTotalDisplayRecords() {
     return this.iTotalDisplayRecords;
  }
  
  public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
     this.iTotalDisplayRecords = iTotalDisplayRecords;
  }
  
  public String getsEcho() {
     return this.sEcho;
  }
 
  public void setsEcho(String sEcho) {
     this.sEcho = sEcho;
  }
 
  public String getsColumns() {
     return this.sColumns;
  }
  
  public void setsColumns(String sColumns) {
     this.sColumns = sColumns;
  }

  public List getAaData() {
     return this.aaData;
  }
  
  public void setAaData(List aaData) {
     this.aaData = aaData;
  }
  
  public List<String> getPalabrasConcretas() {
     return this.palabrasConcretas;
  }
  
  public void setPalabrasConcretas(List<String> palabrasConcretas) {
     this.palabrasConcretas = palabrasConcretas;
  }
}
