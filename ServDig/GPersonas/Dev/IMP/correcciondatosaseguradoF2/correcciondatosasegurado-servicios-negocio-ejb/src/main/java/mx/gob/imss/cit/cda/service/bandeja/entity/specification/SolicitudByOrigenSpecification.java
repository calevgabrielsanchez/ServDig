/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.service.bandeja.entity.specification;

import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import org.hibernate.SQLQuery;

/**
 *
 * @author antonio
 */
public class SolicitudByOrigenSpecification extends BaseSpecification {
  private final Long idOrigen;
  public SolicitudByOrigenSpecification(String origen){
    this.idOrigen = "INTERNET".equals(origen) ? 
      OrigenSolicitudEnum.PORTAL_CIUDADANO.getId() :
      OrigenSolicitudEnum.getByDesc(origen).getId();    
  }
  
  @Override
  public String prepareSQL(){
    return " sol.CVE_ID_ORIGEN_SOLICITUD = :idOrigen ";
  }
  
  @Override
  public void setParameter(SQLQuery query){
    query.setParameter("idOrigen", this.idOrigen);
  }

  
}
