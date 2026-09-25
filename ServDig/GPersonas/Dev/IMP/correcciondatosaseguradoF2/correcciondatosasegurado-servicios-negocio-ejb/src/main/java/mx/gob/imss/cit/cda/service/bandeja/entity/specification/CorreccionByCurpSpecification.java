/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.service.bandeja.entity.specification;

import org.hibernate.SQLQuery;

/**
 *
 * @author antonio
 */
public class CorreccionByCurpSpecification extends BaseSpecification {
  private final String curp;
  public CorreccionByCurpSpecification(String curp){
    this.curp = curp;
  }
  
  @Override
  public String prepareSQL(){
    return " corr.REF_CURP = :curp ";
  }
  
  @Override
  public void setParameter(SQLQuery query){
    query.setParameter("curp", this.curp);
  }

  
}
