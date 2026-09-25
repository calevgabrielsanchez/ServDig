package mx.gob.imss.cit.cda.service.cancelar.utility;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.utility.CorreccionDatosAseguradoUtilityLocal;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.MensajeTarea;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.ParticipantesEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.TipoTransicionEnum;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;


@Stateless(name="cancelarSolicitudUtility", mappedName="cancelarSolicitudUtility")
public class CancelarSolicitudUtility implements CancelarSolicitudUtilityLocal{
    
    @EJB
    private CorreccionDatosAseguradoUtilityLocal correccionAseguradoUtilityLocal;
    
    public MensajeTarea crearMensajeTarea(Solicitud solicitud,String usuario, Boolean isAutorizador,TramiteCorreccionCurp tramiteCda){
        MensajeTarea mensajeTarea = new MensajeTarea();
        mensajeTarea.setObservacion(solicitud.getRazonCancelacion().getDescripcion());
        mensajeTarea.setEstado(EstadoNegocioEnum.obtenerDescripcionNegocio(tramiteCda.getEstadoTramite().getIdEstadoTramitePersona()));
        mensajeTarea.setTipoTransicion(TipoTransicionEnum.ALTERNATIVA1.getId());
        mensajeTarea.setParticipantes(crearParticipantes(usuario, isAutorizador));
        mensajeTarea.setUsuario(usuario);
        mensajeTarea.setFechaActualizacion(getCorreccionAseguradoUtilityLocal().convertDateToString(new Date()));
        
        if (tramiteCda.getTipoRegularizacion() != null) {
            Map<String, Object> mapa = new HashMap<String, Object>();
            mapa.put("tipoRegularizacion", tramiteCda.getTipoRegularizacion().getIdTipoRegularizacion());
            mensajeTarea.setData(getCorreccionAseguradoUtilityLocal().generarJsonDataWf(mapa));
        }
        
        return mensajeTarea;
    }
    
    private Map<String, String> crearParticipantes(String usuario, Boolean isAutorizador){
        Map<String, String> participantes = new HashMap<String, String>();
        if(isAutorizador){
            participantes.put(ParticipantesEnum.AUTORIZADOR.getDescripcion().toString(), usuario);
        }else{
            participantes.put(ParticipantesEnum.RESPONSABLE.getDescripcion().toString(), usuario);
        } 
      return participantes;
    }
    
    public CorreccionDatosAseguradoUtilityLocal getCorreccionAseguradoUtilityLocal() {
        return correccionAseguradoUtilityLocal;
    }
    
}
