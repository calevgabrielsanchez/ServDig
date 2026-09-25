package mx.gob.imss.cit.cda.service.rechazar.utility;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.utility.CorreccionDatosAseguradoUtilityLocal;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.MensajeTarea;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.TipoTransicionEnum;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.apache.commons.lang.StringUtils;

@Stateless(name="rechazarSolicitudUtility", mappedName="rechazarSolicitudUtility")
public class RechazarSolicitudUtility implements RechazarSolicitudUtilityLocal{
    
    @EJB
    private CorreccionDatosAseguradoUtilityLocal correccionAseguradoUtilityLocal;
    

    @Override
    public MensajeTarea crearMensajeTarea(Solicitud solicitud) {
        MensajeTarea mensajeTarea = new MensajeTarea();
        mensajeTarea.setObservacion(StringUtils.isNotBlank(solicitud.getRazonCancelacion().getDescripcion()) ? solicitud.getRazonCancelacion().getDescripcion(): "");
        mensajeTarea.setTipoTransicion(TipoTransicionEnum.PRINCIPAL.getId());
        mensajeTarea.setEstado(EstadoNegocioEnum.obtenerDescripcionNegocio(solicitud.getTramites().get(0).getEstadoTramite().getIdEstadoTramitePersona()));
        mensajeTarea.setFechaActualizacion(getCorreccionAseguradoUtilityLocal().convertDateToString(new Date()));
        return mensajeTarea;  
    }

    public CorreccionDatosAseguradoUtilityLocal getCorreccionAseguradoUtilityLocal() {
        return correccionAseguradoUtilityLocal;
    }
    
}
