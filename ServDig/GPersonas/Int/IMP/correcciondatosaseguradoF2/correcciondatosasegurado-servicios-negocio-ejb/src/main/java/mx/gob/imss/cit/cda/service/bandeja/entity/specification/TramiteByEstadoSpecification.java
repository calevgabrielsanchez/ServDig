/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.service.bandeja.entity.specification;

import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoTramiteBandejaCDA;
import org.hibernate.SQLQuery;

/**
 *
 * @author antonio
 */
public class TramiteByEstadoSpecification extends BaseSpecification {
  private final String estado;
  public TramiteByEstadoSpecification(String estado){
    this.estado = estado;    
  }
  
  // FIX: Considerar crear una especificacion para indicar la tarea usuario por estado
  @Override
  public String prepareSQL(){
    EstadoTramiteBandejaCDA estadoEnum = EstadoTramiteBandejaCDA.
              parseEstadoNegocioCDAToId(this.estado);
    StringBuilder sb = new StringBuilder();

    sb.append( " ( ");
    sb.append( estadoEnum.getIdEstado() == 5 ?
      " ( tra.cve_id_estado_tramite = :estado or tra.cve_id_estado_tramite=89 ) ":
      " tra.cve_id_estado_tramite = :estado " );

    sb.append( EstadoNegocioEnum.ASIGNADA.getDescripcion().equals(this.estado) ?
            " AND tarusu.des_bdoc_tarea not like '%REASIGNADA%' ": "" );

    sb.append( EstadoNegocioEnum.REASIGNADA.getDescripcion().equals(this.estado) ?
            " AND tarusu.des_bdoc_tarea like '%REASIGNADA%' ": "");
    sb.append(" ) ");
    return sb.toString();
  }
  
  @Override
  public void setParameter(SQLQuery query){
    EstadoTramiteBandejaCDA estadoEnum = EstadoTramiteBandejaCDA.
              parseEstadoNegocioCDAToId(this.estado);
    query.setParameter("estado", estadoEnum.getIdEstado() );
  }

  
}
