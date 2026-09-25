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
public class SolicitudByFechaSpecification extends BaseSpecification {
  private final String fecha;
  public SolicitudByFechaSpecification(String fecha){
    this.fecha = fecha;
  }
  
  @Override
  public String prepareSQL(){
    return " ( sol.FEC_SOLICITUD BETWEEN TO_DATE(:fechaSolicitudInicial,'dd/MM/YYYY HH24:mi:ss') AND TO_DATE(:fechaSolicitudFinal,'dd/MM/YYYY HH24:mi:ss') ) ";
  }
  
  @Override
  public void setParameter(SQLQuery query){
    query.setParameter("fechaSolicitudInicial", this.fecha
                + " 00:00:00");
    query.setParameter("fechaSolicitudFinal", this.fecha
                + " 23:59:59");
  }

  
}
