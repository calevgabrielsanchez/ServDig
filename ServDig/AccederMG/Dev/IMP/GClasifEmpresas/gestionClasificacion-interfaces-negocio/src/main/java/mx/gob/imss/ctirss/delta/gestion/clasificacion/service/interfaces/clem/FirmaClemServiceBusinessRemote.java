/**
 *  @Autor: JSM
 *  @Proyecto: IMSS DIGITAL
 *  FirmaClemServiceBusinessRemote
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clem
 *  @Fecha:14/07/2020
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clem;


import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.clasificacion.DatosClemException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.FirmaClemDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ResolucionVO;
import mx.gob.imss.ctirss.delta.model.Usuario;


@Remote
public interface FirmaClemServiceBusinessRemote {

	
	/**
	 * Obtiene lista de patrones con clem sin firma.
	 * @param cveDel, cveSub, fechaIni, fechaFin
	 * @return List<FirmaClemDTO>
	 * @throws Exception
	 */
	public DatosSalidaPaginador<FirmaClemDTO> buscaClemSinFirmaPaginado(
			DatosEntradaPaginador<FirmaClemDTO> datosEntrada);

	/**
	 * Obtiene lista de patrones con clem con firma.
	 * @param cveDel, cveSub, fechaIni, fechaFin
	 * @return List<FirmaClemDTO>
	 * @throws Exception
	 */
	public DatosSalidaPaginador<FirmaClemDTO> buscaClemConFirmaPaginado(
			DatosEntradaPaginador<FirmaClemDTO> datosEntrada);
			
	
	/**
	 * Obtiene lista de patrones a mandar a firma digital.
	 * @param ReporteClemBean
	 * @return List<DatosClemFirmaMasiva>
	 * @throws DatosClemException 
	 * @throws Exception
	 */
	List<ResolucionVO> buscarDatosFirmaMasivaCMS(List<FirmaClemDTO> patFirma);
	
}
