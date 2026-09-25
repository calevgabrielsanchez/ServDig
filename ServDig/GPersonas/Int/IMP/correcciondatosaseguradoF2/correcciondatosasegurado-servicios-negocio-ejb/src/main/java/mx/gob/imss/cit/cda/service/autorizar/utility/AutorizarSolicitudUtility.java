package mx.gob.imss.cit.cda.service.autorizar.utility;

import java.text.Normalizer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.utility.CorreccionDatosAseguradoUtilityLocal;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.MensajeTarea;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.ParticipantesEnum;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;

import org.apache.commons.lang.StringUtils;

@Stateless(name="autorizarSolicitudUtility", mappedName="autorizarSolicitudUtility")
public class AutorizarSolicitudUtility implements AutorizarSolicitudUtilityLocal{
    
    @EJB
    private CorreccionDatosAseguradoUtilityLocal correccionAseguradoUtilityLocal;
    
    private static final Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    
    @Override
    public MensajeTarea crearMensajeTarea(String usuario){
        MensajeTarea mensajeTarea = new MensajeTarea();
        mensajeTarea.setFechaActualizacion(correccionAseguradoUtilityLocal.convertDateToString(new Date()));
        mensajeTarea.setEstado(EstadoNegocioEnum.obtenerDescripcionNegocio(EstadoTramiteEnum.ANALISIS_COMPLETADO.getCodigo()));
        Map<String, String> participantes = new HashMap<String, String>();
        participantes.put(ParticipantesEnum.AUTORIZADOR.getDescripcion(),usuario);
        mensajeTarea.setParticipantes(participantes);
        
        return mensajeTarea;
    }
    
    @Override
    public List<Fisica> normalizarFisicasHistoricas(List<Fisica> fisicasHistoricas) {
        if (fisicasHistoricas != null) {
            for (Fisica fisica : fisicasHistoricas) {
                if (fisica.getNombre() != null) {
                    fisica.setNombre(normalizarCadenas(fisica.getNombre()).replace("\u001A", "#"));
                }
                if (fisica.getPrimerApellido() != null) {
                    fisica.setPrimerApellido(normalizarCadenas(fisica.getPrimerApellido()).replace("\u001A", "#"));
                }
                if (fisica.getSegundoApellido() != null) {
                    fisica.setSegundoApellido(normalizarCadenas(fisica.getSegundoApellido()).replace("\u001A", "#"));
                }
            }
        }

        return fisicasHistoricas;
    }
    
    private String normalizarCadenas(Object cadena) {
        if (cadena != null) {
            return pattern.matcher(Normalizer.normalize(cadena.toString(),Normalizer.Form.NFD)).replaceAll("");
        }
        return StringUtils.EMPTY;
    }

    public CorreccionDatosAseguradoUtilityLocal getCorreccionAseguradoUtilityLocal() {
        return correccionAseguradoUtilityLocal;
    }
    

}
