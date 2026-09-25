/**
]]

 * 
 */
package mx.gob.imss.csdiss.sdroc.service;

import java.util.List;

import mx.gob.imss.csdiss.sdroc.dto.InformacionObraDTO;

/**
 * @author daniel.hernandez
 *
 */
public interface ConsultaObraService {
	
	/**
	 *consultaSubcontratosPorNumRegistroObra
	 * @return Object
	 */
	Object consultaSubcontratosPorNumRegistroObra(String cveRegistroObraPrincipal);

	/**
	 * 
	 */
	int consultaObrasRegistradasAnualesPorRFC(String rfc, String anio);
	/**
	 * 
	 * @param rfc
	 * @param anio
	 * @return
	 */
	List<InformacionObraDTO> consultarInformacionObrasPorCveRfcyAnio(
			String rfc, String anio);

}
