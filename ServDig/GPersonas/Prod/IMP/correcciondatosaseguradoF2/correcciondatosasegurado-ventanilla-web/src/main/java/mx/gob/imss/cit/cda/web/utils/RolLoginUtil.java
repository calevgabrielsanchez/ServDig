package mx.gob.imss.cit.cda.web.utils;

import java.util.HashMap;
import java.util.Map;

import mx.gob.imss.cit.cda.web.constants.RolUsuarioEnum;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RolLoginUtil {

    private static final Map<String, RolUsuarioEnum> rolesContexto = new HashMap<String, RolUsuarioEnum>();

    // contexto actual de la app
    @Value("${app.context}")
    private String CONTEXTO_APLICACION;

    // contextos
    private static final String CONTEXTO_RESPONSABLE = "correccionDatosAsegurado-web-ventanilla";
    private static final String CONTEXTO_AUTORIZADOR = "correccionDatosAsegurado-web-autorizador";
    private static final String CONTEXTO_NORMATIVO = "correccionDatosAsegurado-web-normativo";
    
    {
        rolesContexto.put(CONTEXTO_RESPONSABLE, RolUsuarioEnum.VENTANILLA);
        rolesContexto.put(CONTEXTO_AUTORIZADOR, RolUsuarioEnum.AUTORIZADOR_DAV);
        rolesContexto.put(CONTEXTO_NORMATIVO, RolUsuarioEnum.TIT_COORD_AFILIACION);
        rolesContexto.put(CONTEXTO_NORMATIVO, RolUsuarioEnum.TIT_AFILIACION_OBLIGATORIO);
        rolesContexto.put(CONTEXTO_NORMATIVO, RolUsuarioEnum.TIT_INSCRIP_ASEG);
        rolesContexto.put(CONTEXTO_NORMATIVO, RolUsuarioEnum.TIT_SOPORTE_AFILIACION);
        rolesContexto.put(CONTEXTO_NORMATIVO, RolUsuarioEnum.JEFE_COORD_AFILIACION);
        rolesContexto.put(CONTEXTO_NORMATIVO, RolUsuarioEnum.ANALISTA_COORD_AFLICIACION);

    }

    public RolUsuarioEnum getRolFuncionario() {
        return rolesContexto.get(CONTEXTO_APLICACION);
    }

}
