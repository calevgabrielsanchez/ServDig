/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.service.bandeja.entity.specification;

import mx.gob.imss.ctirss.delta.model.enums.TipoRegularizacionSolicitudCDAEnum;
import org.hibernate.SQLQuery;

/**
 *
 * @author antonio
 */
public class AclaracionByTipoTramiteSpecification extends BaseSpecification {
  private final String tipoTramite;
  public AclaracionByTipoTramiteSpecification(String tipoTramite){
    this.tipoTramite = tipoTramite;
  }
  
  @Override
  public String prepareSQL(){
    return " dmanc.CVE_ID_TIPO_TRAM_CORREC_NSS= :tipoTramite ";
  }
  
  @Override
  public void setParameter(SQLQuery query){
    query.setParameter("tipoTramite", TipoRegularizacionSolicitudCDAEnum.
                fromDesc(this.tipoTramite).getId());
  }

  
}
