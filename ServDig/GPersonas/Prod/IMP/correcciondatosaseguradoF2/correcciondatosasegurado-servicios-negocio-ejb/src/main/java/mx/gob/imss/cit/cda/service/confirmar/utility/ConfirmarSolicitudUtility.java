package mx.gob.imss.cit.cda.service.confirmar.utility;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.utility.CorreccionDatosAseguradoUtilityLocal;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.MensajeTarea;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.TipoTransicionEnum;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

@Stateless(name="confirmarSolicitudUtility", mappedName="confirmarSolicitudUtility")
public class ConfirmarSolicitudUtility implements ConfirmarSolicitudUtilityLocal{
    
    @EJB
    private CorreccionDatosAseguradoUtilityLocal correccionAseguradoUtilityLocal;

    @Override
    public MensajeTarea crearMensajeTarea(TramiteCorreccionCurp tramiteCda) {
        MensajeTarea mensajeTarea = new MensajeTarea();
        mensajeTarea.setTipoTransicion(TipoTransicionEnum.PRINCIPAL.getId());
        mensajeTarea.setEstado(EstadoNegocioEnum.obtenerDescripcionNegocio(tramiteCda.getEstadoTramite().getIdEstadoTramitePersona()));
        mensajeTarea.setFechaActualizacion(getCorreccionAseguradoUtilityLocal().convertDateToString(new Date()));

        if (tramiteCda.getTipoRegularizacion() != null) {
            Map<String, Object> mapa = new HashMap<String, Object>();
            mapa.put("tipoRegularizacion", tramiteCda.getTipoRegularizacion().getIdTipoRegularizacion());
            mensajeTarea.setData(getCorreccionAseguradoUtilityLocal().generarJsonDataWf(mapa));
            //LOGGER.debug("tipo Transiccion {} ", TipoTransicionEnum.PRINCIPAL.getId());
        }
        
        return mensajeTarea;
        
    }
    
    public CorreccionDatosAseguradoUtilityLocal getCorreccionAseguradoUtilityLocal() {
        return correccionAseguradoUtilityLocal;
    }

}
