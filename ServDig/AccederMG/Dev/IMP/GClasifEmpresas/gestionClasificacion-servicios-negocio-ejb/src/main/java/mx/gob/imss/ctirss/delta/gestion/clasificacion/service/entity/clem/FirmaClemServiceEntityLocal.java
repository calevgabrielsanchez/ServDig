/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Héctor Lara Andrés
 *  @Proyecto: delta
 *  @Archivo:DatosClemServiceEntityLocal.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clem
 *  @Fecha:17/08/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clem;


import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.FirmaClemDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ResolucionVO;
import mx.gob.imss.ctirss.delta.model.clasificacion.ReporteClemBean;

@Local
public interface FirmaClemServiceEntityLocal {
	
	/**
	 * Metodo para consultar Clem sin firma
	 * @param model
	 * @return
	 */
	public DatosSalidaPaginador<FirmaClemDTO> consultarClemPaginado(
			DatosEntradaPaginador<FirmaClemDTO> datosEntrada);

	/**
	 * Metodo para consultar Clem con firma
	 * @param model
	 * @return
	 */
	public DatosSalidaPaginador<FirmaClemDTO> consultarClemCFPaginado(
			DatosEntradaPaginador<FirmaClemDTO> datosEntrada);
			

	List<ResolucionVO> obtenerDatosClemFirmaMasiva(List<Long> idAnalisis); 

}