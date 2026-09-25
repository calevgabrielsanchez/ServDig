package mx.gob.imss.cit.cda.web.utils;

import java.util.HashMap;
import java.util.Map;

import mx.gob.imss.cit.cda.web.app.responsable.model.TipoRegularizacion;
import mx.gob.imss.ctirss.delta.model.enums.TipoRegularizacionSolicitudCDAEnum;

import org.springframework.stereotype.Component;

@Component
public class TipoRegularizacionUtil {
	
	private static final Map<Long, TipoRegularizacion> tiposRegularizacionSol = new HashMap<Long, TipoRegularizacion>();
	
	{
		TipoRegularizacion tipoCURP = new TipoRegularizacion();
		tipoCURP.setCorreccionCURP(true);
		TipoRegularizacion tipoDatosBasicos = new TipoRegularizacion();
		tipoDatosBasicos.setCorreccionDatosBasicos(true);
		TipoRegularizacion tipoCuentaIlogica = new TipoRegularizacion();
		tipoCuentaIlogica.setCuentaIlogica(true);
		TipoRegularizacion tipoDesvinculacion = new TipoRegularizacion();
		tipoDesvinculacion.setDesvinculacion(true);
		TipoRegularizacion tipoDuplicidad = new TipoRegularizacion();
		tipoDuplicidad.setDuplicidad(true);
		TipoRegularizacion tipoHomonimia = new TipoRegularizacion();
		tipoHomonimia.setHomonimia(true);
		TipoRegularizacion tipoInvasion = new TipoRegularizacion();
		tipoInvasion.setInvacionCuentaIndividual(true);
		
		tiposRegularizacionSol.put(TipoRegularizacionSolicitudCDAEnum.CORRECCION_CURP.getId(), tipoCURP);
		tiposRegularizacionSol.put(TipoRegularizacionSolicitudCDAEnum.CORRECION_DATOS_BASICOS.getId(),
				tipoDatosBasicos);
		tiposRegularizacionSol.put(TipoRegularizacionSolicitudCDAEnum.CUENTA_ILOGICA.getId(), tipoCuentaIlogica);
		tiposRegularizacionSol.put(TipoRegularizacionSolicitudCDAEnum.DESVINCULACION.getId(), tipoDesvinculacion);
		tiposRegularizacionSol.put(TipoRegularizacionSolicitudCDAEnum.DUPLICIDAD.getId(), tipoDuplicidad);
		tiposRegularizacionSol.put(TipoRegularizacionSolicitudCDAEnum.HOMONIMIA.getId(), tipoHomonimia);
		tiposRegularizacionSol.put(TipoRegularizacionSolicitudCDAEnum.INVASION.getId(), tipoInvasion);
		
	}

	public TipoRegularizacion getTipoRegularizacion(Long idTipoRegularizacion){
		return tiposRegularizacionSol.get(idTipoRegularizacion);
	}
	
}
