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
public class SolicitudByFolioSpecification extends BaseSpecification {
  private final String folio;
  public SolicitudByFolioSpecification(String folio){
    this.folio = folio;
  }
  
  @Override
  public String prepareSQL(){
    return " sol.REF_FOLIO = :folio ";
  }
  
  @Override
  public void setParameter(SQLQuery query){
    query.setParameter("folio", this.folio);
  }

  
}
