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
public class SolicitudByFechaActualizacionSpecification extends BaseSpecification {
  private final String fechaActualizacion;
  public SolicitudByFechaActualizacionSpecification(String fechaActualizacion){
    this.fechaActualizacion = fechaActualizacion;
  }
  
  @Override
  public String prepareSQL(){
    return " ( sol.FEC_REGISTRO_ACTUALIZADO BETWEEN TO_DATE(:fechaActualizacionInicial,'dd/MM/YYYY HH24:mi:ss') AND TO_DATE(:fechaActualizacionFinal,'dd/MM/YYYY HH24:mi:ss') ) ";
  }
  
  @Override
  public void setParameter(SQLQuery query){
    query.setParameter("fechaActualizacionInicial", this.fechaActualizacion
                + " 00:00:00");
    query.setParameter("fechaActualizacionFinal", this.fechaActualizacion
                + " 23:59:59");
  }

  
}
