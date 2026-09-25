package mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces;

import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.asegurado.Empleado;

@Remote
public interface ProcesoSIMEServiceBusinessRemote {

	/**
	 * Servicio que genera el contenido del archivo SAAI
	 * 
	 * @param registros
	 * @return
	 */
	byte[] generarContenidoArchivoSAIIA(Map<Integer, Empleado> registros);

}
