package mx.gob.imss.cit.cda.service.solicitarinformacion.utility;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.solicitarinformacion.constants.SolicitarInformacionConstants;
import mx.gob.imss.cit.cda.service.utility.CorreccionDatosAseguradoUtilityLocal;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.MensajeTarea;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

@Stateless(name="solicitarInformacionUtility", mappedName="solicitarInformacionUtility")
public class SolicitarInformacionUtility implements SolicitarInformacionUtilityLocal{
    
    @EJB
    private CorreccionDatosAseguradoUtilityLocal correccionAseguradoUtilityLocal;

    @Override
    public MensajeTarea crearMensajeTarea(Solicitud solicitud, String usuario, TramiteCorreccionCurp tramiteCda, String observacion) {
        MensajeTarea mensajeTarea = new MensajeTarea();
        mensajeTarea.setFechaActualizacion(getCorreccionAseguradoUtilityLocal().convertDateToString(new Date()));
        mensajeTarea.setEstado(tramiteCda.getEstadoTramite().getDescripcion());
        mensajeTarea.setObservacion(observacion);
        mensajeTarea.setUsuario(usuario);
        return mensajeTarea;
    }
    
    @Override
    public int obtenerTipoUsuarioVentanilla(Boolean isAutorizador){
        if(isAutorizador){
            return SolicitarInformacionConstants.INFO_AUTORIZADOR;
        }
        return SolicitarInformacionConstants.INFO_RESPONSABLE;
    }
    
    public CorreccionDatosAseguradoUtilityLocal getCorreccionAseguradoUtilityLocal() {
        return correccionAseguradoUtilityLocal;
    }


}
