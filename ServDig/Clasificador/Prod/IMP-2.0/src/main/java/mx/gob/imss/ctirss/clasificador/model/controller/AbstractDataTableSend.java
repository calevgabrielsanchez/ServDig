package mx.gob.imss.ctirss.clasificador.model.controller;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;

public abstract class AbstractDataTableSend
  implements Serializable
{
  private int iDisplayLength;
  private int iDisplayStart;
  private String iColumns;
  private String sSearch;
  private boolean bRegex;
  private int iSortingCols;
  private String sEcho;
  private String dispatch;
  
  public int getiDisplayLength() {
     return this.iDisplayLength;
 }



 
 public void setiDisplayLength(int iDisplayLength) {
    this.iDisplayLength = iDisplayLength;
 }



 
 public int getiDisplayStart() {
     return this.iDisplayStart;
 }



 
 public void setiDisplayStart(int iDisplayStart) {
     this.iDisplayStart = iDisplayStart;
 }



 
 public String getiColumns() {
    return this.iColumns;
 }



 
 public void setiColumns(String iColumns) {
    this.iColumns = iColumns;
 }



 
 public String getsSearch() {
     return this.sSearch;
 }



 
 public void setsSearch(String sSearch) {
     this.sSearch = sSearch;
 }



 
 public boolean isbRegex() {
     return this.bRegex;
 }



 
 public void setbRegex(boolean bRegex) {
     this.bRegex = bRegex;
 }



 
 public int getiSortingCols() {
     return this.iSortingCols;
 }



 
 public void setiSortingCols(int iSortingCols) {
    this.iSortingCols = iSortingCols;
 }



 
 public String getsEcho() {
     return this.sEcho;
 }



 
 public void setsEcho(String sEcho) {
     this.sEcho = sEcho;
 }








 
 public void parserArray(List<LinkedHashMap<String, Object>> aoData) {
     System.out.println("aoData .:::" + aoData);

   
     if (aoData != null) {
      Iterator<LinkedHashMap<String, Object>> it = aoData.iterator();
       while (it.hasNext()) {
         LinkedHashMap<String, Object> lb = it.next();
       
         System.out.println("1. All Names :" + lb.get("name"));
         System.out.println("2. All Values :" + lb.get("value"));


       
         String name = (String)lb.get("name");
         Object value = lb.get("value");


       
       try {
           System.out.println("Setting property :" + name + "with value :" + value);
         
          BeanUtils.setProperty(this, name, value);
       }
        catch (IllegalAccessException e) {
         
          e.printStackTrace();
        } catch (InvocationTargetException e) {
         
          e.printStackTrace();
       } 
     } 
   } 
 }







 
 public String getDispatch() {
    return this.dispatch;
 }



 
 public void setDispatch(String dispatch) {
    this.dispatch = dispatch;
 }
}
