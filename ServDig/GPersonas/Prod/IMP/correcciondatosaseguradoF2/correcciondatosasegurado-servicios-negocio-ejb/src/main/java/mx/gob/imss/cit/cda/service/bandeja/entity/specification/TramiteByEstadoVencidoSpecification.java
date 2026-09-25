/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.service.bandeja.entity.specification;

import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import org.hibernate.SQLQuery;

/**
 *
 * @author antonio
 */
public class TramiteByEstadoVencidoSpecification extends BaseSpecification {  
    
  @Override
  public String prepareSQL(){    
    return " tra.CVE_ID_ESTADO_TRAMITE = :vencido ";
  }
  
  @Override
  public void setParameter(SQLQuery query){
    query.setParameter("vencido", EstadoTramiteEnum.VENCIDA.getCodigo());
  }

  
}
