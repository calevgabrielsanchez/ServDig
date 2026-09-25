/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.support.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author antonio
 * @param <T> Modelo de la lista
 */
public class Page<T extends BaseModel> implements Serializable {
  private List<T> data;
  private long currentPage;
  private long pageSize;
  private long totalOfRecords;

  /**
   * @return the data
   */
  public List<T> getData() {
    return data;
  }

  /**
   * @param data the data to set
   */
  public void setData(List<T> data) {
    this.data = data;
  }

  /**
   * @return the currentPage
   */
  public long getCurrentPage() {
    return currentPage;
  }

  /**
   * @param currentPage the currentPage to set
   */
  public void setCurrentPage(long currentPage) {
    this.currentPage = currentPage;
  }

  /**
   * @return the pageSize
   */
  public long getPageSize() {
    return pageSize;
  }

  /**
   * @param pageSize the pageSize to set
   */
  public void setPageSize(long pageSize) {
    this.pageSize = pageSize;
  }

  /**
   * @return the totalOfRecords
   */
  public long getTotalOfRecords() {
    return totalOfRecords;
  }

  /**
   * @param totalOfRecords the totalOfRecords to set
   */
  public void setTotalOfRecords(long totalOfRecords) {
    this.totalOfRecords = totalOfRecords;
  }
  
}
