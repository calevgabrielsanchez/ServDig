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
public class TramiteActivoSpecification extends BaseSpecification {

    private final List<Long> estados;

    public TramiteActivoSpecification(){

        this.estados = new ArrayList<Long>();
        this.estados.add(EstadoTareaUsuarioEnum.ACTIVA.getClave());
        this.estados.add(EstadoTareaUsuarioEnum.DISPONIBLE.getClave());

    }
    
  @Override
  public String prepareSQL(){
        return " (tarusu.CVE_ID_EDO_TAREA in (:estados)) AND sol.CVE_ID_ESTADO_SOLICITUD = :estadoSolicitud AND tra.CVE_ID_ESTADO_TRAMITE <> :estadoTramiteReg ";
  }
  
  @Override
  public void setParameter(SQLQuery query){

        query.setParameterList("estados" , estados);
        query.setParameter("estadoSolicitud", EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo());
        query.setParameter("estadoTramiteReg", EstadoTramiteEnum.INICIADO.getCodigo());
  }

  
}
