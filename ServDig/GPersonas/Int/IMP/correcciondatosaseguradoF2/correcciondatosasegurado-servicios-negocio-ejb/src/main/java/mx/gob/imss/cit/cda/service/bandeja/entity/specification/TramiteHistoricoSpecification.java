/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.service.bandeja.entity.specification;

import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.EstadoTareaUsuarioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import org.hibernate.SQLQuery;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author antonio
 */
public class TramiteHistoricoSpecification extends BaseSpecification {

    private final List<Long> estadosTarea;
    private final List<Integer> estadosSolicitud;
    private final List<Integer> estadosTramite;

    public TramiteHistoricoSpecification(){

        this.estadosSolicitud = new ArrayList<Integer>();
        this.estadosSolicitud.add(EstadoSolicitudEnum.ATENDIDA.getCodigo());
        this.estadosSolicitud.add(EstadoSolicitudEnum.CANCELADA.getCodigo());

        this.estadosTarea = new ArrayList<Long>();
        this.estadosTarea.add(EstadoTareaUsuarioEnum.ACTIVA.getClave());
        this.estadosTarea.add(EstadoTareaUsuarioEnum.DISPONIBLE.getClave());
        this.estadosTarea.add(EstadoTareaUsuarioEnum.COMPLETADA.getClave());

        this.estadosTramite = new ArrayList<Integer>();
        this.estadosTramite.add(EstadoTramiteEnum.CERRADO.getCodigo());
        this.estadosTramite.add(EstadoTramiteEnum.CANCELADO.getCodigo());
        this.estadosTramite.add(EstadoTramiteEnum.BAJA_IMPROCEDENCIA.getCodigo());
    }
    
  @Override
  public String prepareSQL(){    
    return " (tarusu.CVE_ID_EDO_TAREA in (:estadosTarea)) AND (sol.CVE_ID_ESTADO_SOLICITUD in (:estadosSolicitud)) AND tra.cve_id_estado_tramite in (:estadosTramite) ";
  }
  
  @Override
  public void setParameter(SQLQuery query){

      query.setParameterList("estadosTarea", estadosTarea);
      query.setParameterList("estadosSolicitud", estadosSolicitud);
      query.setParameterList("estadosTramite", estadosTramite);
  }

  
}
